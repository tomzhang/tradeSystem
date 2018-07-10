package com.bernard.tradesystem.service;

import io.grpc.stub.StreamObserver;
import io.grpc.tradeCore.service.CancelOrderCmd;
import io.grpc.tradeCore.service.OrderGrpc;
import io.grpc.tradeCore.service.Response;
import io.grpc.tradeCore.service.TakeOrderCmd;
import io.grpc.tradesystem.service.*;

public class TradeCoreService extends OrderGrpc.OrderImplBase {

    @Override
    public void take(TakeOrderCmd request, StreamObserver<Response> responseObserver) {
        super.take(request, responseObserver);
    }

    @Override
    public void cancel(CancelOrderCmd request, StreamObserver<Response> responseObserver) {
        super.cancel(request, responseObserver);
    }
}
