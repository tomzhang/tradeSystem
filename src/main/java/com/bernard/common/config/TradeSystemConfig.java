package com.bernard.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TradeSystemConfig {
    @Value("#{prop.KLineUrl}")
    private String KlineConfig;


    public String getKlineConfig() {
        return KlineConfig;
    }

    public void setKlineConfig(String klineConfig) {
        KlineConfig = klineConfig;
    }
}
