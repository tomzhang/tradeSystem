package com.bernard.mysql.dao;

import com.bernard.mysql.dto.ConversionBond;
import com.bernard.mysql.dto.ConversionBondProgressOverview;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface UserDataMapper {
    public List<ConversionBond> getConversionBondByday(String dateString);
    public List<ConversionBond> getConversionBondHistroyByday(String dateString);
    public List<ConversionBond> updateYesterdayConversionBond(ConversionBond conversionBond);
    public List<ConversionBondProgressOverview> getConversionBondProgressOverview(String date);
}
