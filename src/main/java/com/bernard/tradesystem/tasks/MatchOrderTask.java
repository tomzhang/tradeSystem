package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.OrderSide;
import com.bernard.mysql.dto.OrderState;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.MatchOrderReply;
import io.grpc.tradesystem.service.MatchOrderRequest;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;

public class MatchOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(MatchOrderTask.class);
    private StreamObserver<MatchOrderReply> responseObserver;
    private MatchOrderRequest matchOrderRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");


    private MatchOrderTask() {

    }

    public MatchOrderTask(MatchOrderRequest matchOrderRequest, StreamObserver<MatchOrderReply> responseObserver) {
        this.matchOrderRequest = matchOrderRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理成交回报");
        //1.查询订单
        List<Order> matchOrders = userDataService.queryMatchOrders(matchOrderRequest.getBuySideOrderId(), matchOrderRequest.getSellSideOrderId());
        if (matchOrders.size() != 2) {
            logger.fatal("成交单号匹配错误");
            return null;
        }
        //2.处理订单

        return null;
    }

    private boolean handleOrder(Order order, String matchAmount, String matchPrice) {
        if (order.getState() == OrderState.CANCLE || order.getState() == OrderState.COMPLETE) {
            logger.fatal("订单状态异常");
            return false;
        }

        if (order.getOrderSide() == OrderSide.BUY) {
            BigDecimal remain = new BigDecimal(order.getRemain());

            return true;
        } else if (order.getOrderSide() == OrderSide.SELL) {

            return true;
        } else {
            logger.error("订单异常");
            return false;
        }
    }

}
