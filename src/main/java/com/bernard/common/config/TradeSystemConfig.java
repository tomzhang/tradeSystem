package com.bernard.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TradeSystemConfig {
    @Value("#{prop.KLineUrl}")
    private String KlineConfig;

    @Value("#{prop.GRPC_PORT}")
    private String grpcPort;

    @Value("#{prop.TradeCore_Host}")
    private String tradeCoreHost;

    @Value("#{prop.TradeCore_Port}")
    private String tradeCorePort;

    @Value("#{prop.API_Service_Host}")
    private String apiServiceHost;

    @Value("#{prop.API_Service_Port}")
    private String apiServicePort;

    @Value("#{prop.Wallet_Service_Host}")
    private String walletServiceHost;

    @Value("#{prop.Wallet_Service_Port}")
    private String walletServicePort;



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

    public String getTradeCoreHost() {
        return tradeCoreHost;
    }

    public void setTradeCoreHost(String tradeCoreHost) {
        this.tradeCoreHost = tradeCoreHost;
    }

    public String getTradeCorePort() {
        return tradeCorePort;
    }

    public void setTradeCorePort(String tradeCorePort) {
        this.tradeCorePort = tradeCorePort;
    }

    public String getApiServiceHost() {
        return apiServiceHost;
    }

    public void setApiServiceHost(String apiServiceHost) {
        this.apiServiceHost = apiServiceHost;
    }

    public String getApiServicePort() {
        return apiServicePort;
    }

    public void setApiServicePort(String apiServicePort) {
        this.apiServicePort = apiServicePort;
    }

    public String getWalletServiceHost() {
        return walletServiceHost;
    }

    public void setWalletServiceHost(String walletServiceHost) {
        this.walletServiceHost = walletServiceHost;
    }

    public String getWalletServicePort() {
        return walletServicePort;
    }

    public void setWalletServicePort(String walletServicePort) {
        this.walletServicePort = walletServicePort;
    }
}
