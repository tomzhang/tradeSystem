package com.bernard;

import com.bernard.com.bernard.mysql.service.UserDataService;
import com.bernard.mysql.dto.ConversionBond;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        System.out.println( "Hello World!" );
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
                new String[] { "applicationContext-myBatis.xml" });
        context=appContext;
        UserDataService userDataService=(UserDataService)appContext.getBean("userDataServiceImpl");

        logger.error("ok");
    }
}
