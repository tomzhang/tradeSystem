package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.grpc.client.pool.TradeCoreClient;
import com.bernard.grpc.client.pool.TradeCoreClientPool;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.OrderSide;
import com.bernard.mysql.dto.OrderState;
import com.bernard.mysql.dto.OrderType;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradeCore.service.*;
import io.grpc.tradesystem.service.CancelOrderReply;
import io.grpc.tradesystem.service.CancelOrderRequest;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

public class UserCancelOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserCancelOrderTask.class);
    private StreamObserver<CancelOrderReply> responseObserver;
    private CancelOrderRequest cancelOrderRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");

    private UserCancelOrderTask() {

    }

    public UserCancelOrderTask(CancelOrderRequest cancelOrderRequest, StreamObserver<CancelOrderReply> responseObserver) {
        this.cancelOrderRequest = cancelOrderRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理撤单请求：" + cancelOrderRequest.toString());
        //1.queryOrder and checkOrder
        Order userOrder = null;
        try {
            userOrder = userDataService.queryUserOrder(cancelOrderRequest.getOrderId(), cancelOrderRequest.getAccount());
        } catch (Exception e) {
            logger.error("231", e);
        }
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
        if (account.equalsIgnoreCase(cancelOrderRequest.getAccount()) == false) {
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
        io.grpc.tradeCore.service.OrderSide rpcSide;
        if (userOrder.getOrderSide() == OrderSide.BUY) {
            rpcSide = io.grpc.tradeCore.service.OrderSide.BID;
        } else {
            rpcSide = io.grpc.tradeCore.service.OrderSide.ASK;
        }
        sendCancelToTradeCore(userOrder.getOrderID(), cargoCoin, baseCoin, account, rpcSide);

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
        CancelOrderReply cancelOrderReply = CancelOrderReply.newBuilder().setState(false).setMessage(false).build();
        responseObserver.onNext(cancelOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState() {
        CancelOrderReply cancelOrderReply = CancelOrderReply.newBuilder().setState(true).setMessage(true).build();
        responseObserver.onNext(cancelOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private Response sendCancelToTradeCore(String orderid, String cargoCoin, String baseCoin, String account, io.grpc.tradeCore.service.OrderSide rpcSide) {
        TradeCoreClient client = TradeCoreClientPool.borrowObject();
        AssetPair pair = AssetPair.newBuilder().setAsset(cargoCoin).setMoney(baseCoin).build();
        //AssetPair pair = AssetPair.newBuilder().setAsset("asset").setMoney("money").build();
        //Charge charge = Charge.newBuilder().setAmount(order.getAmount()).setPrice(order.getPrice()).build();
        //io.grpc.tradesystem.service.OrderSide orderSide1 = io.grpc.tradesystem.service.OrderSide.ASK;


        CancelOrderCmd cmd = CancelOrderCmd.newBuilder().setAccount(account).setAssetPair(pair).setUid(orderid).setSide(rpcSide).build();
        Response response = null;
        try {
            response = client.getBlockingStub().cancel(cmd);
        } catch (Exception e) {
            logger.error("调用撮合系统失败", e);
        }

        return response;
    }


}
