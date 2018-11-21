package com.bernard.mysql.dao;

import com.bernard.mysql.dto.*;
import org.apache.ibatis.annotations.Param;
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

    List<Asset> queryAllAsset();

    List<String> queryAllAssetPair();

    int existTable(String tableName);

    List<Order> queryMatchOrders(String id1, String id2);

    int updateUserAssert(String account, String asset, String totalAmountToAdd, String aviToAdd, Date updateTime);

    int insertMatchFlow(String flowId, String sellSideOrderId, String sellSideAccount, String buySideOrderId, String buySideOrderAccount, String price, String amount, Date date, String assetPair, String buySideFee, String sellSideFee);

    int unlockUserAssertWhenFail(String account, String asset, String amountToUnlock, Date updateTime);

    int insertUserCancelOrder(String orderId, String account, Date updateTime);

    int insertOrderFee(String orderId, String assetPair, String FEE, Date date, String matchFlow, String asset);

    int updateUserChangeFlow(TransferFlow transferFlow);

    int decreaseUserAssert(String account, String asset, int oldVersion, String newTotal, String newAvi, Date date);

    int insertUserAsset(UserAsset userAsset);

    int createNewTable(@Param("name") String tableName);

    String queryUserAddr(@Param("account") String account, @Param("asset") String asset);

    int createAddrTable(@Param("name") String tableName);

    int insertUserTransferInAddr(@Param("account") String account, @Param("asset") String asset, @Param("addr") String addr, @Param("time") Date time);

    String queryUserAccountByAddr(@Param("addr") String addr, @Param("asset") String asset);

    int updateUserOrderForce(@Param("orderId") String orderId, @Param("remainToReduce") String remainToReduce, @Param("matchMoneyToAdd") String matchMoneyToAdd, @Param("orderFee") String orderFee);

    void mergeStateInfo(StateReport stateReport);

    void mergeCoinTransfer(CoinTransferRate rate);

    List<CoinTransferRate> queryAllCoinTransferRate(@Param("date") String date);

    void mergeAssetToBTC(AssetToBTCPrice assetToBTCPrice);








}
