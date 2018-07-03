package com.bernard.tradesystem.service;

import com.bernard.mysql.dto.Order;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.*;

public class TradeSystemService extends TradeSystemGrpc.TradeSystemImplBase {


    @Override
    public void takeOrder(UserOrderRequest request, StreamObserver<UserOrderReply> responseObserver) {
        //1.check request
        Order userOrder = Order.fromUserOrderRequest(request);
        //TODO 2.校验用户资产


    }

    @Override
    public void cancelOrder(CancleOrderRequest request, StreamObserver<CancleOrderReply> responseObserver) {
        super.cancelOrder(request, responseObserver);
    }
}
