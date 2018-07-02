package com.bernard.mysql.dao;

import com.bernard.mysql.dto.Order;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserDataMapper {
    void inserUserOrder(Order userOder);
}
