package com.hh.projectxx.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthUtil {
	
	public static final String USER_KEY = "xx_user";
	
	public static final String USER_PERMISSION = "user_permission";
	
	public static final String USER_CAPTCHA = "captcha";
	
	
//	private static Logger logger = LoggerFactory.getLogger(AuthUtil.class);
	
	public static void adminLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_KEY);
    }
	public static Integer getUser(HttpServletRequest request) {
		Integer uid = (Integer) request.getSession().getAttribute(USER_KEY);
		return uid;
	}

}
