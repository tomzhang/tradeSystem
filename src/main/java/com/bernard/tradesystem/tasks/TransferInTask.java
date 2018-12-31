package com.bernard.tradesystem.tasks;


import com.bernard.App;
import com.bernard.common.config.TradeSystemConfig;
import com.bernard.common.error.ErrorType;
import com.bernard.common.utils.HttpsUtil;
import com.bernard.common.utils.TimeUtil;
import com.bernard.mysql.dto.TransferFlow;
import com.bernard.mysql.dto.TransferSide;
import com.bernard.mysql.dto.UserAsset;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.TransferInReply;
import io.grpc.tradesystem.service.TransferInRequest;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

public class TransferInTask implements Callable {
    private static Logger logger = Logger.getLogger(TransferInTask.class);
    private StreamObserver<TransferInReply> responseObserver;
    private TransferInRequest transferInRequest;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");
    TradeSystemConfig config = (TradeSystemConfig) App.context.getBean("tradeSystemConfig");



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
        transferIn.setFromAddress("");
        transferIn.setToAddress(transferInRequest.getAddr());
        transferIn.setNotes(transferInRequest.getNotes());
        transferIn.setTime(new Date());
        transferIn.setSide(TransferSide.IN);
        transferIn.setLockVersion(0);
        transferIn.setAccount("");
        transferIn.setSuccessState(true);
        logger.info("开始处理入账:" + transferIn.toString());
        //0.根据地址查询账号
        String account = userDataService.queryAccountByAddr(transferInRequest.getAddr(), transferInRequest.getAsset());
        if (account == null || account.isEmpty()) {
            logger.error("地址对应账号不存在");
            replyErrorState(ErrorType.MatchAccoutError.getCode() + "", ErrorType.MatchAccoutError.getMessage());
            return null;
        }
        transferIn.setAccount(account);

        //1.更新数据库增加钱,强制增加，没有采用乐观锁
        int updateMoneyResult = userDataService.updateUserAssert(transferIn.getAccount(), transferIn.getAsset(), transferIn.getAmount(), transferIn.getAmount(), new Date());
        if (updateMoneyResult != 1) {
            Boolean iniResult = false;
            if (updateMoneyResult == 0) {
                logger.info("用户资产未初始化，尝试初始化");
                iniResult = tryToInitUserAsset(transferIn.getAsset(), transferIn.getAccount(), new BigDecimal(transferIn.getAmount()));
            }
            if (iniResult == false) {
                logger.fatal("增加用户金额失败." + transferIn.toString());
                replyErrorState(ErrorType.InternalError.getCode() + "", ErrorType.InternalError.getMessage());
                return null;
            }
        }
        //2.记录转入流水
        int updateChangeFlowCount = userDataService.updateUserChangeFlow(transferIn);
        if (updateChangeFlowCount != 1) {
            logger.fatal("插入转账流水失败，但是用户资金已增加");
        }
        JSONObject postData = new JSONObject();
        postData.put("account", account);
        postData.put("asset", transferInRequest.getAsset());
        postData.put("amount", String.valueOf(transferInRequest.getAmount()));
        postData.put("time", TimeUtil.getTimeString());
        String postURL = "http://" + config.getApiServiceHost() + ":" + config.getApiServicePort() + "/web/wallet/in";
        String result = HttpsUtil.post(postURL, null, postData);
        replySuccessState();
        return null;
    }

    private void replyErrorState(String errorCode, String errorMessage) {
        TransferInReply transferInReply = TransferInReply.newBuilder().setState(false).setErrorCode(errorCode).setErrorMessage(errorMessage).build();
        responseObserver.onNext(transferInReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState() {
        TransferInReply transferInReply = TransferInReply.newBuilder().setState(true).build();
        responseObserver.onNext(transferInReply);
        responseObserver.onCompleted();
        return;
    }


    private boolean tryToInitUserAsset(String asset, String account, BigDecimal amountToUser) {
        UserAsset userAsset = userDataService.queryUserAssert(account, asset);
        if (userAsset == null) {
            logger.info("用户:" + asset + "账户未初始化，自动初始化并转入份额");
            UserAsset newUserAssert = new UserAsset();
            newUserAssert.setAccount(account);
            newUserAssert.setAviliable(amountToUser.toString());
            newUserAssert.setTotalAmount(amountToUser.toString());
            newUserAssert.setLiquidationTime(new Date());
            newUserAssert.setUpdateTime(new Date());
            newUserAssert.setLockVersion(0);
            newUserAssert.setAsset(asset);
            userDataService.insertUserAsset(newUserAssert);
            return true;
        } else {
            logger.error("用户已初始化。");
            return false;
        }

    }

}
