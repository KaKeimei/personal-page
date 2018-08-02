package com.hh.projectxx.server.web.controller.novel;

import java.util.List;

import javax.annotation.Resource;


import com.hh.projectxx.base.db.entity.Novel;
import com.hh.projectxx.server.manager.NovelManager;
import com.hh.projectxx.server.manager.model.BreadCrumb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.hh.projectxx.server.web.controller.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NovelController extends BaseController{
	@Resource
	private NovelManager novelManager;

	private static int DEFAULT_PAGE_LIMIT = 30;
	
	private static Logger logger = LoggerFactory.getLogger(NovelController.class);
	
	@RequestMapping(value = "novel")
	public String getNovelList(Model model, Integer offset){
		if (offset != null && offset == 0) {
			offset = null;
		}
		List<Novel> resultList = novelManager.getNovelList(offset, DEFAULT_PAGE_LIMIT);
		model.addAttribute("novelList", resultList);
		setBreadCrumb(model, "激情小说", "./novel", null);
		return "index/novel";
	}

	@RequestMapping(value = "novel!more")
	@ResponseBody
	public String getMoreNovel(Integer offset) {
		if (offset != null && offset == 0) {
			offset = null;
		}
		List<Novel> resultList = novelManager.getNovelList(offset, DEFAULT_PAGE_LIMIT);
		return jsonResult(resultList);
	}
	
	@RequestMapping(value = "novelItem")
	public String getNovelItem(Model model, Integer id){
		if (id == null) {
			id = 1;
		}
		Novel novel = novelManager.getSingleItemWithContent(id);
		if (novel == null || novel.getTitle() == null) {
			return "admin/admin_index";
		}
		int topId = novel.getTopId();
		if (novel.getId().equals(topId)) {
			model.addAttribute("isFirst", true);
		}
		logger.info("fetched novel id is " + novel.getId());
		String title = novel.getTitle();
		title = title.trim();
		title = title.replace(" ", "");
		novel.setTitle(title);
		model.addAttribute("novel", novel);
		setBreadCrumb(model, "激情小说", "./novel", novel.getTitle());
		return "index/novel_item";
	}
}
