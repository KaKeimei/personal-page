package com.hh.projectxx.server.web.interceptor;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hh.projectxx.base.util.AuthUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * 权限拦截器
 * @author yankai
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter  {
	
	@SuppressWarnings("unchecked")
	@Override  
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		
		//登入登出不判断
		if(uri.equals("/admin_bbc/no_auth") || uri.matches("/admin_bbc/login") || uri.matches("/admin_bbc/logout")) {
			return true;
		}
		
		//判断是否有权限
		HashSet<String> permissionSet = (HashSet<String>) request.getSession().getAttribute(AuthUtil.USER_PERMISSION);
		if(permissionSet!=null) {
			//method check
			if(permissionSet.contains(uri)) {
				return true;
			}
			
			//action check
			int endIdx = uri.indexOf("!");
			if(endIdx!=-1) {
				String actionUri = uri.substring(0, endIdx);
				if(permissionSet.contains(actionUri)) {
					return true;
				}
			}
			
			//domain check
			endIdx = uri.lastIndexOf("/");
			if(endIdx > 0) {
				String domainUri = uri.substring(0, endIdx);
				if(permissionSet.contains(domainUri)) {
					return true;
				}
			}
		}
		return noAuth(response, uri);
	}  
	
	private boolean noAuth(HttpServletResponse response, String ref) throws IOException {
		response.sendRedirect("/admin_bbc/no_auth?ref=" + ref);
		return false;
	}
}
