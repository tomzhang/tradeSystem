package com.bernard.tradesystem.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;

public class TradeCoreServer {
    private static Logger logger = Logger.getLogger(TradeCoreServer.class);


    private int port = 8080;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new TradeCoreService())
                .build()
                .start();

        logger.info("交易柜台GRPC服务开启,服务端口：" + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                TradeCoreServer.this.stop();
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

    public static void main(String[] args) throws Exception {
        final TradeCoreServer server = new TradeCoreServer();
        server.start();
        server.blockUntilShutdown();
    }
}
