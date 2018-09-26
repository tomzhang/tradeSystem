package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.cache.RedisKeys;
import com.bernard.cache.service.CacheService;
import com.bernard.globle.ReportStateManager;
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
    private BigDecimal buySideFee = new BigDecimal(0);
    private BigDecimal sellSideFee = new BigDecimal(0);


    private MatchOrderTask() {

    }

    public MatchOrderTask(MatchOrderRequest matchOrderRequest) {
        this.matchOrderRequest = matchOrderRequest;

    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理成交回报======" + matchOrderRequest.toString());
        //0.记录成交统计
        ReportStateManager.addTotal(matchOrderRequest.getAsset(), new BigDecimal(matchOrderRequest.getMatchAmount()));
        long start = System.currentTimeMillis();
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
        //System.out.println("buysideFee:"+ buySideFee.toString()+" sellSideFee:"+sellSideFee.toString());
        userDataService.batchUpdateMatchOrderTask(assetUpdates, orderUpdates, matchOrderRequest, buySideFee.toPlainString(), sellSideFee.toPlainString());
        replySuccessState();
        logger.info("成交回报整体耗时：" + (System.currentTimeMillis() - start));
        return null;
    }

    private boolean handleOrder(Order order, BigDecimal matchAmount, BigDecimal matchPrice, List<AssetUpdate> assetUpdates, List<OrderUpdate> orderUpdates) {
        //logger.info("开始处理成交回报订单：" + order.toString());
        String assetPair = order.getAssetPair();
        String cargoCoin = assetPair.split("-")[0];
        String baseCoin = assetPair.split("-")[1];
        String account = order.getAccount();
        String feeRateString = order.getFeeRate();
        String matchMoney = matchAmount.multiply(matchPrice).toString();
        logger.info("成交金额：" + matchMoney);
        BigDecimal feeRate;
        if (feeRateString == null || feeRateString.isEmpty()) {
            feeRate = new BigDecimal(0);
        } else {
            feeRate = new BigDecimal(feeRateString);
        }
        BigDecimal orderPrice = null;
        if (order.getOrderType() == OrderType.PRICE_LIMIT) {
            orderPrice = new BigDecimal(order.getPrice());
        }
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
            BigDecimal preLockMoneyAmount;
            if (order.getOrderType() == OrderType.PRICE_LIMIT) {
                preLockMoneyAmount = orderPrice.multiply(matchAmount);//预先锁定的钱
            } else {
                preLockMoneyAmount = matchAmount.multiply(matchPrice);
            }
            BigDecimal spendMoney = matchAmount.multiply(matchPrice);//实际花掉的钱
            AssetUpdate assetUpdate = new AssetUpdate(account, baseCoin, spendMoney.multiply(new BigDecimal(-1)).toString(), preLockMoneyAmount.subtract(spendMoney).toString(), new Date());
            assetUpdates.add(assetUpdate);
            //2.货物增加 //计算手续费
            BigDecimal fee = matchAmount.multiply(feeRate);
            BigDecimal amountToUser = matchAmount.subtract(fee);
            buySideFee = fee;

            //2.1 记录手续费
            ReportStateManager.addFee(cargoCoin, fee);
            //

            AssetUpdate cargoUpdate = new AssetUpdate(account, cargoCoin, amountToUser.toString(), amountToUser.toString(), new Date());
            assetUpdates.add(cargoUpdate);
            //3.更新订单
            OrderUpdate orderUpdate = new OrderUpdate(order.getOrderID(), matchAmount.toString(), matchMoney);
            orderUpdates.add(orderUpdate);
            return true;
        } else if (order.getOrderSide() == OrderSide.SELL) {
            //卖出，货-》锁定的货钱少，钱总量增多
            AssetUpdate cargoUpdate = new AssetUpdate(account, cargoCoin, matchAmount.multiply(new BigDecimal(-1)).toString(), "0", new Date());
            assetUpdates.add(cargoUpdate);

            BigDecimal receiveMoney = matchAmount.multiply(matchPrice);
            BigDecimal fee = receiveMoney.multiply(feeRate);
            BigDecimal moneyToUser = receiveMoney.subtract(fee);
            sellSideFee = fee;

            //统计手续费
            ReportStateManager.addFee(baseCoin, fee);

            AssetUpdate baseUpdate = new AssetUpdate(account, baseCoin, moneyToUser.toString(), moneyToUser.toString(), new Date());
            assetUpdates.add(baseUpdate);
            //更新订单

            OrderUpdate orderUpdate = new OrderUpdate(order.getOrderID(), matchAmount.toString(), matchMoney);
            orderUpdates.add(orderUpdate);
            return true;
        } else {
            logger.error("订单异常");
            return false;
        }
    }

    private void replyErrorState() {
        logger.info("处理成交回报失败");
        return;
    }

    private void replySuccessState() {
        logger.info("处理成交回报成功");
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
