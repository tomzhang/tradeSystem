package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.mysql.dto.AssetUpdate;
import com.bernard.mysql.dto.OrderUpdate;
import com.bernard.mysql.service.UserDataService;
import io.grpc.stub.StreamObserver;
import io.grpc.tradesystem.service.MatchOrderCancelReply;
import io.grpc.tradesystem.service.MatchOrderCancelRequest;
import io.grpc.tradesystem.service.OrderSide;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class MatchOrderCancelTask implements Callable {
    private static Logger logger = Logger.getLogger(MatchOrderCancelTask.class);
    private MatchOrderCancelRequest request;
    //StreamObserver<MatchOrderCancelReply> responseObserver;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");

    private MatchOrderCancelTask() {

    }

    public MatchOrderCancelTask(MatchOrderCancelRequest request) {
        this.request = request;
        //this.responseObserver = responseObserver;

    }

    @Override
    public Object call() throws Exception {
        logger.info("开始处理未成交返还请求：" + request.toString());
        long start = System.currentTimeMillis();
        String amount = request.getCancelAmount();
        String orderId = request.getOrderId();
        String assetPair = request.getAsset();
        String cargoCoin = assetPair.split("-")[0];
        String baseCoin = assetPair.split("-")[1];
        OrderSide orderSide = request.getOrderSide();
        String account = request.getAccount();
        String coinTounlock;
        if (orderSide == OrderSide.BID) {
            //1.买入
            coinTounlock = baseCoin;
        } else {
            //2.卖出
            coinTounlock = cargoCoin;
        }

        List<AssetUpdate> assetUpdates = new ArrayList<>();
        AssetUpdate cargoUpdate = new AssetUpdate(account, coinTounlock, amount.toString(), amount.toString(), new Date());
        assetUpdates.add(cargoUpdate);

        List<OrderUpdate> orderUpdates = new ArrayList<>();

        userDataService.batchUpdateMatchOrderTask(assetUpdates, orderUpdates, null, "0", "0");
        logger.info("开始处理未成交返还请求完毕：" + (System.currentTimeMillis() - start));

        return null;
    }
}
