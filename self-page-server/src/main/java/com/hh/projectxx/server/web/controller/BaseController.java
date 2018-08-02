
package com.hh.projectxx.server.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hh.projectxx.base.util.AuthUtil;
import com.hh.projectxx.base.util.JSON;
import com.hh.projectxx.server.manager.model.BreadCrumb;
import com.hh.projectxx.server.web.view.response.AdminApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/web/*", produces = { "text/html; charset=utf-8" })
public class BaseController {
	protected static final int DEFAULT_PAGE_SIZE = 30;
	
	protected static final String PAGE_SIZE_COOKIE = "PAGE_SIZE";
	
	protected static final int DEFAULT_AGE = 3600 * 24 * 365;
	
	@Autowired
	protected HttpServletRequest request;
	
	public String getRefererUrl() {
		return request.getHeader("referer");
	}
	
	public String getRealIp() {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isBlank(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public String redirect(String url) {
		return new StringBuilder("redirect:").append(url).toString();
	}
	
	protected Integer getAdminUser() {
		Integer uid = AuthUtil.getUser(request);
//		if (uid == null) {
//			throw new IllegalStateException("user should login first, request uri is: " + request.getRequestURI());
//		}
		return uid;
	}
	
	protected String jsonResult(Object resp){
		return JSON.toJson(resp);
	}
	
	protected String jsonResponse(String result, String message){
		AdminApiResponse resp = new AdminApiResponse();
		resp.setResult(result);
		resp.setMessage(new StringBuilder("[")
			.append(result)
			.append("] ")
			.append(message)
			.toString()
		);
		return jsonResult(resp);
	}

	public static String getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies == null || name == null || name.length() == 0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value, int maxAge) {
		if (value == null) {
			value = "";
		}
		String path = "/";
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath(path);
		cookie.setSecure(request.isSecure());
		response.addCookie(cookie);
	}

	/**
	 * @description: 用于生成面包屑导航工具
	 * @param model
	 * @param secondName
	 * @param secondUrl
	 * @param thirdName
	 * @date: 2018/4/10
	 **/
	protected void setBreadCrumb(Model model, String secondName, String secondUrl, String thirdName) {
		if (model == null) {
			return;
		}
		BreadCrumb breadCrumb = new BreadCrumb();
		breadCrumb.setSecondName(secondName);
		breadCrumb.setSecondUrl(secondUrl);
		breadCrumb.setThirdName(thirdName);
		model.addAttribute("breadCrumb", breadCrumb);

	}

	protected String getPageCookie(HttpServletRequest request){
		return getCookie(request, PAGE_SIZE_COOKIE);
	}
	
	protected void setPageCookie(HttpServletRequest request, HttpServletResponse response, String pageSize){
		setCookie(request, response, PAGE_SIZE_COOKIE, pageSize, DEFAULT_AGE);
	}

}