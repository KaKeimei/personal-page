package com.hh.projectxx.base.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法
 */
public class CryptUtil {
	private static Logger logger = LoggerFactory.getLogger(CryptUtil.class);

	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String digest(String s, String algorithm) {
		if (s == null || s.length() <= 0)
			return s;

		try {
			byte[] strTemp = s.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance(algorithm);
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String sha1(String s) {
		return digest(s, "SHA1");
	}

	public static String md5(String s) {
		return digest(s, "MD5");
	}

	/**
	 * 计算文件MD5
	 * 
	 * @param f
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static String md5(File f) {
		FileInputStream fis = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			fis = new FileInputStream(f);
			IOUtils.copy(fis, bout);

			return md5(bout.toByteArray());
		} catch (IOException e) {
			logger.error("md5 error!", e);
			return null;
		} finally {
			IOUtils.closeQuietly(bout);
			IOUtils.closeQuietly(fis);
		}
	}

	public static String md5(byte[] content) {
		if (content == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(content, 0, content.length);
			byte[] bs = md.digest();
			int length = bs.length;
			char[] cs = new char[length * 2];
			int k = 0;
			for (int i = 0; i < length; i++) {
				byte byte0 = bs[i];
				cs[k++] = hexDigits[byte0 >>> 4 & 0xf];
				cs[k++] = hexDigits[byte0 & 0xf];
			}

			return new String(cs);
		} catch (NoSuchAlgorithmException e) {
			logger.error("md5 error!", e);
			return null;
		}
	}
	
	public static String md5AndCompress(String src) {
		String randomSalt = String.valueOf(Math.random());
		String s = CryptUtil.md5(src + randomSalt);
		String s1 = s.substring(1,16);
		String s2 = s.substring(17);
		try {
			String compress1 = NumberUtil.compressNum(Long.parseLong(s1, 16));
			String compress2 = NumberUtil.compressNum(Long.parseLong(s2, 16));
			return new StringBuilder(compress1).append(compress2).toString();
		} catch (NumberFormatException e) {
			logger.warn("fail to compress md5 src", e);
			return s;
		}
	}
}
