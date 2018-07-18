package com.bernard.tradesystem.tasks;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.CancleOrderReply;
import io.grpc.tradesystem.service.CancleOrderRequest;
import io.grpc.tradesystem.service.UserOrderReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

public class UserCancelOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserCancelOrderTask.class);
    private StreamObserver<CancleOrderReply> responseObserver;
    private CancleOrderRequest cancleOrderRequest;
    @Autowired
    private UserDataService userDataService;

    private UserCancelOrderTask() {

    }

    public UserCancelOrderTask(CancleOrderRequest cancleOrderRequest, StreamObserver<CancleOrderReply> responseObserver) {
        this.cancleOrderRequest = cancleOrderRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        Order userOrder = userDataService.queryUserOrder(cancleOrderRequest.getOrderId(), cancleOrderRequest.getAccount());
        if (userOrder == null) {
            replyErrorState();
            return null;
        }

        //TODO

        return null;
    }

    private void replyErrorState() {
        CancleOrderReply cancleOrderReply = CancleOrderReply.newBuilder().setState(false).setMessage(false).build();
        responseObserver.onNext(cancleOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState() {
        CancleOrderReply cancleOrderReply = CancleOrderReply.newBuilder().setState(true).setMessage(true).build();
        responseObserver.onNext(cancleOrderReply);
        responseObserver.onCompleted();
        return;
    }


}
