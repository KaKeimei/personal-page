package com.hh.projectxx.base.util;

import org.apache.commons.lang3.StringUtils;

public class CheckInputUtil {
	
	private static final int MAX_PASSPORT_LENGTH = 20;
	
	private static final int MIN_PASSPROT_LENGTH = 3;
	
	private static final int MAX_USER_NAME_LENGTH = 20;
	
	private static final int MIN_USER_NAME_LENGTH = 1;
	
	private static final int MAX_PWD_LENGTH = 18;
	
	private static final int MIN_PWD_LENGTH = 5;
	
	public static String checkPassport(String passport){
		if(StringUtils.isBlank(passport)){
			return null;
		}
		if(!StringUtils.isAlphanumeric(passport)){
			return null;
		}
		if(passport.length() > MAX_PASSPORT_LENGTH || passport.length() < MIN_PASSPROT_LENGTH){
			return null;
		}
		if(passport.indexOf(" ") != -1){
			return null;
		}
		return passport.trim();
	}
	
	public static String checkName(String name){
		if(StringUtils.isBlank(name)){
			return null;
		}
		if(name.length() > MAX_USER_NAME_LENGTH || name.length() < MIN_USER_NAME_LENGTH){
			return null;
		}
		return name.trim();
	}
	
	public static String checkPassword(String pwd){
		if(StringUtils.isBlank(pwd)){
			return null;
		}
		if(!StringUtils.isAlphanumeric(pwd)){
			return null;
		}
		if(pwd.length() > MAX_PWD_LENGTH || pwd.length() < MIN_PWD_LENGTH){
			return null;
		}
		if(pwd.indexOf(" ") != -1){
			return null;
		}
		return pwd.trim();
	}
	
}
