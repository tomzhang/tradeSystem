package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.common.error.ErrorType;
import com.bernard.grpc.client.pool.wallet.WalletClient;
import com.bernard.grpc.client.pool.wallet.WalletClientPool;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.GetTransferInAddrReply;
import io.grpc.tradesystem.service.GetTransferInAddrRequest;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import io.grpc.walletcore.service.BindWalletsReply;
import io.grpc.walletcore.service.BindWalletsRequest;
import org.apache.log4j.Logger;

import java.util.Date;
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
        String addr = null;
        try {
            addr = userDataService.queryUserAddr(getTransferInAddrRequest.getAccount(), getTransferInAddrRequest.getAsset());

        } catch (Exception e) {
            e.printStackTrace();
        }
        //  String addr = userDataService.queryUserAddr(getTransferInAddrRequest.getAccount(), getTransferInAddrRequest.getAsset());
        if (addr == null) {
            //TODO 调用钱包 生成地址
            WalletClient walletClient = WalletClientPool.borrowObject();
            BindWalletsRequest request = BindWalletsRequest.newBuilder().setAccount(getTransferInAddrRequest.getAccount()).setAsset(getTransferInAddrRequest.getAsset()).build();
            BindWalletsReply reply = walletClient.getBlockingStub().bindWallets(request);
            String newAddr = reply.getAddress();
            //TODO 插入地址
            if (newAddr != null && newAddr.isEmpty() == false) {
                userDataService.insertUserTransferInAddr(getTransferInAddrRequest.getAccount(), getTransferInAddrRequest.getAsset(), newAddr, new Date());
            } else {
                logger.error("钱包生成地址为空");
                replyErrorState(ErrorType.InternalError.getCode() + "", ErrorType.InternalError.getMessage());
                return null;
            }
            //TODO 返回
            replySuccessState(newAddr);
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
