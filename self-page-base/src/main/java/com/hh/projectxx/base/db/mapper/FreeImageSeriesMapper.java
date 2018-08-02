package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.FreeImageSeries;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface FreeImageSeriesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FreeImageSeries record);

    int insertSelective(FreeImageSeries record);
    
    public List<FreeImageSeries> selectAllUnimplemented();
	
    FreeImageSeries selectByPrimaryKey(Integer id);
    
    public List<FreeImageSeries> selectByRowbouds(RowBounds rowBounds);
    
    public int countAll();

    int updateByPrimaryKeySelective(FreeImageSeries record);

    int updateByPrimaryKey(FreeImageSeries record);
    
    public int clearExpiredStatus();
}