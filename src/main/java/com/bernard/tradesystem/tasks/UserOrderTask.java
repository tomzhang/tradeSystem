package com.bernard.tradesystem.tasks;

import com.bernard.App;
import com.bernard.cache.service.CacheService;
import com.bernard.common.error.ErrorType;
import com.bernard.grpc.client.pool.tradeSystem.TradeCoreClient;
import com.bernard.grpc.client.pool.tradeSystem.TradeCoreClientPool;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.dto.OrderSide;
import com.bernard.mysql.dto.OrderType;
import com.bernard.mysql.service.UserDataService;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import io.grpc.tradeCore.service.*;
import io.grpc.tradesystem.service.*;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Callable;

public class UserOrderTask implements Callable {
    private static Logger logger = Logger.getLogger(UserOrderTask.class);
    private StreamObserver<UserOrderReply> responseObserver;
    private Order order;
    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");
    private CacheService cacheService = (CacheService) App.context.getBean("cacheServiceImpl");

    private UserOrderTask() {

    }

    public UserOrderTask(Order order, StreamObserver<UserOrderReply> responseObserver) {
        this.order = order;
        this.responseObserver = responseObserver;

    }

    @Override
    @Transactional
    public Object call() throws Exception {
        long start = System.currentTimeMillis();
        if (order.getOrderType() == OrderType.PRICE_LIMIT) {
            logger.info("开始处理用户限价订单:" + order.toString());
            String assetPair = order.getAssetPair();
            String cargoCoin = assetPair.split("-")[0];
            String baseCoin = assetPair.split("-")[1];
            OrderSide orderSide = order.getOrderSide();
            if (orderSide == OrderSide.BUY) {
                BigDecimal neededCoin = new BigDecimal(order.getAmount()).multiply(new BigDecimal(order.getPrice()));
                boolean processResult = processUserOrder(order.getAccount(), baseCoin, neededCoin);
                if (processResult == true) {
                    try {
                        Response response = sendOrderToTradeCore(cargoCoin, baseCoin, OrderType.PRICE_LIMIT);
                        if (response == null || response.getCode() != 0) {
                            throw new RuntimeException("调用撮合系统失败");
                        }
                        replySuccessState(start);
                    } catch (Exception e) {
                        logger.error("插入订单失败", e);
                        unLockAsset(order.getAccount(), baseCoin, neededCoin.toString(), new Date());
                        order.setState(OrderState.ERROR);
                        order.setLockVersion(order.getLockVersion() + 1);
                        userDataService.updateUserOrder(order);
                        replyErrorState(ErrorType.InternalError.getCode() + "", ErrorType.InternalError.getMessage());
                    }
                } else {
                    return null;
                }
            } else {
                //卖出锁定cargoCoin
                BigDecimal needCoin = new BigDecimal(order.getAmount());
                boolean processResult = processUserOrder(order.getAccount(), cargoCoin, needCoin);
                if (processResult == true) {
                    try {
                        Response response = sendOrderToTradeCore(cargoCoin, baseCoin, OrderType.PRICE_LIMIT);
                        if (response == null || response.getCode() != 0) {
                            throw new RuntimeException("调用撮合系统失败");
                        }
                        replySuccessState(start);
                    } catch (Exception e) {
                        logger.error("插入订单失败", e);
                        unLockAsset(order.getAccount(), cargoCoin, needCoin.toString(), new Date());
                        order.setState(OrderState.ERROR);
                        order.setLockVersion(order.getLockVersion() + 1);
                        userDataService.updateUserOrder(order);
                        replyErrorState(ErrorType.InternalError.getCode() + "", ErrorType.InternalError.getMessage());
                    }

                } else {
                    return null;
                }
            }
            return null;
        } else if (order.getOrderType() == OrderType.MARKET_PRICE) {
            logger.info("开始处理用户市价订单:" + order.toString());
            String assetPair = order.getAssetPair();
            String cargoCoin = assetPair.split("-")[0];
            String baseCoin = assetPair.split("-")[1];
            OrderSide orderSide = order.getOrderSide();
            if (orderSide == OrderSide.BUY) {
                BigDecimal neededCoin = new BigDecimal(order.getAssertLimit());
                boolean processResult = processUserOrder(order.getAccount(), baseCoin, neededCoin);
                if (processResult == true) {
                    try {
                        Response response = sendOrderToTradeCore(cargoCoin, baseCoin, OrderType.MARKET_PRICE);
                        if (response == null || response.getCode() != 0) {
                            throw new RuntimeException("调用撮合系统失败");
                        }
                        replySuccessState(start);
                    } catch (Exception e) {
                        logger.error("插入订单失败", e);
                        unLockAsset(order.getAccount(), baseCoin, neededCoin.toString(), new Date());
                        order.setState(OrderState.ERROR);
                        order.setLockVersion(order.getLockVersion() + 1);
                        userDataService.updateUserOrder(order);
                        replyErrorState(ErrorType.InternalError.getCode() + "", ErrorType.InternalError.getMessage());
                    }
                } else {
                    logger.fatal("处理用户订单失败");
                    return null;
                }
            } else {
                //卖出锁定cargoCoin
                BigDecimal needCoin = new BigDecimal(order.getAssertLimit());
                boolean processResult = processUserOrder(order.getAccount(), cargoCoin, needCoin);
                if (processResult == true) {
                    try {
                        Response response = sendOrderToTradeCore(cargoCoin, baseCoin, OrderType.MARKET_PRICE);
                        if (response == null || response.getCode() != 0) {
                            throw new RuntimeException("调用撮合系统失败");
                        }
                        replySuccessState(start);
                    } catch (Exception e) {
                        logger.error("插入订单失败", e);
                        unLockAsset(order.getAccount(), cargoCoin, needCoin.toString(), new Date());
                        order.setState(OrderState.ERROR);
                        order.setLockVersion(order.getLockVersion() + 1);
                        userDataService.updateUserOrder(order);
                        replyErrorState(ErrorType.InternalError.getCode() + "", ErrorType.InternalError.getMessage());
                    }

                } else {
                    return null;
                }

            }
        }
        return null;
    }

