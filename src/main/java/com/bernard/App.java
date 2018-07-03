package com.bernard;


import com.bernard.mysql.dto.*;
import com.bernard.mysql.service.UserDataService;
import com.bernard.tradesystem.service.TradeSystemServer;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

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
//        Order order = new Order();
//        order.setAccount("11");
//        order.setAmount("100.00");
//        order.setAssertLimit("10.00");
//        order.setAsset("BTC");
//        order.setLockVersion(100);
//        order.setOrderID("efsdkf");
//        order.setOrderSide(OrderSide.BUY);
//        order.setOrderType(OrderType.MARKET_PRICE);
//        order.setRemain("0");
//        order.setState(OrderState.OPEN);
//        order.setSurviveTime("0");
//        order.setOrderTime("123");
//        order.setAssertLimit("0");
//        order.setPrice("500");
//        UserDataService userDataService=(UserDataService)appContext.getBean("userDataServiceImpl");
//        userDataService.insertUserOrder(order);
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
}
