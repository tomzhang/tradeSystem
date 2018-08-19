package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.OrderSide;
import com.bernard.mysql.dto.OrderState;
import com.bernard.mysql.dto.UserAsset;
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
    private StreamObserver<MatchOrderReply> responseObserver;
    private MatchOrderRequest matchOrderRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");


    private MatchOrderTask() {

    }

    public MatchOrderTask(MatchOrderRequest matchOrderRequest, StreamObserver<MatchOrderReply> responseObserver) {
        this.matchOrderRequest = matchOrderRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理成交回报");
        //0.插入成交流水
        try {
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
        }
        //1.查询订单
        List<Order> matchOrders = new ArrayList<>();
        try {
            matchOrders = userDataService.queryMatchOrders(matchOrderRequest.getBuySideOrderId(), matchOrderRequest.getSellSideOrderId());
            if (matchOrders.size() != 2) {
                logger.fatal("成交单号匹配错误");
                replyErrorState();
                return null;
            }
        } catch (Exception e) {
            logger.error("查询成交订单失败", e);
            replyErrorState();
            return null;
        }
        //2.处理订单
        for (Order order : matchOrders) {
            boolean handleOrderResult = handleOrder(order, new BigDecimal(matchOrderRequest.getMatchAmount()), new BigDecimal(matchOrderRequest.getMatchPrice()));
            if (handleOrderResult == false) {
                logger.error("处理订单失败：" + order.toString());
                replyErrorState();
                return null;
            }
        }
        replySucessState();
        return null;
    }

    private boolean handleOrder(Order order, BigDecimal matchAmount, BigDecimal matchPrice) {
        logger.info("开始处理订单：" + order.toString());
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
            int updateMoneyAmount = userDataService.updateUserAssert(account, baseCoin, spendMoney.multiply(new BigDecimal(-1)).toString(), preLockMoneyAmount.subtract(spendMoney).toString(), new Date());
            //买入成交用户的钱记录未初始化的情况，因为下单的时候就锁定钱
            if (updateMoneyAmount != 1) {
                logger.fatal("1.更新用户资产失败");
                return false;
            }
            //2.货物增加 //计算手续费
            BigDecimal fee = matchAmount.multiply(feeRate);
            BigDecimal amountToUser = matchAmount.subtract(fee);

            int updateCargoAmount = userDataService.updateUserAssert(account, cargoCoin, amountToUser.toString(), amountToUser.toString(), new Date());
            if (updateCargoAmount != 1) {
                Boolean initResult = false;
                if (updateCargoAmount == 0) {
                    initResult = tryToInitUserAsset(cargoCoin, account, amountToUser);
                }
                if (initResult == false) {
                    logger.fatal("2.更新用户资产失败");
                    return false;
                }
            }
            int updateFeeFlow = userDataService.insertOrderFee(order.getOrderID(), order.getAssetPair(), fee.toString(), new Date(), matchOrderRequest.getMatchOrderWaterflow(), cargoCoin);
            if (updateFeeFlow != 1) {
                logger.fatal("3.更新手续费失败");
                return false;
            }
            //3.更新订单
            remain = remain.subtract(matchAmount);
            order.setLockVersion(order.getLockVersion() + 1);
            order.setRemain(remain.toString());
            if (remain.compareTo(BigDecimal.ZERO) == 0) {
                order.setState(OrderState.COMPLETE);
            } else {
                order.setState(OrderState.PARTITION);
            }
            int updateCount = userDataService.updateUserOrder(order);
            if (updateCount != 1) {
                logger.fatal("更新订单失败");
                return false;
            }
            return true;
        } else if (order.getOrderSide() == OrderSide.SELL) {
            //卖出，货-》锁定的货钱少，钱总量增多
            int updateCargoResult = userDataService.updateUserAssert(account, cargoCoin, matchAmount.multiply(new BigDecimal(-1)).toString(),
                    "0", new Date());
            if (updateCargoResult != 1) {
                logger.fatal("1.更新资产失败");
                return false;
            }
            BigDecimal receiveMoney = matchAmount.multiply(matchPrice);
            BigDecimal fee = receiveMoney.multiply(feeRate);
            BigDecimal moneyToUser = receiveMoney.subtract(fee);
            int updateMoneyResult = userDataService.updateUserAssert(account, baseCoin, moneyToUser.toString(), moneyToUser.toString(), new Date());
            if (updateMoneyResult != 1) {
                boolean initResult = false;
                if (updateMoneyResult == 0) {
                    logger.debug("用户资产未初始化");
                    initResult = tryToInitUserAsset(baseCoin, account, moneyToUser);
                }
                if (initResult == false) {
                    logger.fatal("2.更新资产失败");
                    return false;
                }
            }
            int updateFeeFlow = userDataService.insertOrderFee(order.getOrderID(), order.getAssetPair(), fee.toString(), new Date(), matchOrderRequest.getMatchOrderWaterflow(), baseCoin);
            if (updateFeeFlow != 1) {
                logger.fatal("3.插入手续费记录失败");
                return false;
            }
            //更新订单
            remain = remain.subtract(matchAmount);
            order.setLockVersion(order.getLockVersion() + 1);
            order.setRemain(remain.toString());
            if (remain.compareTo(BigDecimal.ZERO) == 0) {
                order.setState(OrderState.COMPLETE);
            } else {
                order.setState(OrderState.PARTITION);
            }
            userDataService.updateUserOrder(order);
            return true;
        } else {
            logger.error("订单异常");
            return false;
        }
    }

    private void replyErrorState() {
        MatchOrderReply matchOrderReply = MatchOrderReply.newBuilder().setState(false).build();
        responseObserver.onNext(matchOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySucessState() {
        MatchOrderReply matchOrderReply = MatchOrderReply.newBuilder().setState(true).build();
        responseObserver.onNext(matchOrderReply);
        responseObserver.onCompleted();
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
