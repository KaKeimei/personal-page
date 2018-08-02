package com.hh.projectxx.server.db.mapper;

import com.hh.projectxx.server.db.entity.Article;

import java.util.HashMap;
import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    List<Article> selectByOffsetAndCount(HashMap<String, Object> params);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);
}