package com.bernard.mysql.service.impl;


import com.bernard.mysql.dao.UserDataMapper;
import com.bernard.mysql.dto.*;

import com.bernard.mysql.service.UserDataService;
import com.bernard.tradesystem.tasks.MatchOrderCancelTask;
import io.grpc.tradesystem.service.MatchOrderRequest;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserDataServiceImpl implements UserDataService {

    private static Logger logger = Logger.getLogger(UserDataServiceImpl.class);

    @Autowired
    private UserDataMapper userDataMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public void insertUserOrder(Order userOrder) {
        userDataMapper.inserUserOrder(userOrder);
    }


    @Override
    public int updateUserOrder(Order userOrder) {
        return userDataMapper.updateUserOrder(userOrder);
    }

    @Override
    public int updateUserOrderForce(String orderId, String remainToReduce, String matchMoneyToAdd, String feeToAdd) {
        return userDataMapper.updateUserOrderForce(orderId, remainToReduce, matchMoneyToAdd, feeToAdd);
    }

    @Override
    public int insertMatchFlow(String flowId, String sellSideOrderId, String sellSideOrderAccount, String buySideOrderId,
                               String buySideOrderAccount, String price, String amount, Date date, String assetPair, String buysideFee, String sellsideFee) {
        return userDataMapper.insertMatchFlow(flowId, sellSideOrderId, sellSideOrderAccount,
                buySideOrderId, buySideOrderAccount, price, amount, date, assetPair, buysideFee, sellsideFee);
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
    public List<Asset> queryAllAsset() {
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

    @Override
    public int insertUserAsset(UserAsset userAsset) {
        return userDataMapper.insertUserAsset(userAsset);
    }

    @Override
    public int createNewTable(String tableName) {
        return userDataMapper.createNewTable(tableName);
    }

    @Override
    public String queryUserAddr(String account, String asset) {
        return userDataMapper.queryUserAddr(account, asset);
    }

    @Override
    public int createAddrTable(String tableName) {
        return userDataMapper.createAddrTable(tableName);
    }

    @Override
    public String queryAccountByAddr(String addr, String asset) {
        return userDataMapper.queryUserAccountByAddr(addr, asset);
    }

    @Override
    public int insertUserTransferInAddr(String account, String asset, String addr, Date time) {
        return userDataMapper.insertUserTransferInAddr(account, asset, addr, time);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchUpdateMatchOrderTask(List<OrderUpdate> orderUpdates, MatchOrderRequest matchOrderRequest, String buySideFee, String sellSideFee) {
   /*     SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);*/
        /*for (AssetUpdate update : updateList) {
            SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
            UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
            mapper.updateUserAssert(update.getAccount(), update.getAsset(), update.getTotalAmountToAdd(),
                    update.getAviToAdd(), update.getUpdateTime());
            sqlSession.flushStatements();

        }*/
     /*   for (AssetUpdate update : updateList) {
            logger.info("开始更新钱+++++++！！！！！");
            userDataMapper.updateUserAssert(update.getAccount(), update.getAsset(), update.getTotalAmountToAdd(),
                    update.getAviToAdd(), update.getUpdateTime());
            logger.info("更新钱完毕+++++++！！！！！");
        }*/
        //2.成交流水
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
        if (matchOrderRequest != null) {
            mapper.insertMatchFlow(matchOrderRequest.getMatchOrderWaterflow(),
                    matchOrderRequest.getSellSideOrderId(),
                    matchOrderRequest.getSellSideAccount(),
                    matchOrderRequest.getBuySideOrderId(),
                    matchOrderRequest.getBuySideAccount(),
                    matchOrderRequest.getMatchPrice(),
                    matchOrderRequest.getMatchAmount(),
                    new Date(), matchOrderRequest.getAsset(), buySideFee, sellSideFee);
        }

        //3.订单更新
        if (orderUpdates != null && orderUpdates.size() != 0) {
            for (OrderUpdate orderUpdate : orderUpdates) {
                mapper.updateUserOrderForce(orderUpdate.getOrderid(), orderUpdate.getRemainToReduce(), orderUpdate.getMatchMoney(), orderUpdate.getOrderFee());
            }
        }
        sqlSession.flushStatements();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchUpdateUserOrder(List<OrderUpdate> orderUpdates) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
        for (OrderUpdate orderUpdate : orderUpdates) {
            mapper.updateUserOrderForce(orderUpdate.getOrderid(), orderUpdate.getRemainToReduce(), orderUpdate.getMatchMoney(), orderUpdate.getOrderFee());
        }
        sqlSession.flushStatements();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStateReport(List<StateReport> stateReports) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
        for (StateReport report : stateReports) {
            mapper.mergeStateInfo(report);
        }
        sqlSession.flushStatements();

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCoinTransferRate(List<CoinTransferRate> coinTransferRates) {
        logger.info("=======================================================================================");

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
        for (CoinTransferRate coinTransferRate : coinTransferRates) {
            mapper.mergeCoinTransfer(coinTransferRate);
        }
        sqlSession.flushStatements();
        logger.info("=======================================================================================");
    }

    @Override
    public List<CoinTransferRate> queryAllCoinTransferRate(String date) {
        return userDataMapper.queryAllCoinTransferRate(date);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAssetToBTCPrice(List<AssetToBTCPrice> assetToBTCPrices) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
        for (AssetToBTCPrice assetToBTCPrice : assetToBTCPrices) {
            mapper.mergeAssetToBTC(assetToBTCPrice);
        }
        sqlSession.flushStatements();
    }
}
