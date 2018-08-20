package com.bernard.tradesystem.service;

import com.bernard.mysql.dto.Order;
import com.bernard.tradesystem.pool.TradeTaskServicePool;
import com.bernard.tradesystem.tasks.*;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.*;

import java.util.concurrent.FutureTask;

public class TradeSystemService extends TradeSystemGrpc.TradeSystemImplBase {


    @Override
    public void takeOrder(UserOrderRequest request, StreamObserver<UserOrderReply> responseObserver) {
        Order userOrder = Order.fromUserOrderRequest(request);
        UserOrderTask task = new UserOrderTask(userOrder, responseObserver);
        FutureTask futureTask = new FutureTask(task);
        TradeTaskServicePool.submitTask(futureTask);
    }

    @Override
    public void cancelOrder(CancelOrderRequest request, StreamObserver<CancelOrderReply> responseObserver) {
        UserCancelOrderTask cancelOrderTask = new UserCancelOrderTask(request, responseObserver);
        FutureTask futureTask = new FutureTask(cancelOrderTask);
        TradeTaskServicePool.submitTask(futureTask);
    }

    @Override
    public void stepOrder(MatchOrderRequest request, StreamObserver<MatchOrderReply> responseObserver) {
        MatchOrderTask matchOrderTask = new MatchOrderTask(request, responseObserver);
        FutureTask futureTask = new FutureTask(matchOrderTask);
        TradeTaskServicePool.submitTask(futureTask);
    }

    @Override
    public void transferIn(TransferInRequest request, StreamObserver<TransferInReply> responseObserver) {
        TransferInTask transferInTask = new TransferInTask(request, responseObserver);
        FutureTask futureTask = new FutureTask(transferInTask);
        TradeTaskServicePool.submitTask(futureTask);
    }

    @Override
    public void transferOut(TransferOutRequest request, StreamObserver<TransferOutReply> responseObserver) {
        TransferOutTask transferOutTask = new TransferOutTask(request, responseObserver);
        FutureTask futureTask = new FutureTask(transferOutTask);
        TradeTaskServicePool.submitTask(futureTask);
    }

    @Override
    public void getAddr(GetTransferInAddrRequest request, StreamObserver<GetTransferInAddrReply> responseObserver) {
        GetTransferAddrTask getTransferAddrTask = new GetTransferAddrTask(request, responseObserver);
        FutureTask futureTask = new FutureTask(getTransferAddrTask);
        TradeTaskServicePool.submitTask(futureTask);
    }
}
