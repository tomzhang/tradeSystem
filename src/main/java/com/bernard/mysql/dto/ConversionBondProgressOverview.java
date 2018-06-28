package com.bernard.mysql.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ConversionBondProgressOverview {
    private String secName;
    private String secCode;
    private String progress;//流程
    private Date applyDate;//申购日
    private BigDecimal applyPrice;//申购价格
    private String conversionSecCode;//可转债代码

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public BigDecimal getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(BigDecimal applyPrice) {
        this.applyPrice = applyPrice;
    }

    public String getConversionSecCode() {
        return conversionSecCode;
    }

    public void setConversionSecCode(String conversionSecCode) {
        this.conversionSecCode = conversionSecCode;
    }

    @Override
    public String toString() {
        return "ConversionBondProgressOverview{" +
                "secName='" + secName + '\'' +
                ", secCode='" + secCode + '\'' +
                ", progress='" + progress + '\'' +
                ", applyDate=" + applyDate +
                ", applyPrice=" + applyPrice +
                ", conversionSecCode='" + conversionSecCode + '\'' +
                '}';
    }
}
