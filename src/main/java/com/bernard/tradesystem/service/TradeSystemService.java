package com.bernard.tradesystem.service;

import com.bernard.mysql.dto.Order;
import com.bernard.tradesystem.pool.TradeTaskServicePool;
import com.bernard.tradesystem.tasks.UserCancelOrderTask;
import com.bernard.tradesystem.tasks.UserOrderTask;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.*;

import java.util.concurrent.FutureTask;

public class TradeSystemService extends TradeSystemGrpc.TradeSystemImplBase {


    @Override
    public void takeOrder(UserOrderRequest request, StreamObserver<UserOrderReply> responseObserver) {
        //1.check request
        Order userOrder = Order.fromUserOrderRequest(request);
        UserOrderTask task = new UserOrderTask(userOrder, responseObserver);
        FutureTask futureTask = new FutureTask(task);
        TradeTaskServicePool.submitTask(futureTask);


    }

    @Override
    public void cancelOrder(CancleOrderRequest request, StreamObserver<CancleOrderReply> responseObserver) {
        //super.cancelOrder(request, responseObserver);
        UserCancelOrderTask cancelOrderTask = new UserCancelOrderTask(request, responseObserver);
        FutureTask futureTask = new FutureTask(cancelOrderTask);
        TradeTaskServicePool.submitTask(futureTask);
    }

    @Override
    public void matchOrder(MatchOrderRequest request, StreamObserver<MatchOrderReply> responseObserver) {
        //

    }
}
