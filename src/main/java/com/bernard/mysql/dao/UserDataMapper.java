package com.bernard.mysql.dao;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Date;
import java.util.List;

@MapperScan
public interface UserDataMapper {

    void inserUserOrder(Order userOder);

    int updateUserOrder(Order userOrder);

    UserAsset queryUserAssert(String account, String asset);

    int lockUserAssert(String account, String asset, String totalAmount, String oldAvi, String newAvi, int oldLock, int newLock, Date updateTime);

    Order queryUserOrder(String orderId, String account);

    List<String> queryAllAsset();

    List<String> queryAllAssetPair();

    int existTable(String tableName);

    List<Order> queryMatchOrders(String id1, String id2);

    int updateUserAssert(String account, String asset, String totalAmountToAdd, String aviToAdd, Date updateTime);

    int insertMatchFlow(String flowId, String sellSideOrderId, String sellSideAccount, String buySideOrderId, String buySideOrderAccount, String price, String amount, Date date);

    int unlockUserAssertWhenFail(String account, String asset, String amountToUnlock, Date updateTime);




}
