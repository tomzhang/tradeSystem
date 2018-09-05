package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.cache.RedisKeys;
import com.bernard.cache.service.CacheService;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.MatchOrderReply;
import io.grpc.tradesystem.service.MatchOrderRequest;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class MatchOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(MatchOrderTask.class);
    private MatchOrderRequest matchOrderRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");
    private CacheService cacheService = (CacheService) App.context.getBean("cacheServiceImpl");


    private MatchOrderTask() {

    }

    public MatchOrderTask(MatchOrderRequest matchOrderRequest) {
        this.matchOrderRequest = matchOrderRequest;

    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理成交回报======");
        long start = System.currentTimeMillis();
        //0.插入成交流水
      /*  try {
            userDataService.insertMatchFlow(matchOrderRequest.getMatchOrderWaterflow(),
                    matchOrderRequest.getSellSideOrderId(),
                    matchOrderRequest.getSellSideAccount(),
                    matchOrderRequest.getBuySideOrderId(),
                    matchOrderRequest.getBuySideAccount(),
                    matchOrderRequest.getMatchPrice(),
                    matchOrderRequest.getMatchAmount(),
                    new Date());
        } catch (Exception e) {
            logger.error("插入成交流水失败", e);
            replyErrorState();
            return null;
        }*/
        //1.查询订单
        List<Order> matchOrders = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        strings.add(RedisKeys.ORDER_MAP_PREFIX + matchOrderRequest.getBuySideOrderId());
        strings.add(RedisKeys.ORDER_MAP_PREFIX + matchOrderRequest.getSellSideOrderId());
        matchOrders = cacheService.mGetCacheOrder(strings);
        if (matchOrders == null || matchOrders.size() != 2) {
            try {
                logger.info("缓存未命中，查询数据库订单");
                matchOrders = userDataService.queryMatchOrders(matchOrderRequest.getBuySideOrderId(), matchOrderRequest.getSellSideOrderId());
                logger.info("缓存未命中，查询数据库订单耗时：" + (System.currentTimeMillis() - start));
                if (matchOrders.size() != 2) {
                    logger.fatal("查询数据库后，成交单号匹配错误:" + matchOrderRequest.getBuySideOrderId() + "|" + matchOrderRequest.getSellSideOrderId());
                    replyErrorState();
                    return null;
                }
            } catch (Exception e) {
                logger.error("查询成交订单失败", e);
                replyErrorState();
                return null;
            }
        }
        //2.处理订单
        List<AssetUpdate> assetUpdates = new ArrayList<>();
        List<OrderUpdate> orderUpdates = new ArrayList<>();
        for (Order order : matchOrders) {
            boolean handleOrderResult = handleOrder(order, new BigDecimal(matchOrderRequest.getMatchAmount()), new BigDecimal(matchOrderRequest.getMatchPrice()), assetUpdates, orderUpdates);
            if (handleOrderResult == false) {
                logger.error("成交回报处理订单失败：" + order.toString());
                replyErrorState();
                return null;
            }
        }
        userDataService.batchUpdateUserAsset(assetUpdates, orderUpdates, matchOrderRequest);
        replySuccessState();
        logger.info("成交回报整体耗时：" + (System.currentTimeMillis() - start));
        return null;
    }

    private boolean handleOrder(Order order, BigDecimal matchAmount, BigDecimal matchPrice, List<AssetUpdate> assetUpdates, List<OrderUpdate> orderUpdates) {
        logger.info("开始处理成交回报订单：" + order.toString());
        String assetPair = order.getAssetPair();
        String cargoCoin = assetPair.split("-")[0];
        String baseCoin = assetPair.split("-")[1];
        String account = order.getAccount();
        String feeRateString = order.getFeeRate();
        BigDecimal feeRate;
        if (feeRateString == null || feeRateString.isEmpty()) {
            feeRate = new BigDecimal(0);
        } else {
            feeRate = new BigDecimal(feeRateString);
        }
        BigDecimal orderPrice = new BigDecimal(order.getPrice());
        BigDecimal remain = new BigDecimal(order.getRemain());
        if (remain.compareTo(matchAmount) < 0) {
            logger.info("订单金额不足");
            return false;
        }
        if (order.getState() == OrderState.CANCLE || order.getState() == OrderState.COMPLETE) {
            logger.fatal("订单状态异常");
            return false;
        }
        if (order.getOrderSide() == OrderSide.BUY) {
            //1.买入，钱-》锁定的钱减少，钱总量减少，货物总量增多
            BigDecimal preLockMoneyAmount = orderPrice.multiply(matchAmount);//预先锁定的钱
            BigDecimal spendMoney = matchAmount.multiply(matchPrice);//实际花掉的钱
            AssetUpdate assetUpdate = new AssetUpdate(account, baseCoin, spendMoney.multiply(new BigDecimal(-1)).toString(), preLockMoneyAmount.subtract(spendMoney).toString(), new Date());
            assetUpdates.add(assetUpdate);
            //2.货物增加 //计算手续费
            BigDecimal fee = matchAmount.multiply(feeRate);
            BigDecimal amountToUser = matchAmount.subtract(fee);

            AssetUpdate cargoUpdate = new AssetUpdate(account, cargoCoin, amountToUser.toString(), amountToUser.toString(), new Date());
            assetUpdates.add(cargoUpdate);
            //3.更新订单
            OrderUpdate orderUpdate = new OrderUpdate(order.getOrderID(), matchAmount.toString());
            orderUpdates.add(orderUpdate);
            return true;
        } else if (order.getOrderSide() == OrderSide.SELL) {
            //卖出，货-》锁定的货钱少，钱总量增多
            AssetUpdate cargoUpdate = new AssetUpdate(account, cargoCoin, matchAmount.multiply(new BigDecimal(-1)).toString(), "0", new Date());
            assetUpdates.add(cargoUpdate);

            BigDecimal receiveMoney = matchAmount.multiply(matchPrice);
            BigDecimal fee = receiveMoney.multiply(feeRate);
            BigDecimal moneyToUser = receiveMoney.subtract(fee);

            AssetUpdate baseUpdate = new AssetUpdate(account, baseCoin, moneyToUser.toString(), moneyToUser.toString(), new Date());
            assetUpdates.add(baseUpdate);
            //更新订单

            OrderUpdate orderUpdate = new OrderUpdate(order.getOrderID(), matchAmount.toString());
            orderUpdates.add(orderUpdate);
            return true;
        } else {
            logger.error("订单异常");
            return false;
        }
    }

    private void replyErrorState() {
        logger.info("处理成交回报失败");
        //  MatchOrderReply matchOrderReply = MatchOrderReply.newBuilder().setState(false).build();
     /*   responseObserver.onNext(matchOrderReply);
        responseObserver.onCompleted();*/
        return;
    }

    private void replySuccessState() {
        logger.info("处理成交回报成功");
        //    MatchOrderReply matchOrderReply = MatchOrderReply.newBuilder().setState(true).build();
   /*     responseObserver.onNext(matchOrderReply);
        responseObserver.onCompleted();*/
        return;
    }

    private boolean tryToInitUserAsset(String asset, String account, BigDecimal amountToUser) {
        UserAsset userAsset = userDataService.queryUserAssert(account, asset);
        if (userAsset == null) {
            logger.info("用户:" + asset + "账户未初始化，自动初始化并转入成交份额");
            UserAsset newUserAssert = new UserAsset();
            newUserAssert.setAccount(account);
            newUserAssert.setAviliable(amountToUser.toString());
            newUserAssert.setTotalAmount(amountToUser.toString());
            newUserAssert.setLiquidationTime(new Date());
            newUserAssert.setUpdateTime(new Date());
            newUserAssert.setLockVersion(0);
            newUserAssert.setAsset(asset);
            userDataService.insertUserAsset(newUserAssert);
            return true;
        } else {
            logger.error("用户已初始化。");
            return false;
        }

    }

}
