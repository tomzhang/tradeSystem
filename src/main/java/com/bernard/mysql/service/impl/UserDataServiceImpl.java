package com.bernard.mysql.service.impl;


import com.bernard.mysql.dao.UserDataMapper;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;
import com.bernard.mysql.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserDataMapper userDataMapper;


    @Override
    public void insertUserOrder(Order userOrder) {
        userDataMapper.inserUserOrder(userOrder);
    }

    @Override
    public UserAsset queryUserAssert(String account, String asset) {
        return userDataMapper.queryUserAssert(account, asset);

    }


    @Override
    public int lockUserAssert(String account, String asset, String totalAmount, String oldAvi, String newAvi, int oldLock, int newLock, Date updateTime) {
        return userDataMapper.lockUserAssert(account, asset, totalAmount, oldAvi, newAvi, oldLock, newLock, updateTime);
    }

    @Override
    public Order queryUserOrder(String orderId, String account) {
        return userDataMapper.queryUserOrder(orderId, account);
    }

    @Override
    public List<String> queryAllAsset() {
        return userDataMapper.queryAllAsset();
    }

    @Override
    public List<String> queryAllAssetPair() {
        return userDataMapper.queryAllAssetPair();
    }


}
