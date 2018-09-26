package com.bernard.scheduled;

import com.bernard.App;
import com.bernard.common.config.AssetCoinfig;
import com.bernard.common.config.AssetPairConfig;
import com.bernard.globle.ReportStateManager;
import com.bernard.mysql.dto.StateReport;
import com.bernard.mysql.service.UserDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;


@Component
public class StateReportTask {
    private static Logger logger = Logger.getLogger(StateReportTask.class);
    @Autowired
    private UserDataService userDataService;
//    private UserDataService userDataService = (UserDataService) App.context.getBean("userDataServiceImpl");

    @Scheduled(cron = "0/10 * * * * ? ")
    public void reportState() {
        // HashMap map=AssetCoinfig.assetMap;
        logger.info("开始执行定时统计任务");
        List<StateReport> stateReports = new ArrayList<>();
        for (Map.Entry<String, String> entry : AssetCoinfig.assetMap.entrySet()) {

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
        logger.info("定时统计任务执行完毕");
    }

}
