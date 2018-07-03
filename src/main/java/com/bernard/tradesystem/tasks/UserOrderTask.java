package com.bernard.tradesystem.tasks;

import com.bernard.mysql.dto.AssertType;
import com.bernard.mysql.dto.Order;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.UserOrderReply;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class UserOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserOrderTask.class);
    private StreamObserver<UserOrderReply> responseObserver;
    private Order order;

    private UserOrderTask() {

    }

    public UserOrderTask(Order order, StreamObserver<UserOrderReply> responseObserver) {
        this.order = order;
        this.responseObserver = responseObserver;

    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理用户订单");
        String method = AssertType.getMethodNameByName(order.getAsset());


        return null;
    }


}
