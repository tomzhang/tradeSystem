package com.bernard;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.CancleOrderReply;
import io.grpc.tradesystem.service.CancleOrderRequest;
import io.grpc.tradesystem.service.TradeSystemGrpc;


import java.io.IOException;

public class TradeSystemServer {


    private int port = 50051;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                TradeSystemServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        final TradeSystemServer server = new TradeSystemServer();
        server.start();
        server.blockUntilShutdown();
    }


    // 实现 定义一个实现服务接口的类
    private class GreeterImpl extends TradeSystemGrpc.TradeSystemImplBase {
        @Override
        public void cancelOrder(CancleOrderRequest request, StreamObserver<CancleOrderReply> responseObserver) {



        }



    }
}
