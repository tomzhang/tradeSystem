package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.dto.TransferIn;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import io.grpc.tradesystem.service.TransferOutReply;
import io.grpc.tradesystem.service.TransferOutRequest;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class TransferOutTask implements Callable {
    private static Logger logger = Logger.getLogger(TransferOutTask.class);
    private StreamObserver<TransferOutReply> responseObserver;
    private TransferOutRequest transferOutRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");


    private TransferOutTask() {

    }

    public TransferOutTask(TransferOutRequest transferOutRequest, StreamObserver<TransferOutReply> responseObserver) {
        this.transferOutRequest = transferOutRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理入账");
        replySucessState();
        return null;
    }

    private void replyErrorState() {
        TransferOutReply transferOutReply = TransferOutReply.newBuilder().setId("").build();
        responseObserver.onNext(transferOutReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySucessState() {
        TransferOutReply transferOutReply = TransferOutReply.newBuilder().setId("").build();
        responseObserver.onNext(transferOutReply);
        responseObserver.onCompleted();
        return;
    }

}
