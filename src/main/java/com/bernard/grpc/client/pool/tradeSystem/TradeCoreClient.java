package com.bernard.grpc.client.pool.tradeSystem;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.tradeCore.service.OrderGrpc;

import java.util.concurrent.TimeUnit;



public class TradeCoreClient {

    private final ManagedChannel channel;
    private OrderGrpc.OrderBlockingStub blockingStub;
    private long subTime;
    private static final int aviTime = 15;


    public TradeCoreClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();

        blockingStub = OrderGrpc.newBlockingStub(channel).withDeadlineAfter(aviTime, TimeUnit.SECONDS);
        subTime = System.currentTimeMillis();
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public OrderGrpc.OrderBlockingStub getBlockingStub() {
        if (System.currentTimeMillis() - subTime > 10 * 1000) {
            System.out.println("重建blockingstub");
            subTime = System.currentTimeMillis();
            blockingStub = OrderGrpc.newBlockingStub(channel).withDeadlineAfter(aviTime, TimeUnit.SECONDS);
            return blockingStub;
        } else {
            return blockingStub;
        }
    }



 /*   public  void greet (String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        try {
            HelloReply response = blockingStub.sayHello(request);
            System.out.println(response.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        atomicLong.incrementAndGet();
        //System.out.println(response.getMessage());

    }*/
/* public void takeOrder(UserOrderRequest){
     UserOrderRequest request = UserOrderRequest.newBuilder()
    UserOrderReply reply = blockingStub.takeOrder(UserOrderRequest);


 }*/




 /*   public static void main(String[] args) throws InterruptedException {

      HelloWorldClient client = new HelloWorldClient("127.0.0.1",50051);
      client.greet("123");

    }*/
}