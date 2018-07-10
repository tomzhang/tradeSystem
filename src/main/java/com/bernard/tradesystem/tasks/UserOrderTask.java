package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.common.error.ErrorType;
import com.bernard.grpc.client.pool.TradeCoreClient;
import com.bernard.grpc.client.pool.TradeCoreClientPool;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.dto.OrderSide;
import com.bernard.mysql.dto.OrderType;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradeCore.service.AssetPair;
import io.grpc.tradeCore.service.Charge;
import io.grpc.tradeCore.service.Response;
import io.grpc.tradeCore.service.TakeOrderCmd;
import io.grpc.tradesystem.service.*;
import org.apache.log4j.Logger;


import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

public class UserOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserOrderTask.class);
    private StreamObserver<UserOrderReply> responseObserver;
    private Order order;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");

    private UserOrderTask() {

    }

    public UserOrderTask(Order order, StreamObserver<UserOrderReply> responseObserver) {
        this.order = order;
        this.responseObserver = responseObserver;

    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理用户订单");
        //获取币对
        String assetPair = order.getAssetPair();
        String cargoCoin = assetPair.split("-")[0];
        String baseCoin = assetPair.split("-")[1];
        OrderSide orderSide = order.getOrderSide();
        if (orderSide == OrderSide.BUY) {
            BigDecimal neededCoin = new BigDecimal(order.getAmount()).multiply(new BigDecimal(order.getPrice()));
            boolean processResult = processUserOrder(order.getAccount(), baseCoin, neededCoin, order.getOrderType());
            //boolean processResult=true;
            if (processResult == true) {
                sendOrderToTradeCore(cargoCoin, baseCoin);
                try {
                    userDataService.insertUserOrder(order);
                } catch (Exception e) {
                    logger.error("插入订单失败", e);
                }

                replySucessState();
            } else {
                replyErrorState();
            }
        } else {
            //卖出锁定cargoCoin
            BigDecimal needCoin = new BigDecimal(order.getAmount());
            boolean processResult = processUserOrder(order.getAccount(), cargoCoin, needCoin, order.getOrderType());
            if (processResult == true) {
                sendOrderToTradeCore(cargoCoin, baseCoin);
                userDataService.insertUserOrder(order);
                replySucessState();
            } else {
                replyErrorState();
            }
        }
        return null;
    }

    /**
     * @param account
     * @param amountToLock
     * @param orderType
     * @param coinToLock
     * @return 锁定成功返回true
     */
    private boolean processUserOrder(String account, String coinToLock, BigDecimal amountToLock, OrderType orderType) {
        if (orderType == OrderType.PRICE_LIMIT) {
            //查询资产
            UserAsset userAsset;
            try {
                userAsset = userDataService.queryUserAssert(order.getAccount(), coinToLock);
            } catch (Exception e) {
                logger.error("查询数据库失败", e);
                return false;
            }

            if (userAsset == null) {
                logger.error("查询个人资产失败：coin:" + coinToLock);
                return false;

            }
            BigDecimal available = new BigDecimal(userAsset.getAviliable());
            if (available.compareTo(amountToLock) < 0) {
                logger.error(ErrorType.Insufficient.getMessage());
                return false;
            }
            int updateCount = userDataService.lockUserAssert(account, coinToLock, userAsset.getTotalAmount(), available.toString(), available.subtract(amountToLock).toString(), userAsset.getLockVersion(), userAsset.getLockVersion() + 1, new Date());


            if (updateCount != 1) {
                //更新失败 下单失败
                logger.error("乐观锁更新失败：" + order.toString());
                return false;
            }
            return true;
        } else {
            //TODO
            return false;
        }

    }

    private void replyErrorState() {
        UserOrderReply userOrderReply = UserOrderReply.newBuilder().setState(false).setOrderId(order.getOrderID()).build();
        responseObserver.onNext(userOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySucessState() {
        UserOrderReply userOrderReply = UserOrderReply.newBuilder().setState(false).setOrderId(order.getOrderID()).build();
        responseObserver.onNext(userOrderReply);
        responseObserver.onCompleted();
        return;
    }


    private Response sendOrderToTradeCore(String cargoCoin, String baseCoin) {
        TradeCoreClient client = TradeCoreClientPool.borrowObject();
        //AssetPair pair=AssetPair.newBuilder().setAsset(cargoCoin).setMoney(baseCoin).build();
        AssetPair pair = AssetPair.newBuilder().setAsset("asset").setMoney("money").build();
        Charge charge = Charge.newBuilder().setAmount(order.getAmount()).setPrice(order.getPrice()).build();
        //io.grpc.tradesystem.service.OrderSide orderSide1 = io.grpc.tradesystem.service.OrderSide.ASK;
        io.grpc.tradeCore.service.OrderSide rpcSide;
        io.grpc.tradeCore.service.OrderType rpcType;
        if (order.getOrderSide() == OrderSide.BUY) {
            rpcSide = io.grpc.tradeCore.service.OrderSide.BID;
        } else {
            rpcSide = io.grpc.tradeCore.service.OrderSide.ASK;
        }
        if (order.getOrderType() == OrderType.PRICE_LIMIT) {
            rpcType = io.grpc.tradeCore.service.OrderType.LIMIT;
        } else {
            rpcType = io.grpc.tradeCore.service.OrderType.MARKET;
        }
        TakeOrderCmd cmd = TakeOrderCmd.newBuilder().setAccount(order.getAccount()).setAssetPair(pair).setUid(order.getOrderID()).setCharge(charge).setSide(rpcSide).setType(rpcType).build();
        Response response = null;
        try {
            response = client.getBlockingStub().take(cmd);
        } catch (Exception e) {
            logger.error("调用撮合系统失败", e);
        }

        return response;
    }



}
