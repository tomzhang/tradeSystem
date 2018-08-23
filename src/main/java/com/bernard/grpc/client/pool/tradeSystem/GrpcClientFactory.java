package com.bernard.grpc.client.pool.tradeSystem;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GrpcClientFactory extends BasePooledObjectFactory<TradeCoreClient> {
    public static String host = "172.20.10.13";
    public static int port = 20039;
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
