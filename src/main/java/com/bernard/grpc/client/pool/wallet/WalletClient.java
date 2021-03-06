package com.bernard.grpc.client.pool.wallet;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.walletcore.service.WalletServiceGrpc;


import java.util.concurrent.TimeUnit;



public class WalletClient {

    private final ManagedChannel channel;
    private WalletServiceGrpc.WalletServiceBlockingStub blockingStub;
    private long subTime;
    private static final int aviTime = 15;


    public WalletClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();

        blockingStub = WalletServiceGrpc.newBlockingStub(channel).withDeadlineAfter(aviTime, TimeUnit.SECONDS);
        subTime = System.currentTimeMillis();
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public WalletServiceGrpc.WalletServiceBlockingStub getBlockingStub() {
        if (System.currentTimeMillis() - subTime > 10 * 1000) {
            //System.out.println("重建blockingstub");
            subTime = System.currentTimeMillis();
            blockingStub = WalletServiceGrpc.newBlockingStub(channel).withDeadlineAfter(aviTime, TimeUnit.SECONDS);
            return blockingStub;
        } else {
            return blockingStub;
        }
    }
}
