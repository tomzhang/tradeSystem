package com.bernard.mysql.dto;

public class UserAsset {
    private String account;
    private String totalAmount;
    private String aviliable;
    private int lockVersion;
    private String updateTime;
    private String liquidationTime;

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLiquidationTime() {
        return liquidationTime;
    }

    public void setLiquidationTime(String liquidationTime) {
        this.liquidationTime = liquidationTime;
    }

    @Override
    public String toString() {
        return "UserAsset{" +
                "account='" + account + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", aviliable='" + aviliable + '\'' +
                ", lockVersion=" + lockVersion +
                ", updateTime='" + updateTime + '\'' +
                ", liquidationTime='" + liquidationTime + '\'' +
                '}';
    }
}
