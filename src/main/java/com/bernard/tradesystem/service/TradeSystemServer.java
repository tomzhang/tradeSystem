package com.bernard.tradesystem.service;


import com.bernard.App;
import com.bernard.common.config.TradeSystemConfig;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import org.apache.log4j.Logger;

import java.io.IOException;

public class TradeSystemServer {
    private static Logger logger = Logger.getLogger(TradeSystemServer.class);


    private int port = 50051;
    private Server server;

    public void start() throws IOException {
        TradeSystemConfig config = (TradeSystemConfig) App.context.getBean("tradeSystemConfig");
        server = ServerBuilder.forPort(Integer.parseInt(config.getGrpcPort()))
                .addService(new TradeSystemService())
                .build()
                .start();

        logger.info("交易柜台GRPC服务开启,服务端口：" + port);

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


}
