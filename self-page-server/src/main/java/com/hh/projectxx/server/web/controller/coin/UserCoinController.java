package com.hh.projectxx.server.web.controller.coin;

import javax.annotation.Resource;

import com.hh.projectxx.base.db.entity.User;
import com.hh.projectxx.base.manager.UserCoinManager;
import com.hh.projectxx.base.manager.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.hh.projectxx.server.web.controller.BaseController;
@Controller
public class UserCoinController extends BaseController{
	
	@Resource
	private UserCoinManager userCoinManager;
	@Resource
	private UserManager userManager;
	
	
	
	@RequestMapping(value = "/getDailyBonus")
	@ResponseBody
	public String getDailyBonus() {
		int userId;
		try {
			userId = getAdminUser();
		} catch (Exception e) {
			return jsonResponse("ERROR", "需要登录");
		}
		User user = userManager.getUserById(userId);
		if (user == null) {
			return jsonResponse("ERROR", "用户不存在");
		}
		Integer bonusResult = userCoinManager.getLoginIncome(user);
		if (bonusResult == null) {
			return jsonResponse("ERROR", "领取失败");
		}else if (bonusResult == 0) {
			return jsonResponse("ERROR", "今日已经领取过,请明日再来");
		}else {
			return jsonResponse("SUCCESS", "成功领取金币" + bonusResult + "个");
		}
	}
	

}
