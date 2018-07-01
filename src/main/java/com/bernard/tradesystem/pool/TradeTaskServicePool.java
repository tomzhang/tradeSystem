package com.bernard.tradesystem.pool;

import java.util.concurrent.*;

public class TradeTaskServicePool {
    private static ExecutorService fixedThreadPool=  new ThreadPoolExecutor(32,32,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(3000));

    public static void submitTask(FutureTask<Integer> futureTask){
        fixedThreadPool.submit(futureTask);
    }

}
