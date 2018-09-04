package com.bernard.mysql.dto;

public class OrderUpdate {
    private String orderid;
    private String remainToReduce;


    private OrderUpdate() {

    }

    public OrderUpdate(String orderid, String remainToReduce) {
        this.orderid = orderid;
        this.remainToReduce = remainToReduce;
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
}
