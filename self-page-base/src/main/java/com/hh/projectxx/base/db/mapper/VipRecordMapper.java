package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.VipRecord;

import java.util.List;

public interface VipRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipRecord record);

    int insertSelective(VipRecord record);

    VipRecord selectByPrimaryKey(Integer id);
    
    VipRecord selectLatestRecord(Integer userId);
    
    List<VipRecord> selectValidRecords();

    int updateByPrimaryKeySelective(VipRecord record);

    int updateByPrimaryKey(VipRecord record);
}