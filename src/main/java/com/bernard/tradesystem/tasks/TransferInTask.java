package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.dto.TransferIn;
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
        TransferIn transferIn = new TransferIn();
        transferIn.setAmount(transferInRequest.getAmount());
        transferIn.setAsset(transferInRequest.getAsset());
        transferIn.setFromAddress(transferInRequest.getFromAddress());
        transferIn.setToAddress(transferInRequest.getToAddress());
        transferIn.setNotes(transferInRequest.getNotes());
        transferIn.setTime(transferInRequest.getTime());
        userDataService.transferIn(transferIn);
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
