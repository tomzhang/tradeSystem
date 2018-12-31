package com.bernard.scheduled;


import com.bernard.common.config.AssetCoinfig;
import com.bernard.common.config.AssetPairConfig;
import com.bernard.common.config.TradeSystemConfig;
import com.bernard.common.utils.HttpsUtil;
import com.bernard.common.utils.TimeUtil;
import com.bernard.globle.ReportStateManager;
import com.bernard.mysql.dto.*;
import com.bernard.mysql.service.ClearDataService;
import com.bernard.mysql.service.UserDataService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class StateReportTask {
    private static Logger logger = Logger.getLogger(StateReportTask.class);
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ClearDataService clearDataService;

    @Autowired
    private TradeSystemConfig tradeSystemConfig;


    @Scheduled(cron = "0/10 * * * * ? ")
    public void reportState() {
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
                JSONObject resultJson;
                try {
                    resultJson = JSONObject.fromObject(HttpsUtil.get(url));
                } catch (Exception e) {
                    continue;
                }
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


    @Scheduled(cron = "0/10 * * * * ? ")
    public void updateUserBounty() {
        int clearFlag = clearDataService.checkClearFlag(TimeUtil.getClearTime());
        if (clearFlag == 0) {
            return;
        } else {
            clearBounty();
            clearRebase();
        }
    }


    private void clearBounty() {
        while (clearDataService.bonusCount(TimeUtil.getClearTime()) != 0) {
            //更新至中间状态
            List<Bonus> prebonusList = clearDataService.getBonusListByPage(TimeUtil.getClearTime(), "NOT_START");
            //List<UserAsset> userAssets = new ArrayList<>();
            String uuid = UUID.randomUUID().toString();
            if (prebonusList.size() == 0) {
                continue;
            }
            for (Bonus bonus : prebonusList) {
                bonus.setState(uuid);
                bonus.setOldState("NOT_START");
            }
            try {
                clearDataService.updateBonus(prebonusList);
            } catch (Exception e) {
                logger.info("已处理");
                continue;
            }
            List<Bonus> bonusList = clearDataService.getBonusListByPage(TimeUtil.getClearTime(), uuid);
            if (bonusList.size() == 0) {
                continue;
            }
            List<UserAsset> userAssets = new ArrayList<>();
            for (Bonus bonus : bonusList) {
                UserAsset userAsset = new UserAsset();
                userAsset.setAsset(bonus.getAsset());
                userAsset.setTotalAmount(bonus.getBouns());
                userAsset.setAccount(bonus.getAccount());
                userAsset.setUpdateTime(new Date());
                userAssets.add(userAsset);
            }
            clearDataService.batchUpdateUserAsset(userAssets);
            for (Bonus bonus : bonusList) {
                bonus.setState("COMPLETE");
                bonus.setOldState(uuid);
            }
            try {
                clearDataService.updateBonus(bonusList);
            } catch (Exception e) {
                continue;
            }
        }
    }


    private void clearRebase() {
        while (clearDataService.rebateCount(TimeUtil.getClearTime()) != 0) {
            List<Rebate> prerebateList = clearDataService.getRebateListByPage(TimeUtil.getClearTime(), "NOT_START");
            if (prerebateList.size() == 0) {
                continue;
            }
            // List<UserAsset> userAssets = new ArrayList<>();
            String uuid = UUID.randomUUID().toString();
            for (Rebate rebate : prerebateList) {
                rebate.setState(uuid);
                rebate.setOldState("NOT_START");
            }
            try {
                clearDataService.updateRebate(prerebateList);
            } catch (Exception e) {
                logger.info("已处理");
                continue;
            }
            List<Rebate> rebateList = clearDataService.getRebateListByPage(TimeUtil.getClearTime(), uuid);
            List<UserAsset> userAssets = new ArrayList<>();
            for (Rebate rebate : rebateList) {
                UserAsset userAsset = new UserAsset();
                userAsset.setAsset(rebate.getAsset());
                userAsset.setTotalAmount(rebate.getAmount());
                userAsset.setAccount(rebate.getAccount());
                userAsset.setUpdateTime(new Date());
                userAssets.add(userAsset);
            }

            clearDataService.batchUpdateUserAsset(userAssets);
            for (Rebate rebate : rebateList) {
                rebate.setState("COMPLETE");
                rebate.setOldState(uuid);
            }
            clearDataService.updateRebate(rebateList);
        }

    }


}
