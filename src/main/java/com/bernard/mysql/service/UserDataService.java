package com.bernard.mysql.service;

import com.bernard.mysql.dto.Order;

public interface UserDataService {
    public void insertUserOrder(Order userOrder);

    //查询用户BTC资产
    public void queryUserAssert(String account);

    //锁定用户BTC资产
    public void lockUserAssert();
    //划转用户BTC资产

}
