package com.bernard.common.error;

public enum ErrorType {
    //下单
    Insufficient("Insufficient Balance", 10000),
    FrequencyLimit("Order Frequency Limit", 10001),
    InternalError("Internal Error", 10002),
    UnsupportedAsset("Unsupported Asset", 10003),
    AccountError("Account Error", 10004),
    //撤单
    OrderError("Order Error", 10005),
    //
    MatchAccoutError("Match account addr Error", 10006),

    //提现
    AssetChanged("Asset amount changed", 10007);



    private String message;
    private int code;


    private ErrorType(String message, int code) {
        this.message = message;
        this.code = code;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static void main(String[] args) {
        ErrorType.InternalError.getCode();
    }
}
