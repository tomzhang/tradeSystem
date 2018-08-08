package com.bernard.mysql.dto;

import java.util.Date;

public class UserAsset {
    private String asset;
    private String account;
    private String totalAmount;
    private String aviliable;
    private int lockVersion;
    private Date updateTime;
    private Date liquidationTime;

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

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAviliable() {
        return aviliable;
    }

    public void setAviliable(String aviliable) {
        this.aviliable = aviliable;
    }

    public int getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(int lockVersion) {
        this.lockVersion = lockVersion;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLiquidationTime() {
        return liquidationTime;
    }

    public void setLiquidationTime(Date liquidationTime) {
        this.liquidationTime = liquidationTime;
    }

    @Override
    public String toString() {
        return "UserAsset{" +
                "asset='" + asset + '\'' +
                ", account='" + account + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", aviliable='" + aviliable + '\'' +
                ", lockVersion=" + lockVersion +
                ", updateTime=" + updateTime +
                ", liquidationTime=" + liquidationTime +
                '}';
    }
}
