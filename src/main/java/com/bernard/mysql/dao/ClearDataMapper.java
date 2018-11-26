package com.bernard.mysql.dao;

import com.bernard.mysql.dto.Bonus;
import com.bernard.mysql.dto.Rebate;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface ClearDataMapper {
    int checkClearFlag(String dateString);

    int rebateCount(String dateString);

    int bonusCount(String dateString);

    List<Rebate> getRebateListByPage(String page, String state);

    List<Bonus> getBonusListByPage(String page, String state);

    int updateRebate(Rebate rebate);

    int updateBonus(Bonus bonus);

}
