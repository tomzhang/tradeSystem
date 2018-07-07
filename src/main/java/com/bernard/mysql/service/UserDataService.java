package com.bernard.mysql.service;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;

import java.util.Date;

public interface UserDataService {
    public void insertUserOrder(Order userOrder);


    public UserAsset queryUserAssert(String account, String asset);


    int lockUserAssert(String account, String asset, String totalAmount, String oldAvi, String newAvi, int oldLock, int newLock, Date updateTime);


    //public void updateUserAssert();
    Order queryUserOrder(String orderId, String account);



    //更新用户ETH资产



}
