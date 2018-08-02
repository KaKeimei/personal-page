package com.hh.projectxx.base.db.mapper;

import com.hh.projectxx.base.db.entity.EvilGifSeries;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface EvilGifSeriesMapper {
	
	public EvilGifSeries selectByPrimaryKey(int id);
	
	public EvilGifSeries selectByMaxId();
	
	public int countAll();
	
	public int selectMaxId();
	
	public List<EvilGifSeries> selectByRowbouds(RowBounds rowBounds);
	
	public int insert(EvilGifSeries record);
}
