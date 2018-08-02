package com.hh.projectxx.base.util;

import java.nio.ByteBuffer;
import java.util.Random;

public class NumberUtil {

	private static final String ALL_NUMBER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_";
	private static final int RADIX = ALL_NUMBER.length();

	public static String generateMsgID() {
		return generateMsgID(2);
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

	public static long bytesToLong(byte[] bytes) {
		if (bytes.length != 8)
			throw new IllegalArgumentException("Long must be 8 bytes");

		return ByteBuffer.wrap(bytes).getLong();
	}

	public static byte[] longToBytes(long value) {
		return ByteBuffer.allocate(8).putLong(value).array();
	}

	public static int bytesToInt(byte[] bytes) {
		if (bytes.length != 4)
			throw new IllegalArgumentException("Int must be 4 bytes");

		return ByteBuffer.wrap(bytes).getInt();
	}

	public static byte[] intToBytes(int value) {
		return ByteBuffer.allocate(4).putInt(value).array();
	}

	public static long uintToLong(int i) {
		if (i < 0) {
			long mm = (long) Integer.MAX_VALUE + 1;
			return (mm << 1) + i;
		}
		return i;
	}

	public static Short getShort(String s) {
		if (s != null && s.length() > 0) {
			try {
				return Short.parseShort(s.trim());
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	public static Integer getInteger(String s) {
		if (s != null && s.length() > 0) {
			try {
				return Integer.parseInt(s.trim());
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	public static Long getLong(String s) {
		if (s != null && s.length() > 0) {
			try {
				return Long.parseLong(s.trim());
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	public static Double getDouble(String s) {
		if (s != null && s.length() > 0) {
			try {
				return Double.parseDouble(s.trim());
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}
}
