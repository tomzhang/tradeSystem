package com.bernard.test;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.tradeCore.service.OrderGrpc;
import io.grpc.tradesystem.service.TradeSystemGrpc;
import io.grpc.tradesystem.service.UserOrderReply;
import io.grpc.tradesystem.service.UserOrderRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestGrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();

        TradeSystemGrpc.TradeSystemBlockingStub blockingStub = TradeSystemGrpc.newBlockingStub(channel).withDeadlineAfter(15, TimeUnit.SECONDS);
        UserOrderRequest request = UserOrderRequest.newBuilder().setAccount("zzz666").setAmount("1").setAssertLimit("").setAssetPair("ETH-BTC").setOrderSide("BUY")
                .setOrderType("PRICE_LIMIT").setPrice("1").build();
        UserOrderReply reply = blockingStub.takeOrder(request);
        System.out.println(reply.getState());


    }

}