    /**
     * @param account
     * @param amountToLock
     * @param coinToLock
     * @return 锁定成功返回true
     */

    private boolean processUserOrder(String account, String coinToLock, BigDecimal amountToLock) {
        UserAsset userAsset;
        //userAsset =cacheService.getUserAsset(order.getAccount(),coinToLock);
        try {
            userAsset = userDataService.queryUserAssert(order.getAccount(), coinToLock);
        } catch (Exception e) {
            logger.error("查询数据库失败", e);
            replyErrorState(ErrorType.UnsupportedAsset.getCode() + "", ErrorType.UnsupportedAsset.getMessage());
            return false;
        }
        if (userAsset == null) {
            logger.error("查询个人资产失败：coin:" + coinToLock);
            replyErrorState(ErrorType.Insufficient.getCode() + "", ErrorType.Insufficient.getMessage());
            return false;
        }
        //notice 这里不更新缓存 因为在之后还需要对用户资产进行锁定操作
        BigDecimal available = new BigDecimal(userAsset.getAviliable());
        if (available.compareTo(amountToLock) < 0) {
            logger.error(ErrorType.Insufficient.getMessage());
            replyErrorState(ErrorType.Insufficient.getCode() + "", ErrorType.Insufficient.getMessage());
            return false;
        }
        int updateCount = userDataService.lockUserAssert(account, coinToLock, userAsset.getTotalAmount(), available.toString(), available.subtract(amountToLock).toString(), userAsset.getLockVersion(), userAsset.getLockVersion() + 1, new Date());

        if (updateCount != 1) {
            //更新失败 下单失败
            logger.fatal("锁定用户资产失败：" + order.toString() + "开始重试");
            UserAsset newAsset = userDataService.queryUserAssert(order.getAccount(), coinToLock);
            BigDecimal newAvailable = new BigDecimal(newAsset.getAviliable());
            if (newAvailable.compareTo(amountToLock) < 0) {
                logger.error("下单失败：" + ErrorType.Insufficient.getMessage());
                replyErrorState(ErrorType.Insufficient.getCode() + "", ErrorType.Insufficient.getMessage());
                return false;
            }
            int newUpdateCount = userDataService.lockUserAssert(account, coinToLock, newAsset.getTotalAmount(), newAvailable.toString(), newAvailable.subtract(amountToLock).toString(), newAsset.getLockVersion(), newAsset.getLockVersion() + 1, new Date());
            if (newUpdateCount != 1) {
                logger.fatal("下单失败：锁定用户资产重试失败," + order.toString());
                replyErrorState(ErrorType.FrequencyLimit.getCode() + "", ErrorType.FrequencyLimit.getMessage());
                return false;
            }
        }
        cacheService.updateCacheOrder(order);
        userDataService.insertUserOrder(order);
        return true;

    }

