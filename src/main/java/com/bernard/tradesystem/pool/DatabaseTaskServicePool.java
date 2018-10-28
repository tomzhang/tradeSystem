package com.bernard.tradesystem.pool;

import java.util.concurrent.*;

public class DatabaseTaskServicePool {
    private static LinkedBlockingQueue queue = new LinkedBlockingQueue<Runnable>(50000);
    private static ExecutorService fixedThreadPool = new ThreadPoolExecutor(32, 32, 1000L, TimeUnit.MILLISECONDS, queue);

    public static void submitTask(FutureTask<Integer> futureTask) {
        fixedThreadPool.submit(futureTask);
    }

    public static int getTaskQueueSize() {
        return queue.size();
    }
}
