package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.BindWalletsReply;
import io.grpc.tradesystem.service.BindWalletsRequest;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class BindingWalletTask implements Callable {
    private static Logger logger = Logger.getLogger(BindingWalletTask.class);
    private StreamObserver<BindWalletsReply> responseObserver;
    private BindWalletsRequest bindWalletsRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");


    private BindingWalletTask() {

    }

    public BindingWalletTask(BindWalletsRequest bindWalletsRequest, StreamObserver<BindWalletsReply> responseObserver) {
        this.bindWalletsRequest = bindWalletsRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理入账");
        replySucessState();
        return null;
    }

    private void replyErrorState() {
        BindWalletsReply bindWalletsReply = BindWalletsReply.newBuilder().setAddress("").build();
        responseObserver.onNext(bindWalletsReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySucessState() {
        BindWalletsReply bindWalletsReply = BindWalletsReply.newBuilder().setAddress("").build();
        responseObserver.onNext(bindWalletsReply);
        responseObserver.onCompleted();
        return;
    }

}
