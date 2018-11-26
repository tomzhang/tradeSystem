package com.bernard.mysql.dto;

public class Bonus {
    private String asset;
    private String account;
    private String bouns;
    private String cleardate;
    private String state;
    private String oldState;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBouns() {
        return bouns;
    }

    public void setBouns(String bouns) {
        this.bouns = bouns;
    }

    public String getCleardate() {
        return cleardate;
    }

    public void setCleardate(String cleardate) {
        this.cleardate = cleardate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOldState() {
        return oldState;
    }

    public void setOldState(String oldState) {
        this.oldState = oldState;
    }
}
