package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.PhotoSeries;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PhotoSeriesMapper {
	
	public PhotoSeries selectByPrimaryKey(int id);
	
	public int countAll(int type);
	
	public int selectMaxId();
	
	public List<PhotoSeries> selectByRowbouds(int type, RowBounds rowBounds);
	
	public List<PhotoSeries> selectInvalidByRowbouds(RowBounds rowBounds);
	
	public List<PhotoSeries> selectAllUnimplemented();
	
	public int insert(PhotoSeries record);
	
	public int insertList(List<PhotoSeries> list);
	
	public int updateByPrimaryKeySelective(PhotoSeries record);
	
	public int clearExpiredStatus();

}
