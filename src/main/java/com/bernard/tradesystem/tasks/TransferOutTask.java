package com.bernard.tradesystem.tasks;

import com.bernard.App;

import com.bernard.common.error.ErrorType;
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
        TransferFlow transferOutFlow = new TransferFlow();
        transferOutFlow.setAccount(transferOutRequest.getAccount());
        transferOutFlow.setLockVersion(0);
        transferOutFlow.setSide(TransferSide.OUT);
        transferOutFlow.setAmount(transferOutRequest.getAmount());
        transferOutFlow.setAsset(transferOutRequest.getAsset());
        transferOutFlow.setToAddress(transferOutRequest.getToAddr());
        transferOutFlow.setTime(new Date());
        logger.info("开始处理提币请求:" + transferOutFlow.toString());

        //2.交易余额，稳定以后可以用缓存数据以加快访问速度。
        UserAsset userAsset = userDataService.queryUserAssert(transferOutFlow.getAccount(), transferOutFlow.getAsset());
        BigDecimal avi = new BigDecimal(userAsset.getAviliable());
        if (avi.compareTo(new BigDecimal(transferOutFlow.getAmount())) <= 0) {
            logger.info("提币余额不足");
            transferOutFlow.setSuccessState(false);
            userDataService.updateUserChangeFlow(transferOutFlow);
            replyErrorState(ErrorType.Insufficient.getMessage(), ErrorType.InternalError.getCode() + "");
            return null;
        }
        BigDecimal newAvi = avi.subtract(new BigDecimal(transferOutFlow.getAmount())).setScale(8, BigDecimal.ROUND_DOWN);
        BigDecimal newTotal = new BigDecimal(userAsset.getTotalAmount()).subtract(new BigDecimal(transferOutFlow.getAmount())).setScale(8, BigDecimal.ROUND_DOWN);
        int result = userDataService.decreaseUserAssert(transferOutFlow.getAccount(), transferOutFlow.getAsset(), userAsset.getLockVersion(), newTotal.toString(), newAvi.toString(), new Date());
        if (result != 1) {
            logger.error("提币锁定账户金额错误");
            replyErrorState(ErrorType.AssetChanged.getMessage(), ErrorType.AssetChanged.getCode() + "");
            return null;
        }
        //3.通知钱包转账
        userDataService.updateUserChangeFlow(transferOutFlow);
        replySuccessState();
        return null;
    }

    private void replyErrorState(String errorMessage, String errorCode) {
        TransferOutReply transferOutReply = TransferOutReply.newBuilder().setState(false).setErrorCode(errorCode).setErrorMessage(errorMessage).build();
        responseObserver.onNext(transferOutReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState() {
        TransferOutReply transferOutReply = TransferOutReply.newBuilder().setState(true).build();
        responseObserver.onNext(transferOutReply);
        responseObserver.onCompleted();
        return;
    }

}
