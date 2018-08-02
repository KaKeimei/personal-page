package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.Photo;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;

public interface PhotoMapper {

	public Photo selectByPrimaryKey(long id);
	
	public List<Photo> selectPhotoByRange(HashMap<String, Object> params);
	
	public List<Photo> selectBySeriesId(int seriesId, RowBounds rowBounds);
	
	public int countBySeriesId(int seriesId);
	
	public int delete(long id);
	
	public int insert(Photo photo);
	
	public int insertList(List<Photo> photos);
	
	public int updateByPrimaryKeySelective(Photo photo);

}
