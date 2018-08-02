package com.hh.projectxx.base.db.mapper;


import com.hh.projectxx.base.db.entity.CoinConsume;

import java.util.List;

public interface CoinConsumeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CoinConsume record);

    int insertSelective(CoinConsume record);

    CoinConsume selectByPrimaryKey(Integer id);
    
    List<CoinConsume> selectByParams(CoinConsume record);

    int updateByPrimaryKeySelective(CoinConsume record);

    int updateByPrimaryKey(CoinConsume record);
}