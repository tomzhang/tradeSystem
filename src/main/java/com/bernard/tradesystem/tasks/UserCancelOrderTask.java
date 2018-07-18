package com.bernard.tradesystem.tasks;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.OrderSide;
import com.bernard.mysql.dto.OrderState;
import com.bernard.mysql.dto.OrderType;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.CancleOrderReply;
import io.grpc.tradesystem.service.CancleOrderRequest;
import io.grpc.tradesystem.service.UserOrderReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

public class UserCancelOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserCancelOrderTask.class);
    private StreamObserver<CancleOrderReply> responseObserver;
    private CancleOrderRequest cancleOrderRequest;
    @Autowired
    private UserDataService userDataService;

    private UserCancelOrderTask() {

    }

    public UserCancelOrderTask(CancleOrderRequest cancleOrderRequest, StreamObserver<CancleOrderReply> responseObserver) {
        this.cancleOrderRequest = cancleOrderRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理撤单请求：" + cancleOrderRequest.toString());
        //1.queryOrder and checkOrder
        Order userOrder = userDataService.queryUserOrder(cancleOrderRequest.getOrderId(), cancleOrderRequest.getAccount());
        if (userOrder == null) {
            replyErrorState();
            return null;
        }
        if (userOrder.getState() == OrderState.COMPLETE) {
            replyErrorState();
            return null;
        }
        String assetPair = userOrder.getAssetPair();
        String cargoCoin = assetPair.split("-")[0];
        String baseCoin = assetPair.split("-")[1];
        String account = userOrder.getAccount();
        if (account.equalsIgnoreCase(cancleOrderRequest.getAccount()) == false) {
            replyErrorState();
            return null;
        }
        BigDecimal remain = new BigDecimal(userOrder.getRemain());
        BigDecimal price = new BigDecimal(userOrder.getPrice());
        if (remain.compareTo(BigDecimal.ZERO) == 0) {
            replyErrorState();
            logger.fatal("订单余额为0，但是未关闭！");
            return null;
        }
        //
        if (userOrder.getOrderType() == OrderType.PRICE_LIMIT) {
            if (userOrder.getOrderSide() == OrderSide.BUY) {
                //撤销买单 需要释放锁定的钱
                BigDecimal moneyToUnlock = remain.multiply(price);
                userDataService.updateUserAssert(account, baseCoin, "0", moneyToUnlock.toString(), new Date());

            } else {
                //撤销卖单
                userDataService.updateUserAssert(account, cargoCoin, "0", remain.toString(), new Date());
            }
        } else {
            logger.fatal("不支持，非限价订单");
            return null;
        }
        //2.发送撤单请求至撮合系统

        //3.更新用户订单
        userOrder.setState(OrderState.CANCLE);
        userOrder.setRemain("0");
        userOrder.setLockVersion(userOrder.getLockVersion() + 1);
        int updateOrderResult = userDataService.updateUserOrder(userOrder);
        if (updateOrderResult != 1) {
            logger.error("更新用户订单失败");
            return null;
        }
        replySuccessState();
        return null;
    }

    private void replyErrorState() {
        CancleOrderReply cancleOrderReply = CancleOrderReply.newBuilder().setState(false).setMessage(false).build();
        responseObserver.onNext(cancleOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState() {
        CancleOrderReply cancleOrderReply = CancleOrderReply.newBuilder().setState(true).setMessage(true).build();
        responseObserver.onNext(cancleOrderReply);
        responseObserver.onCompleted();
        return;
    }


}
