package com.hh.projectxx.server.db.mapper;

import com.hh.projectxx.base.db.entity.Novel;

import java.util.HashMap;
import java.util.List;

public interface NovelMapper {

	Novel selectByPrimaryKey(int id);

	Novel selectByPrimaryKeyWithBlob(int id);

	int selectTopId();

	List<Novel> selectByOffsetAndCount(HashMap<String, Object> params);
}
