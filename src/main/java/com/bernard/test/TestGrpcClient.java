package com.bernard.test;

import com.bernard.tradesystem.tasks.MatchOrderTask;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.tradeCore.service.OrderGrpc;
import io.grpc.tradesystem.service.MatchOrderRequest;
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

        TradeSystemGrpc.TradeSystemBlockingStub blockingStub = TradeSystemGrpc.newBlockingStub(channel);
        UserOrderRequest request = UserOrderRequest.newBuilder().setAccount("zzz666").setAmount("1").setAssertLimit("").setAssetPair("ETH-BTC").setOrderSide("SELL")
                .setOrderType("PRICE_LIMIT").setPrice("1").setFeeRate("0.003").build();
        for (int i = 0; i < 1000; i++) {
            UserOrderReply reply = blockingStub.takeOrder(request);
            System.out.println("收到回复" + i + ":" + reply.getState());
        }
       /* MatchOrderRequest request1 = MatchOrderRequest.newBuilder().setBuySideOrderId("f956e8b6-bb0d-4e2f-8da4-807a85ee0aa0")
                .setBuySideAccount("zzz666").setSellSideOrderId("f64b72da-afb3-44aa-90bb-5445ad82576b")
                .setSellSideAccount("zzz666").setMatchOrderWaterflow(System.currentTimeMillis() + "").setMatchAmount("1").setMatchPrice("1").build();
        blockingStub.stepOrder(request1);*/

        //f956e8b6-bb0d-4e2f-8da4-807a85ee0aa0
        //f64b72da-afb3-44aa-90bb-5445ad82576b
        //System.out.println(reply.getState());


    }

}
