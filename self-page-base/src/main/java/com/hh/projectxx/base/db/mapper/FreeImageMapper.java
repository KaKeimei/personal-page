package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.FreeImage;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

public interface FreeImageMapper {
	public FreeImage selectByPrimaryKey(long id);
	
	public List<FreeImage> selectFreeImageByRange(HashMap<String, Object> params);
	
	public List<FreeImage> selectBySeriesId(int seriesId, RowBounds rowBounds);
	
	public int countBySeriesId(int seriesId);
	
	public int delete(long id);
	
	public int insert(FreeImage freeImage);
	
	public int insertList(List<FreeImage> freeImages);
	
	public int updateByPrimaryKeySelective(FreeImage freeImage);

}
