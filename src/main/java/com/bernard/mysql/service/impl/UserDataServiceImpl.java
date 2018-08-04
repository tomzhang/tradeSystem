package com.bernard.mysql.service.impl;


import com.bernard.mysql.dao.UserDataMapper;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.TransferFlow;

import com.bernard.mysql.dto.UserAsset;
import com.bernard.mysql.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserDataMapper userDataMapper;


    @Override
    public void insertUserOrder(Order userOrder) {
        userDataMapper.inserUserOrder(userOrder);
    }


    @Override
    public int updateUserOrder(Order userOrder) {
        return userDataMapper.updateUserOrder(userOrder);
    }

    @Override
    public int insertMatchFlow(String flowId, String sellSideOrderId, String sellSideOrderAccount, String buySideOrderId,
                               String buySideOrderAccount, String price, String amount, Date date) {
        return userDataMapper.insertMatchFlow(flowId, sellSideOrderId, sellSideOrderAccount,
                buySideOrderId, buySideOrderAccount, price, amount, date);
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
    public int decreaseUserAssert(String account, String asset, int oldVersion, String newTotal, String newAvi, Date date) {
        return userDataMapper.decreaseUserAssert(account, asset, oldVersion, newTotal, newAvi, date);
    }


    @Override
    public int unlockUserAssertWhenFail(String account, String asset, String amountToUnlock, Date updateTime) {
        return userDataMapper.unlockUserAssertWhenFail(account, asset, amountToUnlock, updateTime);
    }

    @Override
    public int updateUserAssert(String account, String asset, String totalAmountToAdd, String aviToAdd, Date updateTime) {
        return userDataMapper.updateUserAssert(account, asset, totalAmountToAdd, aviToAdd, updateTime);
    }


    @Override
    public Order queryUserOrder(String orderId, String account) {
        return userDataMapper.queryUserOrder(orderId, account);
    }

    @Override
    public List<Order> queryMatchOrders(String id1, String id2) {
        return userDataMapper.queryMatchOrders(id1, id2);
    }

    @Override
    public List<String> queryAllAsset() {
        return userDataMapper.queryAllAsset();
    }

    @Override
    public List<String> queryAllAssetPair() {
        return userDataMapper.queryAllAssetPair();
    }

    @Override
    public int existTable(String tableName) {
        return userDataMapper.existTable(tableName);
    }

    @Override
    public int insertUserCancelOrder(String orderId, String account, Date updateTime) {
        return userDataMapper.insertUserCancelOrder(orderId, account, updateTime);
    }

    @Override
    public int insertOrderFee(String orderId, String assetPair, String FEE, Date date, String matchFlow, String asset) {
        return userDataMapper.insertOrderFee(orderId, assetPair, FEE, date, matchFlow, asset);
    }



    @Override
    public int updateUserChangeFlow(TransferFlow transferFlow) {
        return userDataMapper.updateUserChangeFlow(transferFlow);
    }


}
