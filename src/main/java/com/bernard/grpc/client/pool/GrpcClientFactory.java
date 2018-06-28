package com.bernard.grpc.client.pool;

import com.bernard.HelloWorldClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GrpcClientFactory extends BasePooledObjectFactory<HelloWorldClient> {
    private String host="127.0.0.1";
    private int port =50051;
    @Override
    public HelloWorldClient create() throws Exception {
        return  new HelloWorldClient(host,port);
    }

    @Override
    public PooledObject<HelloWorldClient> wrap(HelloWorldClient helloWorldClient) {
        return  new DefaultPooledObject<>(helloWorldClient);
        //return null;
    }

    @Override
    public void destroyObject(PooledObject<HelloWorldClient> p) throws Exception {
        p.getObject().shutdown();
        super.destroyObject(p);
    }
}
