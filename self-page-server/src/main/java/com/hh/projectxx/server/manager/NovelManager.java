package com.hh.projectxx.server.manager;


import com.hh.projectxx.base.db.entity.Novel;
import com.hh.projectxx.server.db.mapper.NovelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service
public class NovelManager {
	@Resource
	private NovelMapper novelMapper;

	/**
	 * @description: 使用流水账形式展示列表
	 * @param lastId 上次最后的id
	 * @param count 请求的个数
	 * @date: 2018/4/10
	 **/
	public List<Novel> getNovelList(Integer lastId, int count) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("count", count);
		if (lastId != null) {
			params.put("startId", lastId);
			params.put("endId", lastId - count);
		} else {
			int topId = getTopId();
			params.put("startId", topId);
			params.put("endId", topId - count);
		}

		return novelMapper.selectByOffsetAndCount(params);
	}

	/**
	 * 用id选出带方向的带content的novel记录
	 * @param id
	 * @return
	 */
	public Novel getSingleItemWithContent(int id){
		Novel novel = novelMapper.selectByPrimaryKeyWithBlob(id);
		if (novel == null) {
			return null;
		}
		int topId = getTopId();
		novel.setTopId(topId);
		return novel;
	}

	/**
	 * @description: 获取最大id用于判断是否到头
	 * @param
	 * @date: 2018/4/10
	 **/
	private int getTopId() {
		return novelMapper.selectTopId();
	}
}
