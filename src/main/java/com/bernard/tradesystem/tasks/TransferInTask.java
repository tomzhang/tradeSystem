package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class TransferInTask implements Callable {
    private static Logger logger = Logger.getLogger(TransferInTask.class);
    private StreamObserver<TransferInReply> responseObserver;
    private TransferInRequest transferInRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");


    private TransferInTask() {

    }

    public TransferInTask(TransferInRequest transferInRequest, StreamObserver<TransferInReply> responseObserver) {
        this.transferInRequest = transferInRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理入账");
        replySucessState();
        return null;
    }

    private void replyErrorState() {
        TransferInReply transferInReply = TransferInReply.newBuilder().setState(false).build();
        responseObserver.onNext(transferInReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySucessState() {
        TransferInReply transferInReply = TransferInReply.newBuilder().setState(true).build();
        responseObserver.onNext(transferInReply);
        responseObserver.onCompleted();
        return;
    }

}
