package com.bernard.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class SecUtil {
    public static void main(String[] args) {
        try {
            System.out.println(Base64.getEncoder().encodeToString(args[0].getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
