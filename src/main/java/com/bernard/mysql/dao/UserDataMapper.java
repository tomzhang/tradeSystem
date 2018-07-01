package com.bernard.mysql.dao;

import com.bernard.mysql.dto.ConversionBond;
import com.bernard.mysql.dto.ConversionBondProgressOverview;
import com.bernard.mysql.dto.Order;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface UserDataMapper {
    void inserUserOrder(Order userOder);
}
