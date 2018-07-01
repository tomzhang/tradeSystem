package com.bernard.mysql.service.impl;


import com.bernard.mysql.dao.UserDataMapper;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserDataMapper userDataMapper;


    @Override
    public void insertUserOrder(Order userOrder) {
        userDataMapper.inserUserOrder(userOrder);
    }
}
