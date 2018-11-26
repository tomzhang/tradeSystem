package com.bernard.mysql.service.impl;

import com.bernard.mysql.dao.ClearDataMapper;
import com.bernard.mysql.dao.UserDataMapper;
import com.bernard.mysql.dto.Bonus;
import com.bernard.mysql.dto.Rebate;
import com.bernard.mysql.dto.UserAsset;
import com.bernard.mysql.service.ClearDataService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClearDataServiceImpl implements ClearDataService {

    @Autowired
    private UserDataMapper userDataMapper;

    @Autowired
    private ClearDataMapper clearDataMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public int checkClearFlag(String dateString) {
        return clearDataMapper.checkClearFlag(dateString);
    }

    @Override
    public int rebateCount(String dateString) {
        return clearDataMapper.rebateCount(dateString);
    }

    @Override
    public int bonusCount(String dateString) {
        return clearDataMapper.bonusCount(dateString);
    }

    @Override
    public List<Rebate> getRebateListByPage(String dateString, String state) {
        return clearDataMapper.getRebateListByPage(dateString, state);
    }

    @Override
    public List<Bonus> getBonusListByPage(String dateString, String state) {
        return clearDataMapper.getBonusListByPage(dateString, state);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateRebate(List<Rebate> rebate) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        ClearDataMapper mapper = sqlSession.getMapper(ClearDataMapper.class);
        for (Rebate rebate1 : rebate) {
            mapper.updateRebate(rebate1);
        }
        sqlSession.flushStatements();
        return 0;


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateBonus(List<Bonus> bonus) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        ClearDataMapper mapper = sqlSession.getMapper(ClearDataMapper.class);
        //int updateCount=0;
        for (Bonus bonus1 : bonus) {
            mapper.updateBonus(bonus1);
            //updateCount=updateCount+update;
        }
        sqlSession.flushStatements();
        return 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchUpdateUserAsset(List<UserAsset> userAssets) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, true);
        UserDataMapper mapper = sqlSession.getMapper(UserDataMapper.class);
        for (UserAsset userAsset : userAssets) {
            mapper.updateUserAssert(userAsset.getAccount(), userAsset.getAsset(), userAsset.getTotalAmount(), userAsset.getTotalAmount(), userAsset.getUpdateTime());
        }
        sqlSession.flushStatements();
        return 0;
    }
}
