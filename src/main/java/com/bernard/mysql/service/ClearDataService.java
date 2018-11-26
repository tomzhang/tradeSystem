package com.bernard.mysql.service;

import com.bernard.mysql.dto.Bonus;
import com.bernard.mysql.dto.Rebate;
import com.bernard.mysql.dto.UserAsset;

import java.util.List;

public interface ClearDataService {

    int checkClearFlag(String dateString);

    int rebateCount(String dateString);

    int bonusCount(String dateString);

    List<Rebate> getRebateListByPage(String dateString, String state);

    List<Bonus> getBonusListByPage(String dateString, String state);

    int updateRebate(List<Rebate> rebate);

    int updateBonus(List<Bonus> bonus) throws Exception;

    int batchUpdateUserAsset(List<UserAsset> userAssets);

}
