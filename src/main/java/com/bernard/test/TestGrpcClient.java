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
        ManagedChannel channel1 = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();
        ManagedChannel channel2 = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext(true)
                .build();


        TradeSystemGrpc.TradeSystemBlockingStub blockingStub = TradeSystemGrpc.newBlockingStub(channel);
        TradeSystemGrpc.TradeSystemBlockingStub blockingStub1 = TradeSystemGrpc.newBlockingStub(channel1);
        TradeSystemGrpc.TradeSystemBlockingStub blockingStub2 = TradeSystemGrpc.newBlockingStub(channel2);

        UserOrderRequest request = UserOrderRequest.newBuilder().setAccount("zzz666").setAmount("1").setAssertLimit("").setAssetPair("ETH-BTC").setOrderSide("SELL")
                .setOrderType("PRICE_LIMIT").setPrice("1").setFeeRate("0.003").build();
        MatchOrderRequest matchOrderRequest = MatchOrderRequest.newBuilder().setMatchPrice("1").setMatchAmount("1")
                .setSellSideOrderId("b908dd87-8008-441f-b438-bef69d82d7ed").setSellSideAccount("bernard10060@yahoo.com")
                .setBuySideOrderId("af5e4ca6-12a2-478a-82c8-868c37d1dd0d").setBuySideAccount("bernard10060@yahoo.com").setMatchOrderWaterflow("948da559-abf2-488a-8023-f7336c2063e2")
                .setAsset("ETH-BTC").setBuySideOrderLeft("0").setSellSideOrderLeft("0").build();
        blockingStub.stepOrder(matchOrderRequest);
        /*Thread thread1 = new Thread() {
            @Override
            public void run() {
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

            }
        };*/


        // thread6.start();
        // thread7.start();

        /*for (int i = 0; i < 1000; i++) {
            UserOrderReply reply = blockingStub.takeOrder(request);
            System.out.println("收到回复" + i + ":" + reply.getState());
            UserOrderReply reply1= blockingStub1.takeOrder(request);
            System.out.println("收到回复" + i + ":" + reply1.getState());
            UserOrderReply reply2=blockingStub2.takeOrder(request);
            System.out.println("收到回复" + i + ":" + reply2.getState());
        }*/
       /* MatchOrderRequest request1 = MatchOrderRequest.newBuilder().setBuySideOrderId("f956e8b6-bb0d-4e2f-8da4-807a85ee0aa0")
                .setBuySideAccount("zzz666").setSellSideOrderId("f64b72da-afb3-44aa-90bb-5445ad82576b")
                .setSellSideAccount("zzz666").setMatchOrderWaterflow(System.currentTimeMillis() + "").setMatchAmount("1").setMatchPrice("1").build();
        blockingStub.stepOrder(request1);*/

        //f956e8b6-bb0d-4e2f-8da4-807a85ee0aa0
        //f64b72da-afb3-44aa-90bb-5445ad82576b
        //System.out.println(reply.getState());


    }

}
