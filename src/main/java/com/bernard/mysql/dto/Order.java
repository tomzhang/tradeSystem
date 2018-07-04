package com.bernard.mysql.dto;

import io.grpc.tradesystem.service.UserOrderRequest;

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
    int LockVersion;// 乐观锁版本号*/

    public Order() {
        this.orderTime = System.currentTimeMillis() + "";
        this.surviveTime = "";
        this.state = OrderState.OPEN;
        this.remain = "0";
        this.AssertLimit = "0";
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
        Order order = new Order();
        order.setPrice(request.getPrice());
        order.setAssertLimit(request.getAssertLimit());
        order.setOrderType(OrderType.valueOf(request.getOrderType()));
        order.setAmount(request.getAmount());
        order.setOrderSide(OrderSide.valueOf(request.getOrderSide()));
        order.setAssetPair(request.getAssetPair());
        order.setAccount(request.getAccount());
        return order;
    }
}
