package com.bernard.mysql.dto;

public enum AssertType {
    BTC("BTC", "1"), ETH("绿色", "2");
    private String assertName;
    private String methodName;


    private AssertType(String btc, String s) {
    }

    public static String getMethodNameByName(String name) {
        for (AssertType c : AssertType.values()) {
            if (c.assertName == name) {
                return c.methodName;
            }
        }
        return null;
    }
}
