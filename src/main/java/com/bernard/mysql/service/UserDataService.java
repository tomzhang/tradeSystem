package com.bernard.mysql.service;

import com.bernard.mysql.dto.*;
import io.grpc.tradesystem.service.MatchOrderRequest;

import java.util.Date;
import java.util.List;

public interface UserDataService {
    void insertUserOrder(Order userOrder);

    int updateUserOrder(Order userOrder);

    int updateUserOrderForce(String orderId, String remainToReduce);

    int insertMatchFlow(String flowId, String sellSideOrderId, String sellSideOrderAccount, String buySideOrderId, String buySideOrderAccount, String price, String amount, Date date);


    UserAsset queryUserAssert(String account, String asset);


    int lockUserAssert(String account, String asset, String totalAmount, String oldAvi, String newAvi, int oldLock, int newLock, Date updateTime);

    int decreaseUserAssert(String account, String asset, int oldVersion, String newTotal, String newAvi, Date date);

    int unlockUserAssertWhenFail(String account, String asset, String amountToUnlock, Date updateTime);

    // int updateUserAssert(String account,String asset,String totalAmount,String newAvi,int oldLock, int newLock,Date updateTime);
    int updateUserAssert(String account, String asset, String totalAmountToAdd, String aviToAdd, Date updateTime);

    //public void updateUserAssert();
    Order queryUserOrder(String orderId, String account);

    List<Order> queryMatchOrders(String id1, String id2);


    List<String> queryAllAsset();

    List<String> queryAllAssetPair();

    int existTable(String tableName);

    int insertUserCancelOrder(String orderId, String account, Date updateTime);

    int insertOrderFee(String orderId, String assetPair, String FEE, Date date, String matchFlow, String asset);


    int updateUserChangeFlow(TransferFlow transferFlow);

    int insertUserAsset(UserAsset userAsset);

    int createNewTable(String tableName);

    String queryUserAddr(String account, String asset);

    int createAddrTable(String tableName);

    String queryAccountByAddr(String addr, String asset);

    int insertUserTransferInAddr(String account, String asset, String addr, Date time);

    void batchUpdateUserAsset(List<AssetUpdate> updateList, List<OrderUpdate> orderUpdates, MatchOrderRequest matchOrderRequest);

    void batchUpdateUserOrder(List<OrderUpdate> orderUpdates);




    //更新用户ETH资产



}
