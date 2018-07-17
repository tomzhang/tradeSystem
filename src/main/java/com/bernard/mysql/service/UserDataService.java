package com.bernard.mysql.service;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;

import java.util.Date;
import java.util.List;

public interface UserDataService {
    public void insertUserOrder(Order userOrder);

    int updateUserOrder(Order userOrder);

    int insertMatchFlow(String flowId, String sellSideOrderId, String sellSideOrderAccount, String buySideOrderId, String buySideOrderAccount, String price, String amount, Date date);


    public UserAsset queryUserAssert(String account, String asset);


    int lockUserAssert(String account, String asset, String totalAmount, String oldAvi, String newAvi, int oldLock, int newLock, Date updateTime);

    // int updateUserAssert(String account,String asset,String totalAmount,String newAvi,int oldLock, int newLock,Date updateTime);
    int updateUserAssert(String account, String asset, String totalAmountToAdd, String aviToAdd, Date updateTime);

    //public void updateUserAssert();
    Order queryUserOrder(String orderId, String account);

    List<Order> queryMatchOrders(String id1, String id2);


    List<String> queryAllAsset();

    List<String> queryAllAssetPair();

    int existTable(String tableName);




    //更新用户ETH资产



}
