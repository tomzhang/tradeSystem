package com.bernard;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;


public class HelloWorldClient {
    public static AtomicLong atomicLong = new AtomicLong(0);

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;


    public HelloWorldClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel).withDeadlineAfter(15,TimeUnit.SECONDS);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public GreeterGrpc.GreeterBlockingStub getBlockingStub() {
        return blockingStub;
    }

    public  void greet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        try {
            HelloReply response = blockingStub.sayHello(request);
            System.out.println(response.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
        atomicLong.incrementAndGet();
        //System.out.println(response.getMessage());

    }

    public static void main(String[] args) throws InterruptedException {

      HelloWorldClient client = new HelloWorldClient("127.0.0.1",50051);
      client.greet("123");

    }
}