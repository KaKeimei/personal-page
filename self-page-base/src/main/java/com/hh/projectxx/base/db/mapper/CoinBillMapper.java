package com.hh.projectxx.base.db.mapper;


import com.hh.projectxx.base.db.entity.CoinBill;

public interface CoinBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoinBill record);

    int insertSelective(CoinBill record);

    CoinBill selectByPrimaryKey(Integer id);
    
    CoinBill selectLatestRecordByUserId(Integer userId);
    
    CoinBill selectLatestLoginRecordByUserId(Integer userId);

    int updateByPrimaryKeySelective(CoinBill record);

    int updateByPrimaryKey(CoinBill record);
}