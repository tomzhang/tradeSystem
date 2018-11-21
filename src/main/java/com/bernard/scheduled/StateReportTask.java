package com.bernard.scheduled;

import com.bernard.App;
import com.bernard.common.config.AssetCoinfig;
import com.bernard.common.config.AssetPairConfig;
import com.bernard.common.config.TradeSystemConfig;
import com.bernard.common.utils.HttpsUtil;
import com.bernard.common.utils.TimeUtil;
import com.bernard.globle.ReportStateManager;
import com.bernard.mysql.dto.Asset;
import com.bernard.mysql.dto.AssetToBTCPrice;
import com.bernard.mysql.dto.CoinTransferRate;
import com.bernard.mysql.dto.StateReport;
import com.bernard.mysql.service.UserDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class StateReportTask {
    private static Logger logger = Logger.getLogger(StateReportTask.class);
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private TradeSystemConfig tradeSystemConfig;
//    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");

    @Scheduled(cron = "0/10 * * * * ? ")
    public void reportState() {
        // HashMap map=AssetCoinfig.assetMap;
        //logger.info("开始执行定时统计任务");
        List<StateReport> stateReports = new ArrayList<>();
        for (Map.Entry<String, Asset> entry : AssetCoinfig.assetMap.entrySet()) {
            StateReport report = new StateReport();
            report.setAssetPair(entry.getKey());
            report.setAmount("0");
            report.setFee(ReportStateManager.getFee(entry.getKey()).toString());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            report.setDate(sdf.format(date));
            stateReports.add(report);
        }
        for (Map.Entry<String, String> entry : AssetPairConfig.assetPairMap.entrySet()) {
            StateReport report = new StateReport();
            report.setAssetPair(entry.getKey());
            report.setAmount(ReportStateManager.getTotal(entry.getKey()).toString());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            report.setDate(sdf.format(date));
            report.setFee("0");
            stateReports.add(report);
        }
        userDataService.updateStateReport(stateReports);
        if (System.currentTimeMillis() % 5 == 0) {
            logger.info("定时统计任务执行完毕");
        }
    }

    @Scheduled(cron = "0/10 * * * * ? ")
    public void getCoinTransferRate() {
        try {
            List<CoinTransferRate> coinTransferRates = new ArrayList<>();
            for (Map.Entry<String, String> entry : AssetPairConfig.assetPairMap.entrySet()) {
                CoinTransferRate rate = new CoinTransferRate();
                String url = tradeSystemConfig.getKlineConfig() + entry.getKey() + "?limit=2";
                JSONObject resultJson = JSONObject.fromObject(HttpsUtil.get(url));
                JSONArray array = resultJson.getJSONArray("candles");
                if (array.size() != 2) {
                    logger.error("币对" + entry.getKey() + "昨日行情获取失败");
                    continue;
                } else {
                    JSONObject object = array.getJSONObject(1);
                    object.getString("close");
                    rate.setDate(TimeUtil.getTime());
                    rate.setPair(entry.getKey());
                    rate.setRate(object.getString("close"));
                    coinTransferRates.add(rate);
                }
            }
            userDataService.updateCoinTransferRate(coinTransferRates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/10 * * * * ? ")
    public void calcCoinTransferRate() {
        try {
            List<CoinTransferRate> rates = userDataService.queryAllCoinTransferRate(TimeUtil.getTime());
            HashMap<String, CoinTransferRate> ratesMap = new HashMap<>();
            for (CoinTransferRate rate : rates) {
                ratesMap.put(rate.getPair(), rate);
            }
            List<AssetToBTCPrice> assetToBTCPrices = new ArrayList<>();
            for (Map.Entry<String, Asset> entry : AssetCoinfig.assetMap.entrySet()) {
                AssetToBTCPrice assetToBTCPrice = new AssetToBTCPrice();
                Asset asset = entry.getValue();
                assetToBTCPrice.setAsset(asset.getAsset());
                String route = asset.getRoute();
                String[] subRoutes = route.split(";");
                BigDecimal rate = new BigDecimal(1);
                for (String a : subRoutes) {
                    String[] subr = a.split("\\|");
                    if (subr.length != 2) {
                        logger.fatal("转换路径配置错误1");
                    } else {
                        CoinTransferRate transferRate = ratesMap.get(subr[0]);
                        if (transferRate == null && !subr[1].equalsIgnoreCase("none")) {
                            logger.fatal("转换路径配置错误2" + a);
                            continue;
                        }
                        if (subr[1].equalsIgnoreCase("none")) {
                            continue;
                        }
                        if (subr[1].equalsIgnoreCase("multiply")) {
                            rate = rate.multiply(new BigDecimal(transferRate.getRate()));
                        } else if (subr[1].equalsIgnoreCase("divide")) {
                            rate = rate.divide(new BigDecimal(transferRate.getRate()), 8, BigDecimal.ROUND_DOWN);
                        }
                    }

                }
                assetToBTCPrice.setPrice(rate.toString());
                assetToBTCPrice.setDate(TimeUtil.getTime());
                assetToBTCPrices.add(assetToBTCPrice);

            }
            userDataService.updateAssetToBTCPrice(assetToBTCPrices);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
