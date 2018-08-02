package com.hh.projectxx.base.util;

import java.util.Random;

public class TokenUtil {
	private static final String ALL_NUMBER = "\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz{|}~";
	private static final int RADIX = ALL_NUMBER.length();

	public static String generateMsgID() {
		return generateMsgID(3);
	}
	
	public static String generateMsgID(int randomCount) {
		String result = compressNum(System.currentTimeMillis());
		Random r = new Random();
		StringBuilder buf = new StringBuilder(result);
		for (int i = 0; i < randomCount; i++) {
			buf.append(ALL_NUMBER.charAt(r.nextInt(RADIX)));
		}
		return buf.toString();
	}

	public static String compressNum(long m) {
		String result = "";
		while (m >= RADIX) {
			int n = (int) (m % RADIX);
			m = m / RADIX;
			result = ALL_NUMBER.charAt(n) + result;
		}
		result = ALL_NUMBER.charAt((int) m) + result;
		return result;
	}
	
}
