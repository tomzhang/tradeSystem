package com.bernard.mysql.dto;

import java.util.Date;

public class AssetUpdate {
    private String account;
    private String asset;
    private String totalAmountToAdd;
    private String aviToAdd;
    private Date updateTime;

    private AssetUpdate() {

    }


    public AssetUpdate(String account, String asset, String totalAmountToAdd, String aviToAdd, Date updateTime) {
        this.account = account;
        this.asset = asset;
        this.totalAmountToAdd = totalAmountToAdd;
        this.aviToAdd = aviToAdd;
        this.updateTime = updateTime;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getTotalAmountToAdd() {
        return totalAmountToAdd;
    }

    public void setTotalAmountToAdd(String totalAmountToAdd) {
        this.totalAmountToAdd = totalAmountToAdd;
    }

    public String getAviToAdd() {
        return aviToAdd;
    }

    public void setAviToAdd(String aviToAdd) {
        this.aviToAdd = aviToAdd;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
