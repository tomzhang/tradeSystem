package com.bernard.grpc.client.pool;


import com.bernard.HelloWorldClient;

import java.util.concurrent.Callable;


public class ConvertibleBondTask implements Callable{
    //private static Logger logger = Logger.getLogger(ConvertibleBondTask.class);
    private int i;

    private ConvertibleBondTask(){

    }
    public ConvertibleBondTask(int i){
        this.i =i;
    }


    public Object call() throws Exception {
      HelloWorldClient client= HelloWorldClientPool.borrowObject();
        client.greet("world:"+i);
        HelloWorldClientPool.returnObject(client);
        return null;
    }


}
