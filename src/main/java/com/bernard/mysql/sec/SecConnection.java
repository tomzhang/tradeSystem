package com.bernard.mysql.sec;

import org.apache.commons.dbcp.BasicDataSource;
import org.bouncycastle.jce.provider.symmetric.AES;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class SecConnection extends BasicDataSource {
    @Override
    public synchronized void setPassword(String password) {
        try {
            this.password = new String(Base64.getDecoder().decode(password), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
