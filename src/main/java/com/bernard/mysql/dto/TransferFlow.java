package com.bernard.mysql.dto;

import java.util.Date;

public class TransferFlow {
    private String asset;
    private String fromAddress;
    private String toAddress;
    private String amount;
    private String notes;
    private Date time;
    private TransferSide side;
    private int lockVersion;
    private String account;
    private boolean successState;
    private String fee;


    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public TransferSide getSide() {
        return side;
    }

    public void setSide(TransferSide side) {
        this.side = side;
    }

    public int getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(int lockVersion) {
        this.lockVersion = lockVersion;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isSuccessState() {
        return successState;
    }

    public void setSuccessState(boolean successState) {
        this.successState = successState;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "TransferFlow{" +
                "asset='" + asset + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", amount='" + amount + '\'' +
                ", notes='" + notes + '\'' +
                ", time=" + time +
                ", side=" + side +
                ", lockVersion=" + lockVersion +
                ", account='" + account + '\'' +
                ", successState=" + successState +
                '}';
    }
}
