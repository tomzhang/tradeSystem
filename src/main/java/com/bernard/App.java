package com.bernard;


import com.bernard.cache.service.CacheService;
import com.bernard.common.config.AssetCoinfig;
import com.bernard.common.config.AssetPairConfig;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.service.UserDataService;
import com.bernard.tradesystem.service.TradeSystemServer;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = Logger.getLogger(App.class);
    public static ClassPathXmlApplicationContext context;
    public static void main( String[] args )
    {
        System.out.println( "Init Spring context" );
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext-myBatis.xml" });
        context=appContext;
        //加载配置文件
        loadCoinfig();
        System.out.println("启动RPC服务器");
        final TradeSystemServer server = new TradeSystemServer();
        try {
            server.start();
            server.blockUntilShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

      //  logger.error("ok");
    }

    private static void loadCoinfig() {
        UserDataService userDataService = (UserDataService) context.getBean("userDataServiceImpl");
        CacheService cacheService = (CacheService) App.context.getBean("cacheServiceImpl");
        List<Asset> assets = userDataService.queryAllAsset();
        for (Asset asset : assets) {
            AssetCoinfig.assetMap.put(asset.getAsset(), asset);
        }
        List<String> assetPair = userDataService.queryAllAssetPair();
        for (String pair : assetPair) {
            AssetPairConfig.assetPairMap.put(pair, "");
        }
        logger.info("加载支持货币数量：" + assets.size() + " 加载支持交易币对数量：" + assetPair.size());

        for (Asset asset : assets) {
            String assetName = asset.getAsset();
            int isOk = userDataService.existTable("T_USER_ASSET_" + assetName);
            if (isOk == 0) {
                logger.info("发现新增资产种类，新建表：" + "T_USER_ASSET_" + assetName);
                userDataService.createNewTable("T_USER_ASSET_" + assetName);

            }
            int hasAddrTable = userDataService.existTable("T_USER_TRANSFERIN_ADDR_" + assetName);
            if (hasAddrTable == 0) {
                logger.info("发现新增用户地址表，新建表：T_USER_TRANSFERIN_ADDR_" + assetName);
                userDataService.createAddrTable("T_USER_TRANSFERIN_ADDR_" + assetName);
            }
        }

    }
}
