package com.bernard.mysql.service;

import com.bernard.mysql.dto.Order;

public interface UserDataService {
    public void insertUserOrder(Order userOrder);

    //查询用户BTC资产
    public void queryUserBTCAssert(String account);

    //锁定用户BTC资产
    public void lockUserBTCAssert();

    //更新用户BTC资产
    public void updateUserBTCAssert();

    //更新用户ETH资产



}
