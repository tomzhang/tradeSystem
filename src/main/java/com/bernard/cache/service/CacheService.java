package com.bernard.cache.service;

import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;

import java.util.List;

public interface CacheService {
    Order getCacheOrder(String orderId);

    boolean updateCacheOrder(Order order);

    UserAsset getUserAsset(String account, String asset);

    boolean updateUserAsset(UserAsset userAsset);

    List<Order> mGetCacheOrder(List<String> orderIds);

}
