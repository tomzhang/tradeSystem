package com.bernard.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TradeSystemConfig {
    @Value("#{prop.KLineUrl}")
    private String KlineConfig;

    @Value("#{prop.GRPC_PORT}")
    private String grpcPort;


    public String getGrpcPort() {
        return grpcPort;
    }

    public void setGrpcPort(String grpcPort) {
        this.grpcPort = grpcPort;
    }

    public String getKlineConfig() {
        return KlineConfig;
    }

    public void setKlineConfig(String klineConfig) {
        KlineConfig = klineConfig;
    }
}