    private void replyErrorState(String errorCode, String errorMessage) {
        logger.info("下单失败");
        UserOrderReply userOrderReply = UserOrderReply.newBuilder().setState(false).setOrderId(order.getOrderID()).setErrorCode(errorCode).setErrorMessage(errorMessage).build();
        responseObserver.onNext(userOrderReply);
        responseObserver.onCompleted();
        return;
    }

    private void replySuccessState(long start) {
        logger.info("下单成功,整体耗时：" + (System.currentTimeMillis() - start));
        UserOrderReply userOrderReply = UserOrderReply.newBuilder().setState(true).setOrderId(order.getOrderID()).build();
        responseObserver.onNext(userOrderReply);
        responseObserver.onCompleted();
        return;
    }


    private Response sendOrderToTradeCore(String cargoCoin, String baseCoin, OrderType orderType) throws RuntimeException {
        TradeCoreClient client = TradeCoreClientPool.borrowObject();
        //1.pair
        AssetPair pair = AssetPair.newBuilder().setAsset(cargoCoin).setMoney(baseCoin).build();
        //2.charge
        Charge charge = null;
        if (orderType == OrderType.PRICE_LIMIT) {
            charge = Charge.newBuilder().setAmount(order.getAmount()).setPrice(order.getPrice()).build();
        } else if (orderType == OrderType.MARKET_PRICE) {
            charge = Charge.newBuilder().setAmount(order.getAssertLimit()).build();
        } else {
            logger.fatal("下单类型暂不支持；" + order.toString());
        }
        //3.side
        io.grpc.tradeCore.service.OrderSide rpcSide;
        io.grpc.tradeCore.service.OrderType rpcType = null;
        if (order.getOrderSide() == OrderSide.BUY) {
            rpcSide = io.grpc.tradeCore.service.OrderSide.BID;
        } else {
            rpcSide = io.grpc.tradeCore.service.OrderSide.ASK;
        }
        //4.rpcType
        if (order.getOrderType() == OrderType.PRICE_LIMIT) {
            rpcType = io.grpc.tradeCore.service.OrderType.LIMIT;
        } else if (order.getOrderType() == OrderType.MARKET_PRICE) {
            rpcType = io.grpc.tradeCore.service.OrderType.MARKET;
        } else {
            logger.fatal("下单类型暂不支持；" + order.toString());
        }
        //5.SpotTradeExt ext
        SpotTradeExt spotTradeExt = null;
        if (order.getOrderType() == OrderType.MARKET_PRICE) {
            OrderValidTimeType orderValidTimeType = OrderValidTimeType.IOC;
            spotTradeExt = SpotTradeExt.newBuilder().setStopPrice("0")
                    .setMinQty("0").setMaxPriceLevel(0).setTimeInForce(orderValidTimeType)
                    .build();
        }
        TakeOrderCmd cmd = TakeOrderCmd.newBuilder().setAccount(order.getAccount()).setAssetPair(pair).setUid(order.getOrderID())
                .setCharge(charge).setSide(rpcSide).setType(rpcType).setExt(spotTradeExt).build();
        Response response = null;
        try {
            response = client.getBlockingStub().take(cmd);
        } catch (Exception e) {
            logger.error("调用撮合系统失败", e);
        } finally {
            TradeCoreClientPool.returnObject(client);
        }

        return response;
    }


    private int unLockAsset(String account, String coinToUnlock, String amountToUnlock, Date updateTime) {
        return userDataService.updateUserAssert(account, coinToUnlock, "0", amountToUnlock, updateTime);

    }



}
