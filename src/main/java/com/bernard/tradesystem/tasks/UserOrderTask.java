package com.bernard.tradesystem.tasks;

import com.bernard.common.error.ErrorType;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.UserOrderReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;

public class UserOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserOrderTask.class);
    private StreamObserver<UserOrderReply> responseObserver;
    private Order order;
    @Autowired
    private UserDataService userDataService;

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
            if (processResult == true) {
                userDataService.insertUserOrder(order);
                replySucessState();
            } else {
                replyErrorState();
            }
        } else {
            //卖出锁定cargoCoin
            BigDecimal needCoin = new BigDecimal(order.getAmount());
            boolean processResult = processUserOrder(order.getAccount(), cargoCoin, needCoin, order.getOrderType());
            if (processResult == true) {
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
            UserAsset userAsset = userDataService.queryUserAssert(order.getAccount(), coinToLock);
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



}
