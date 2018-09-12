package com.bernard.mysql.dto;

public class OrderUpdate {
    private String orderid;
    private String remainToReduce;
    private String matchMoney;


    private OrderUpdate() {

    }

    public OrderUpdate(String orderid, String remainToReduce, String matchMoney) {
        this.orderid = orderid;
        this.remainToReduce = remainToReduce;
        this.matchMoney = matchMoney;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getRemainToReduce() {
        return remainToReduce;
    }

    public void setRemainToReduce(String remainToReduce) {
        this.remainToReduce = remainToReduce;
    }

    public String getMatchMoney() {
        return matchMoney;
    }

    public void setMatchMoney(String matchMoney) {
        this.matchMoney = matchMoney;
    }
}
