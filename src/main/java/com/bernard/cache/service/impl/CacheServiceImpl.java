package com.bernard.cache.service.impl;

import com.bernard.cache.RedisKeys;
import com.bernard.cache.RedisUtil;
import com.bernard.cache.service.CacheService;
import com.bernard.mysql.dto.Order;
import com.bernard.mysql.dto.UserAsset;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CacheServiceImpl implements CacheService {
    private long cacheTime = 10 * 60 * 60;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Order getCacheOrder(String orderId) {
        return (Order) redisUtil.get(RedisKeys.ORDER_MAP_PREFIX + orderId);

    }

    @Override
    public boolean updateCacheOrder(Order order) {
        return redisUtil.set(RedisKeys.ORDER_MAP_PREFIX + order.getOrderID(), JSONObject.fromObject(order).toString(), cacheTime);
    }

    @Override
    public UserAsset getUserAsset(String account, String asset) {
        return (UserAsset) redisUtil.hget(RedisKeys.ASSET_MAP_PREFIX + asset, account);
    }

    @Override
    public boolean updateUserAsset(UserAsset userAsset) {
        return redisUtil.hset(RedisKeys.ASSET_MAP_PREFIX + userAsset.getAsset(), userAsset.getAccount(), userAsset);
    }

    @Override
    public List<Order> mGetCacheOrder(List<String> orderIds) {
        List objectlist = redisUtil.mGet(orderIds);
        List<Order> orders = new ArrayList<>();
        if (objectlist != null && objectlist.size() > 0) {
            for (Object o : objectlist) {
                Order order = com.alibaba.fastjson.JSONObject.parseObject((String) o, Order.class);
                if (order != null) {
                    orders.add(order);
                }
            }
        } else {
            return new ArrayList<>();
        }
        return orders;
    }


}
