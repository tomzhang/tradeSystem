package com.bernard.mysql.dto;

import io.grpc.tradesystem.service.UserOrderRequest;

import java.util.UUID;

public class Order {
    String orderID;// 下单号（主键）
    String orderTime; //下单时间
    String account;// 账号
    String assetPair;// 标的（ETH\BTC）
    OrderSide orderSide;//BUY/SELL
    OrderType orderType; //限价/市价
    String surviveTime;// 订单有效时间
    String amount;// 委托数量
    String price;// 价格
    OrderState state;// 状态（未成交/部分成交/全部成交/手动撤单/超时撤单）
    String remain;// 未成交数量
    String AssertLimit; //市价买入最大金额
    String feeRate;//手续费率
    int LockVersion;// 乐观锁版本号*/

    private Order() {
        this.orderTime = System.currentTimeMillis() + "";
        this.surviveTime = "";
        this.state = OrderState.OPEN;
        this.remain = "0";
        this.AssertLimit = "0";
        this.LockVersion = 0;
    }

    public Order(String account, String assetPair, OrderSide orderSide, OrderType orderType, String surviveTime, String amount, String price, String assertLimit, String feeRate) {
        this.orderID = UUID.randomUUID().toString();
        this.orderTime = System.currentTimeMillis() + "";
        this.account = account;
        this.assetPair = assetPair;
        this.orderSide = orderSide;
        this.orderType = orderType;
        this.surviveTime = surviveTime;
        this.state = OrderState.OPEN;
        this.remain = amount;
        this.price = price;
        this.AssertLimit = assertLimit;
        this.amount = amount;
        this.feeRate = feeRate;
        this.LockVersion = 0;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAssetPair() {
        return assetPair;
    }

    public void setAssetPair(String assetPair) {
        this.assetPair = assetPair;
    }

    public OrderSide getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getSurviveTime() {
        return surviveTime;
    }

    public void setSurviveTime(String surviveTime) {
        this.surviveTime = surviveTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getAssertLimit() {
        return AssertLimit;
    }

    public void setAssertLimit(String assertLimit) {
        AssertLimit = assertLimit;
    }

    public int getLockVersion() {
        return LockVersion;
    }

    public void setLockVersion(int lockVersion) {
        LockVersion = lockVersion;
    }

    public static Order fromUserOrderRequest(UserOrderRequest request) {
        //Order order = new Order(request.getAccount())
        Order order = new Order(request.getAccount(), request.getAssetPair(), OrderSide.valueOf(request.getOrderSide()),
                OrderType.valueOf(request.getOrderType()), "", request.getAmount(), request.getPrice(), request.getAssertLimit(), request.getFeeRate());
        return order;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", account='" + account + '\'' +
                ", assetPair='" + assetPair + '\'' +
                ", orderSide=" + orderSide +
                ", orderType=" + orderType +
                ", surviveTime='" + surviveTime + '\'' +
                ", amount='" + amount + '\'' +
                ", price='" + price + '\'' +
                ", state=" + state +
                ", remain='" + remain + '\'' +
                ", AssertLimit='" + AssertLimit + '\'' +
                ", LockVersion=" + LockVersion +
                '}';
    }
}
