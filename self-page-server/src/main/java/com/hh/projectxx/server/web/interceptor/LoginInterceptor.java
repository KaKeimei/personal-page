package com.hh.projectxx.server.web.interceptor;

import com.hh.projectxx.base.db.entity.User;
import com.hh.projectxx.base.manager.UserManager;
import com.hh.projectxx.base.util.AuthUtil;
import com.hh.projectxx.server.db.field.ArticleCateType;
import com.hh.projectxx.server.manager.LoginManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆拦截器
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	@Resource
	private LoginManager loginManager;
	@Resource
	private UserManager userManager;

	
	@Override  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		request.getSession().getAttribute(AuthUtil.USER_CAPTCHA);   ///此处为了强制生成session以免验证码首次验证失败
		ArticleCateType[] types = ArticleCateType.values();
		request.setAttribute("articleTypes", types);
		if(uri.matches("/web/index")  
								|| uri.matches("/web/login") 
								|| uri.matches("/web/logout")  
								|| uri.matches("/web/captcha")  
								|| uri.matches("/web/signup")
								|| uri.matches("/web/article!.*?")
								){
			Integer uid = AuthUtil.getUser(request);
			if (uid != null) {
				User user = userManager.getUserById(uid);  //注入用户信息到首页
				request.setAttribute("adminUser", user);

			}else {
				loginManager.checkLoginByCookie(request);
			}
			return true;
		}
		
		//判断是否登陆了
		Integer uid = AuthUtil.getUser(request);
		if(uid == null) {
			if (loginManager.checkLoginByCookie(request)) {
				return true;
			}
			return needLogin(response, uri);
		} else {  ///将用户信息注入到首页中
			User user = userManager.getUserById(uid);
			request.setAttribute("adminUser", user);
			return true;
		}
	}
	
	private boolean needLogin(HttpServletResponse response, String ref) throws IOException {
		response.sendRedirect("/web/login?ref=" + ref);
		return false;
	}
}
