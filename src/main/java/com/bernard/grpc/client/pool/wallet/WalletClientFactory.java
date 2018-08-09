/*package com.bernard.grpc.client.pool.wallet;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class WalletClientFactory extends BasePooledObjectFactory<WalletClient> {
    public static String host = "127.0.0.1";
    public static int port = 8080;
    @Override
    public WalletClient create() throws Exception {
        return new WalletClient(host, port);
    }

    @Override
    public PooledObject<WalletClient> wrap(WalletClient walletClient) {
        return  new DefaultPooledObject<>(walletClient);
        //return null;
    }

    @Override
    public void destroyObject(PooledObject<WalletClient> p) throws Exception {
        p.getObject().shutdown();
        super.destroyObject(p);
    }


}*/
