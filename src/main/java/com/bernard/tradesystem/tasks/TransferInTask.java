package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.dto.TransferFlow;
import com.bernard.mysql.dto.TransferSide;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import org.apache.log4j.Logger;

import java.util.Date;
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
        TransferFlow transferIn = new TransferFlow();
        transferIn.setAmount(String.valueOf(transferInRequest.getAmount()));
        transferIn.setAsset(transferInRequest.getAsset());
        transferIn.setFromAddress(transferInRequest.getFromAddress());
        transferIn.setToAddress(transferInRequest.getToAddress());
        transferIn.setNotes(transferInRequest.getNotes());
        transferIn.setTime(new Date());
        transferIn.setSide(TransferSide.IN);
        transferIn.setLockVersion(0);
        transferIn.setAccount(transferInRequest.getAccount());
        transferIn.setSuccessState(true);
        logger.info("开始处理入账:" + transferIn.toString());

        //1.更新数据库增加钱,强制增加，没有采用乐观锁
        int updateMoneyResult = userDataService.updateUserAssert(transferIn.getAccount(), transferIn.getAsset(), transferIn.getAmount(), transferIn.getAmount(), new Date());
        if (updateMoneyResult != 1) {
            logger.fatal("增加用户金额失败");
            replyErrorState();
            return null;
        }
        //2.记录转入流水
        int updateChangeFlowCount = userDataService.updateUserChangeFlow(transferIn);
        if (updateChangeFlowCount != 1) {
            logger.error("插入转账流水失败");
            replyErrorState();
            return null;
        }
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
