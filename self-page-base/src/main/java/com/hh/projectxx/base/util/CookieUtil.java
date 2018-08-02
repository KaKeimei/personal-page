package com.hh.projectxx.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	private static final String ACT_TOKEN_COOKIE = "ACTSESSID";
	private static final String ACT_UID_COOKIE = "ACTUID";
	private static final String ACT_TS_COOKIE = "ACTTS";
	private static final String ACT_BABYID_COOKIE = "ACTBID";
	
	private static final String VERSION_CODE_COOKIE = "vc";
	private static final String VERSION_NAME_COOKIE = "vn";
	
	public static final String SHARE_OPEN_LIKE_PHOTOID_COOKIE = "SHAREOPENLIKES";
	
	public static final String SHARE_ACT_BABYID_COOKIE = "SHAREACT";
	
	public static final String BLESS_WISH_CARD_ID_COOKIE = "BLESSWISHCARDS";
	

	public static final String COOKIE_USER_ID = "user_id";
	
	public static final String COOKIE_USER_KEY = "user_key_xx";
	
	public static final String COOKIE_EXPIRE_TIME = "user_expires";
	
	public static final int DEFAULT_AGE = 3600 * 24 * 10;  ///cookie十天内有效
	
	private static String PRIVATE_KEY = "7d4eebab7ce33f2c5d6d8c6240cc8fe65ea14cd7";  ///用于加密cookie进行验证
	
	public static String generateUserKey(String password, String expired) {
		return CryptUtil.sha1(password + expired + PRIVATE_KEY);
	}
	
	public static String getActUidCookie(HttpServletRequest request) {
		return getCookie(request, ACT_UID_COOKIE);
	}

	public static void setActUidCookie(HttpServletRequest request,
			HttpServletResponse response, String value) {
		setCookie(request, response, ACT_UID_COOKIE, value, DEFAULT_AGE);
	}

	public static String getActBabyidCookie(HttpServletRequest request) {
		return getCookie(request, ACT_BABYID_COOKIE);
	}

	public static void setActBabyidCookie(HttpServletRequest request,
			HttpServletResponse response, String value) {
		setCookie(request, response, ACT_BABYID_COOKIE, value, DEFAULT_AGE);
	}

	public static String getActTsCookie(HttpServletRequest request) {
		return getCookie(request, ACT_TS_COOKIE);
	}

	public static void setActTsCookie(HttpServletRequest request,
			HttpServletResponse response, String value) {
		setCookie(request, response, ACT_TS_COOKIE, value, DEFAULT_AGE);
	}

	public static String getActTokenCookie(HttpServletRequest request) {
		return getCookie(request, ACT_TOKEN_COOKIE);
	}

	public static void setActTokenCookie(HttpServletRequest request,
			HttpServletResponse response, String value) {
		setCookie(request, response, ACT_TOKEN_COOKIE, value, DEFAULT_AGE);
	}
	
	public static String getOpenLikePhotoIdsCookie(HttpServletRequest request){
		return getCookie(request, SHARE_OPEN_LIKE_PHOTOID_COOKIE);
	}
	
	public static void setOpenLikePhotoIdsCookie(HttpServletRequest request, 
			HttpServletResponse response, String value){
		setCookie(request, response, SHARE_OPEN_LIKE_PHOTOID_COOKIE, value, DEFAULT_AGE);
	}

	public static String getBlessWishCardIdsCookie(HttpServletRequest request){
		return getCookie(request, BLESS_WISH_CARD_ID_COOKIE);
	}
	
	public static void setBlessWishCardIdsCookie(HttpServletRequest request, 
			HttpServletResponse response, String value){
		setCookie(request, response, BLESS_WISH_CARD_ID_COOKIE, value, DEFAULT_AGE);
	}
	
	public static String getActShareCookie(HttpServletRequest request){
		return getCookie(request, SHARE_ACT_BABYID_COOKIE);
	}
	
	public static void setActShareCookie(HttpServletRequest request, 
			HttpServletResponse response, String value){
		setCookie(request, response, SHARE_ACT_BABYID_COOKIE, value, DEFAULT_AGE);
	}
	
	public static String getVersionCodeCookie(HttpServletRequest request){
		return getCookie(request, VERSION_CODE_COOKIE);
	}
	
	public static void setVersionCodeCookie(HttpServletRequest request, 
			HttpServletResponse response, String value){
		setCookie(request, response, VERSION_CODE_COOKIE, value, DEFAULT_AGE);
	}
	
	public static String getVersionNameCookie(HttpServletRequest request){
		return getCookie(request, VERSION_NAME_COOKIE);
	}
	
	public static void setVersionNameCookie(HttpServletRequest request, 
			HttpServletResponse response, String value){
		setCookie(request, response, VERSION_NAME_COOKIE, value, DEFAULT_AGE);
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
}
