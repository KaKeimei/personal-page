package com.hh.projectxx.server.manager;

import com.hh.projectxx.base.db.field.BaseStatus;
import com.hh.projectxx.server.db.entity.Article;
import com.hh.projectxx.server.db.field.ArticleCateType;
import com.hh.projectxx.server.db.mapper.ArticleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class ArticleManager {
	@Resource
	private ArticleMapper articleMapper;

	public Article getArticleDetail(int id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	/**
	 * @description: 批量选取文章，按时间倒叙，startTime是之前最后一个文章的时间
	 * @param cate
	 * @param startTime
	 * @param limit
	 * @date: 2018/5/7
	 **/
	public List<Article> getArticleList(String cate, Date startTime, int limit) {
		HashMap<String, Object> params = new HashMap<>();
		params.put("cate", cate);
		params.put("startTime", startTime);
		params.put("limit", limit);
		return articleMapper.selectByOffsetAndCount(params);

	}

	public int createArticle(String title, ArticleCateType type, String data) {
		if (StringUtils.isBlank(title) || type == null) {
			return 0;
		}
		Article article = new Article();
		article.setCate(type.getValue());
		article.setTitle(title);
		article.setData(data.trim());
		article.setCreateTime(new Date());
		article.setStatus(BaseStatus.NORMAL.value());
		return articleMapper.insert(article);
	}

	public int modifyArticle(Article record) {
		if (record == null) {
			return 0;
		}
		record.setModifyTime(new Date());
		return articleMapper.updateByPrimaryKeySelective(record);
	}
}
