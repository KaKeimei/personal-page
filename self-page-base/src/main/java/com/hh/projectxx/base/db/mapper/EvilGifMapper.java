package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.EvilGif;

import java.util.HashMap;
import java.util.List;

public interface EvilGifMapper {
	
	public EvilGif selectByPrimaryKey(int id);
	
	public List<EvilGif> selectGifByRange(HashMap<String, Object> params);
	
	public List<EvilGif> selectBySeriesId(int seriesId);
	
	public List<EvilGif> select20NewRecords();
	
	public int insert(EvilGif record);
	
	public int updateByPrimaryKeySelective(EvilGif record);
	
	public int updateSeries(EvilGif record);
	
	public int delete(EvilGif record);
}
