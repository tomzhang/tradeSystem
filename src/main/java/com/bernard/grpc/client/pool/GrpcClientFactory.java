package com.bernard.grpc.client.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GrpcClientFactory extends BasePooledObjectFactory<TradeCoreClient> {
    public static String host = "127.0.0.1";
    public static int port = 50051;
    @Override
    public TradeCoreClient create() throws Exception {
        return new TradeCoreClient(host, port);
    }

    @Override
    public PooledObject<TradeCoreClient> wrap(TradeCoreClient helloWorldClient) {
        return  new DefaultPooledObject<>(helloWorldClient);
        //return null;
    }

    @Override
    public void destroyObject(PooledObject<TradeCoreClient> p) throws Exception {
        p.getObject().shutdown();
        super.destroyObject(p);
    }
}
