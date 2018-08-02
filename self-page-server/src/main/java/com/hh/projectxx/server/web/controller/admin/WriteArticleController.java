package com.hh.projectxx.server.web.controller.admin;

import com.hh.projectxx.server.db.entity.Article;
import com.hh.projectxx.server.db.field.ArticleCateType;
import com.hh.projectxx.server.manager.ArticleManager;
import com.hh.projectxx.server.manager.model.BreadCrumb;
import com.hh.projectxx.server.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by zhangli on 2017/8/7
 */
@Controller
public class WriteArticleController extends BaseController{

	private static int DEFAULT_PAGE_LIMIT = 10;

	@Resource
	private ArticleManager articleManager;

	@RequestMapping(value = "publish")
	public String publishNewArticle(Model model) {
		ArticleCateType[] types = ArticleCateType.values();
		model.addAttribute("types", types);
		setBreadCrumb(model, "发布新文章", "./publish", null);

		return "admin/publish";
	}

	@RequestMapping(value = "modify")
	public String modifyArticle(Model model, Integer aid) {
		if (aid == null) {
			return "admin/modify";
		}
		Article article = articleManager.getArticleDetail(aid);
		if (article == null) {
			return "admin/modify";
		}
		model.addAttribute("article", article);
		ArticleCateType[] types = ArticleCateType.values();
		model.addAttribute("types", types);
		BreadCrumb crumb = new BreadCrumb();
		crumb.setSecondName("修改文章");
		crumb.setSecondUrl("./modify?aid=" + aid);
		return "admin/modify";
	}

	@RequestMapping(value = "doPublish")
	@ResponseBody
	public String doPublishArticle(String cateType, String title, String data) {
		ArticleCateType type = ArticleCateType.parseType(cateType);
		if (type == null) {
			return "error";
		}
		articleManager.createArticle(title, type, data);

		return "ok";
	}

	@RequestMapping(value = "doModify")
	@ResponseBody
	public String doModifyArticle(Integer aid, String cateType, String title, String data) {
		if (aid == null) {
			return "error";
		}
		Article record = articleManager.getArticleDetail(aid);
		ArticleCateType type = ArticleCateType.parseType(cateType);
		if (type == null) {
			return "error";
		}
		record.setCate(type.getValue());
		record.setTitle(title.trim());
		record.setData(data);
		articleManager.modifyArticle(record);
		return "ok";
	}

	@RequestMapping(value = "article!detail")
	public String getArticleDetail(Integer aid, HttpServletResponse response, Model model) throws IOException {
		if (aid == null) {
			return redirect("/web/index");
		}
		Article article = articleManager.getArticleDetail(aid);
		if (article == null) {
			return redirect("/web/index");
		}
		model.addAttribute("article", article);
		ArticleCateType type = ArticleCateType.parseType(article.getCate());
		String secondName = type.getDesc();
		String secondUrl = "/web/article!list?cate=" + article.getCate();
		setBreadCrumb(model, secondName, secondUrl, article.getTitle());

		return "article_detail";
	}

	@RequestMapping(value = "article!list")
	public String getArticleList(String cate, Model model) {
		if (StringUtils.isBlank(cate)) {
			cate = null;
		}
		if (cate != null) {
			String secondUrl = "/web/article!list?cate=" + cate;
			BreadCrumb crumb = new BreadCrumb();
			crumb.setSecondUrl(secondUrl);
			ArticleCateType cateType = ArticleCateType.parseType(cate);
			crumb.setSecondName(cateType.getDesc());
			model.addAttribute("breadCrumb", crumb);
			model.addAttribute("desc", cateType.getDesc());
			model.addAttribute("cate", cate);
		}
		List<Article> articles = articleManager.getArticleList(cate, null, DEFAULT_PAGE_LIMIT);
		model.addAttribute("articles", articles);
		return "article_list";
	}

	@RequestMapping(value = "article!more")
	@ResponseBody
	public String getMoreNovel(String cate, Long offset) {
		Date start = null;
		if (offset != null) {
			start = new Date(offset);
		}
		if (StringUtils.isBlank(cate)) {
			cate = null;
		}
		List<Article> resultList = articleManager.getArticleList(cate, start, DEFAULT_PAGE_LIMIT);
		return jsonResult(resultList);
	}


}
