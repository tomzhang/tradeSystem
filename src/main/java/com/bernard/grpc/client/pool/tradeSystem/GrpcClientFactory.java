package com.bernard.grpc.client.pool.tradeSystem;

import com.bernard.App;
import com.bernard.common.config.TradeSystemConfig;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GrpcClientFactory extends BasePooledObjectFactory<TradeCoreClient> {
    TradeSystemConfig config = (TradeSystemConfig) App.context.getBean("tradeSystemConfig");
    public static String host = "10.0.1.20";
    public static int port = 20039;
    @Override
    public TradeCoreClient create() throws Exception {
        return new TradeCoreClient(config.getTradeCoreHost(), Integer.parseInt(config.getTradeCorePort()));
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
