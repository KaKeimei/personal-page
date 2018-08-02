package com.hh.projectxx.base.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	static int bigCharLength = 3;

	public static String eliminateSub(String origin, String delim, String sub) {
		StringBuilder sb = new StringBuilder();
		StringTokenizer token = new StringTokenizer(origin, delim);
		boolean needDelim = false;
		while (token.hasMoreElements()) {
			String thisToken = token.nextToken();
			if (!thisToken.equals(sub)) {
				if (needDelim) {
					sb.append(delim);
				} else {
					needDelim = true;
				}
				sb.append(thisToken);
			}
		}
		return sb.toString();
	}

	public static boolean hasSubTerm(String origin, String delim, String sub) {
		if (origin != null && origin.length() > 0) {
			StringTokenizer token = new StringTokenizer(origin, delim);
			while (token.hasMoreElements()) {
				if (sub.equals(token.nextToken()))
					return true;
			}
		}
		return false;
	}

	// for short string use..
	public static int charLength(char c) {
		return (c >= 32 && c <= 126) ? 1 : bigCharLength;
	}

	public static int strLength(String s) {
		if (s == null || s.length() == 0)
			return 0;
		char[] cbuf = s.toCharArray();
		int length = 0;
		for (char c : cbuf) {
			length += charLength(c);
		}
		return length;
	}

	public static boolean isEmpty(String s) {
		return (s == null || s.length() == 0);
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	public static String shortString(String s, int length, int dotCount) {
		length = length * bigCharLength;
		int tmpLength = 0;
		int i = 0;
		char[] cbuf = s.toCharArray();
		for (i = 0; i < cbuf.length; i++) {
			tmpLength += charLength(cbuf[i]);
			if (tmpLength > length)
				break;
		}
		if (tmpLength <= length)
			return s;

		for (;; i--) {
			if (tmpLength <= length - dotCount)
				break;
			tmpLength -= charLength(cbuf[i]);
		}
		char[] cresult = Arrays.copyOf(cbuf, i + 1);
		return new String(cresult) + "...";
	}

	public static byte[] stringToBytes(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return new byte[0];
		}
	}

	public static String byteToString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 按csv字段格式进行转义，步骤：<br>
	 * 1、将双引号 <code>"</code> 全部替换为两个双引号 <code>""</code> <br>
	 * 2、在字符串前加上一个双引号和制表符 <code>"\t</code>，末尾加上一个双引号 <code>"</code>。
	 * 
	 * @param s
	 * @return
	 */
	public static String csvEscaper(String s) {
		if (s == null || s.length() == 0)
			return "";
		String tmp = s.replaceAll("\"", "\"\"");
		tmp = "\"\t" + tmp + "\"";
		return tmp;
	}

	/**
	 * 按xml字段格式进行转义，步骤：<br>
	 * 1、将 <code>&</code> 全部替换为 <code>&amp;</code> <br>
	 * 2、将 <code>&lt;</code> 全部替换为 <code>&amp;lt;</code> ，将 <code>&gt;</code> 全部替换为 <code>&amp;gt;</code>
	 * 
	 * @param field
	 * @return
	 */
	public static String xmlFieldEscape(String field) {
		if (field == null || field.length() == 0) {
			return "";
		}
		String tmp = field.replaceAll("&", "&amp;");
		tmp = tmp.replaceAll("<", "&lt;");
		tmp = tmp.replaceAll(">", "&gt;");
		return tmp;
	}

	public static String htmlSimpleEscape(String field) {
		if (field == null || field.length() == 0) {
			return "";
		}
		String tmp = field.replaceAll("'", "");
		tmp = tmp.replaceAll("\"", "");
		tmp = tmp.replaceAll("\n", "");
		tmp = tmp.replaceAll("<", "");
		tmp = tmp.replaceAll(">", "");
		return tmp;
	}

	public static String htmlTagFilter(String field) {
		if (field == null || field.length() == 0) {
			return "";
		}
		String tmp = field.replaceAll("'", "");
		tmp = tmp.replaceAll("\"", "");
		tmp = tmp.replaceAll("\n", "");
		tmp = tmp.replaceAll("<(.*?)>", "");
		return tmp;
	}
	
	static HashMap<Integer, String> chineseNums = new HashMap<Integer, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(1, "一");
			put(2, "二");
			put(3, "三");
			put(4, "四");
			put(5, "五");
			put(6, "六");
			put(7, "七");
			put(8, "八");
			put(9, "九");
			put(10, "十");
		}
	};
	
	public static String getChineseNum(int num) {
		return chineseNums.get(num);
	}
	
	public static String encrptyPassport(String passport) {
		if (passport == null) return null;
		if (passport.length() <= 4) return "****";
		int len = (passport.length() - 4) / 2;
		String result = passport.substring(0, len) + "****" + passport.substring(len + 4);
		return result;
	}
	
	public static String encryptPhone(String phone) {
		if (phone == null) return null;
		if (phone.length() <= 3) return "***";
		char[] c = phone.toCharArray();
		for (int i = 0; i < phone.length() - 3; i ++) {
			if (i >= 4)
				break;
			c[i+3] = '*';
		}
		return new String(c);		
	}
	
	/**
	 * 获得字符串中开始和结束字符串中间得值
	 * @param str 字符串
	 * @param s 开始
	 * @param e 结束
	 * @param azimuth 匹配内容是否包含开始与结束符号，0：不包含  1：包含
	 * @return
	 */
	public static String getValueBetween(String str, String s, String e,int azimuth){
        Pattern pattern;
        if (azimuth==1){
            pattern = Pattern.compile("(?=(" + s + "))[.\\s\\S]*?(?<=(" + e + "))");
        }else{
            pattern = Pattern.compile("(?<=(" + s + "))[.\\s\\S]*?(?=(" + e + "))");
        }
        Matcher matcher = pattern.matcher(str);
        //Vector<String> vector=new Vector<String>();
        String resultString = "";
        while(matcher.find()){             
            //vector.add(matcher.group());
        	resultString += matcher.group();
        }
       // return vector;
        return resultString;
    }

}
