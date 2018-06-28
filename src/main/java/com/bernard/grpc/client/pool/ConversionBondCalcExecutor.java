package com.bernard.grpc.client.pool;

import java.util.concurrent.*;

public class ConversionBondCalcExecutor {
    private static ExecutorService fixedThreadPool=  new ThreadPoolExecutor(32,32,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100000));

    public static void submitTask(FutureTask<Integer> futureTask){
        fixedThreadPool.submit(futureTask);
    }

}
