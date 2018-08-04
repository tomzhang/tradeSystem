package com.bernard.tradesystem.tasks;

import com.bernard.App;

import com.bernard.mysql.dto.TransferFlow;
import com.bernard.mysql.dto.TransferSide;
import com.bernard.mysql.dto.UserAsset;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.TransferOutReply;
import io.grpc.tradesystem.service.TransferOutRequest;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
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
        logger.info("开始处理提币");
        TransferFlow transferOutFlow = new TransferFlow();
        transferOutFlow.setAccount(transferOutRequest.getAccount());
        transferOutFlow.setLockVersion(0);
        transferOutFlow.setSide(TransferSide.OUT);
        transferOutFlow.setAmount(transferOutRequest.getAmount());
        transferOutFlow.setAsset(transferOutRequest.getAsset());
        transferOutFlow.setToAddress(transferOutRequest.getToAddr());
        transferOutFlow.setTime(new Date());

        //2.扣钱
        UserAsset userAsset = userDataService.queryUserAssert(transferOutFlow.getAccount(), transferOutFlow.getAsset());
        BigDecimal avi = new BigDecimal(userAsset.getAviliable());
        if (avi.compareTo(new BigDecimal(transferOutFlow.getAmount())) <= 0) {
            logger.info("提币余额不足");
            transferOutFlow.setSuccessState(false);
            userDataService.updateUserChangeFlow(transferOutFlow);
            replyErrorState();
            return null;
        }
        BigDecimal newAvi = avi.subtract(new BigDecimal(transferOutFlow.getAmount())).setScale(8, BigDecimal.ROUND_DOWN);
        BigDecimal newTotal = new BigDecimal(userAsset.getTotalAmount()).subtract(new BigDecimal(transferOutFlow.getAmount())).setScale(8, BigDecimal.ROUND_DOWN);
        userDataService.decreaseUserAssert(transferOutFlow.getAccount(), transferOutFlow.getAsset(), userAsset.getLockVersion(), newTotal.toString(), newAvi.toString());
        //3.通知钱包转账

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
