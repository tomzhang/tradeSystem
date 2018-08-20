package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.common.error.ErrorType;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.GetTransferInAddrReply;
import io.grpc.tradesystem.service.GetTransferInAddrRequest;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

public class GetTransferAddrTask implements Callable {
    private static Logger logger = Logger.getLogger(GetTransferAddrTask.class);
    private StreamObserver<GetTransferInAddrReply> responseObserver;
    private GetTransferInAddrRequest getTransferInAddrRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");


    private GetTransferAddrTask() {

    }

    public GetTransferAddrTask(GetTransferInAddrRequest getTransferInAddrRequest, StreamObserver<GetTransferInAddrReply> responseObserver) {
        this.getTransferInAddrRequest = getTransferInAddrRequest;
        this.responseObserver = responseObserver;
    }

    @Override
    public Object call() throws Exception {

        // replySuccessState();
        String addr = userDataService.queryUserAddr(getTransferInAddrRequest.getAccount(), getTransferInAddrRequest.getAsset());
        if (addr == null) {
            //TODO 调用钱包 生成地址
            //TODO 插入地址
            //TODO 返回
            logger.error("匹配地址失败：" + getTransferInAddrRequest.getAsset() + " " + getTransferInAddrRequest.getAccount());
            replyErrorState(ErrorType.MatchAccoutError.getCode() + "", ErrorType.MatchAccoutError.getMessage());
            return null;
        } else {
            replySuccessState(addr);
            return null;
        }

    }

    private void replyErrorState(String errorCode, String errorMessage) {
        GetTransferInAddrReply bindWalletsReply = GetTransferInAddrReply.newBuilder().setState(false)
                .setErrorCode(errorCode).setErrorMessage(errorMessage).setAddr("").build();
        responseObserver.onNext(bindWalletsReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState(String addr) {
        GetTransferInAddrReply bindWalletsReply = GetTransferInAddrReply.newBuilder().setState(true).setAddr(addr).build();
        responseObserver.onNext(bindWalletsReply);
        responseObserver.onCompleted();
        return;
    }

}
