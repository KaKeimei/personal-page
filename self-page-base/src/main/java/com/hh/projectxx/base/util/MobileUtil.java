package com.hh.projectxx.base.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class MobileUtil {
	
	public static final String CHINA = "+86";
	
	public static String getFullMobile(String country, String mobile) {
		if (StringUtils.isBlank(country) || country.equals(CHINA)) {
			return mobile;
		}
		return new StringBuilder(country).append(mobile).toString();
	}
	
	public static boolean checkCountryEqual(String country1, String country2) {
		if (StringUtils.isBlank(country1)) {
			country1 = CHINA;
		}
		if (StringUtils.isBlank(country2)) {
			country2 = CHINA;
		}
		return country1.equals(country2);
	}
	
	public static boolean checkMobileValid(String country, String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return false;
		}
		if (isMobileForeign(country)) {
			return Pattern.matches("^[0-9]+$", mobile);
		}
		return Pattern.matches("^(1)[0-9]{10}$", mobile);
	}
	
	public static void main(String[] args) {
		System.out.println(checkMobileValid("+86", "18668136052"));
	}
	
	public static boolean isMobileForeign(String country) {
		return (StringUtils.isNotBlank(country) && !country.equals(CHINA));
	}

}
