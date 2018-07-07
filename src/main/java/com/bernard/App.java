package com.bernard;


import com.bernard.common.config.AssetCoinfig;
import com.bernard.common.config.AssetPairConfig;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.service.UserDataService;
import com.bernard.tradesystem.service.TradeSystemServer;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
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
    private static ClassPathXmlApplicationContext context;
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
        List<String> assets = userDataService.queryAllAsset();
        for (String asset : assets) {
            AssetCoinfig.assetMap.put(asset, "");
        }
        List<String> assetPair = userDataService.queryAllAssetPair();
        for (String pair : assetPair) {
            AssetPairConfig.assetPairMap.put(pair, "");
        }
    }
}
