package com.bernard.mysql.dto;



import java.math.BigDecimal;
import java.util.Date;

public class ConversionBond {
    private String secCode;
    private String secName;
    private String conversionSecCode;
    private BigDecimal PB;
    private BigDecimal conversionProportion;
    private BigDecimal conversionPrice;
    private BigDecimal bondValue;
    private BigDecimal optionValue;
    private BigDecimal buyBackPrice;
    private BigDecimal redemPrice;
    private Date endDate;
    private BigDecimal remainingYear;
    private BigDecimal betaxYield;
    private BigDecimal aftertaxYield;
    private BigDecimal buyBackProfit;
    private String interestTerms;

    private BigDecimal yieldRateBeforeTax;//税前收益率
    private BigDecimal yieldRateAfterTax;//税后收益率
    private BigDecimal stockVolatility;//股票波动
    private BigDecimal dividendRate;
    private BigDecimal optPrice;//期权价值
    private BigDecimal conversionStockValue;//转股价值
    private BigDecimal premiumRate;//溢价率
    private BigDecimal PureDebtValue;//纯债价值
    private String timeStamp;//时间

    private String secCodeDataBase;//数据库中的secCode
    private String conversionCodeDataBase;//数据库中的coversionCode

    private BigDecimal qyzInterest;

    private BigDecimal issueAmount;//份额
    private BigDecimal remainingProportion;//剩余规模

    private BigDecimal putbacktriggermaxspan;//回售最大期限
    private BigDecimal putbacktriggerspan;//回售触发期限
    private BigDecimal redeemmaxspan;//强赎最大期限
    private BigDecimal redeemspan;//强赎触发期限



    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConversionSecCode() {
        return conversionSecCode;
    }

    public void setConversionSecCode(String conversionSecCode) {
        this.conversionSecCode = conversionSecCode;
    }

    public BigDecimal getPB() {
        return PB;
    }

    public void setPB(BigDecimal PB) {
        this.PB = PB;
    }

    public BigDecimal getConversionProportion() {
        return conversionProportion;
    }

    public void setConversionProportion(BigDecimal conversionProportion) {
        this.conversionProportion = conversionProportion;
    }

    public BigDecimal getConversionPrice() {
        return conversionPrice;
    }

    public void setConversionPrice(BigDecimal conversionPrice) {
        this.conversionPrice = conversionPrice;
    }

    public BigDecimal getBondValue() {
        return bondValue;
    }

    public void setBondValue(BigDecimal bondValue) {
        this.bondValue = bondValue;
    }

    public BigDecimal getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(BigDecimal optionValue) {
        this.optionValue = optionValue;
    }

    public BigDecimal getBuyBackPrice() {
        return buyBackPrice;
    }

    public void setBuyBackPrice(BigDecimal buyBackPrice) {
        this.buyBackPrice = buyBackPrice;
    }

    public BigDecimal getRedemPrice() {
        return redemPrice;
    }

    public void setRedemPrice(BigDecimal redemPrice) {
        this.redemPrice = redemPrice;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getRemainingYear() {
        return remainingYear;
    }

    public void setRemainingYear(BigDecimal remainingYear) {
        this.remainingYear = remainingYear;
    }

    public BigDecimal getBetaxYield() {
        return betaxYield;
    }

    public void setBetaxYield(BigDecimal betaxYield) {
        this.betaxYield = betaxYield;
    }

    public BigDecimal getAftertaxYield() {
        return aftertaxYield;
    }

    public void setAftertaxYield(BigDecimal aftertaxYield) {
        this.aftertaxYield = aftertaxYield;
    }

    public BigDecimal getBuyBackProfit() {
        return buyBackProfit;
    }

    public void setBuyBackProfit(BigDecimal buyBackProfit) {
        this.buyBackProfit = buyBackProfit;
    }



    public String getInterestTerms() {
        return interestTerms;
    }

    public void setInterestTerms(String interestTerms) {
        this.interestTerms = interestTerms;
    }

    public BigDecimal getYieldRateBeforeTax() {
        return yieldRateBeforeTax;
    }

    public void setYieldRateBeforeTax(BigDecimal yieldRateBeforeTax) {
        this.yieldRateBeforeTax = yieldRateBeforeTax;
    }

    public BigDecimal getYieldRateAfterTax() {
        return yieldRateAfterTax;
    }

    public void setYieldRateAfterTax(BigDecimal yieldRateAfterTax) {
        this.yieldRateAfterTax = yieldRateAfterTax;
    }



    public BigDecimal getStockVolatility() {
        return stockVolatility;
    }

    public void setStockVolatility(BigDecimal stockVolatility) {
        this.stockVolatility = stockVolatility;
    }


    public BigDecimal getDividendRate() {
        return dividendRate;
    }

    public void setDividendRate(BigDecimal dividendRate) {
        this.dividendRate = dividendRate;
    }

    public BigDecimal getOptPrice() {
        return optPrice;
    }

    public void setOptPrice(BigDecimal optPrice) {
        this.optPrice = optPrice;
    }

    public BigDecimal getConversionStockValue() {
        return conversionStockValue;
    }

    public void setConversionStockValue(BigDecimal conversionStockValue) {
        this.conversionStockValue = conversionStockValue;
    }

    public BigDecimal getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(BigDecimal premiumRate) {
        this.premiumRate = premiumRate;
    }

    public BigDecimal getPureDebtValue() {
        return PureDebtValue;
    }

    public void setPureDebtValue(BigDecimal pureDebtValue) {
        PureDebtValue = pureDebtValue;
    }

    public BigDecimal getQyzInterest() {
        return qyzInterest;
    }

    public void setQyzInterest(BigDecimal qyzInterest) {
        this.qyzInterest = qyzInterest;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSecCodeDataBase() {
        return secCodeDataBase;
    }

    public void setSecCodeDataBase(String secCodeDataBase) {
        this.secCodeDataBase = secCodeDataBase;
    }

    public String getConversionCodeDataBase() {
        return conversionCodeDataBase;
    }

    public void setConversionCodeDataBase(String conversionCodeDataBase) {
        this.conversionCodeDataBase = conversionCodeDataBase;
    }

    public BigDecimal getIssueAmount() {
        return issueAmount;
    }

    public void setIssueAmount(BigDecimal issueAmount) {
        this.issueAmount = issueAmount;
    }

    public BigDecimal getRemainingProportion() {
        return remainingProportion;
    }

    public void setRemainingProportion(BigDecimal remainingProportion) {
        this.remainingProportion = remainingProportion;
    }



}
