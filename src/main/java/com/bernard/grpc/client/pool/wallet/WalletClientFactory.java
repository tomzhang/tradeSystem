package com.bernard.grpc.client.pool.wallet;

import com.bernard.App;
import com.bernard.common.config.TradeSystemConfig;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class WalletClientFactory extends BasePooledObjectFactory<WalletClient> {
    TradeSystemConfig config = (TradeSystemConfig) App.context.getBean("tradeSystemConfig");
    public static String host = "10.1.1.191";
    public static int port = 50053;
    @Override
    public WalletClient create() throws Exception {
        return new WalletClient(config.getWalletServiceHost(), Integer.parseInt(config.getWalletServicePort()));
    }

    @Override
    public PooledObject<WalletClient> wrap(WalletClient helloWorldClient) {
        return new DefaultPooledObject<>(helloWorldClient);
        //return null;
    }

    @Override
    public void destroyObject(PooledObject<WalletClient> p) throws Exception {
        p.getObject().shutdown();
        super.destroyObject(p);
    }


}
