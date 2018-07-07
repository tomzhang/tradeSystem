package com.bernard.mysql.dao;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;
import org.mybatis.spring.annotation.MapperScan;

import java.util.Date;

@MapperScan
public interface UserDataMapper {

    void inserUserOrder(Order userOder);

    UserAsset queryUserAssert(String account, String asset);

    int lockUserAssert(String account, String asset, String totalAmount, String oldAvi, String newAvi, int oldLock, int newLock, Date updateTime);

    Order queryUserOrder(String orderId, String account);

}
