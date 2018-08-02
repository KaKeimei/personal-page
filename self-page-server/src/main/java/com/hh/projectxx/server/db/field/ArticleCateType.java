package com.hh.projectxx.server.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 文章类型
 *
 */
public enum ArticleCateType {
	JAVA("java项目总结", "java"),
	FRONT("前端项目总结", "front"),
	BLOCKCHAIN("区块链与数字货币", "blockchain"),
	;

	private static Map<String, ArticleCateType> valueMap = Maps.newHashMap();
	static {
		for (ArticleCateType t : ArticleCateType.values()) {
			valueMap.put(t.value, t);
		}
	}

	private String desc;

	private String value;

	ArticleCateType(String desc, String value) {
		this.value = value;
		this.desc = desc;
	}

	public static ArticleCateType parseType(String value) {
		return valueMap.get(value);
	}

	public String value() {
		return this.value;
	}

	public String getDesc() {
		return desc;
	}

	public String getValue() {
		return value;
	}
}
