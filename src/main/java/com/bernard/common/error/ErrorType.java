package com.bernard.common.error;

public enum ErrorType {
    Insufficient("Insufficient balance", 10000);

    private String message;
    private int code;


    private ErrorType(String message, int code) {

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
}
