package com.hh.projectxx.server.manager.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * transaction type
 *
 */
public enum TransactionType {
	INCOME("收入", "income"),
	OUTCOME("支出", "outcome"),
	;

	private static Map<String, TransactionType> valueMap = Maps.newHashMap();
	static {
		for (TransactionType t : TransactionType.values()) {
			valueMap.put(t.value, t);
		}
	}

	private String desc;

	private String value;

	TransactionType(String desc, String value) {
		this.value = value;
		this.desc = desc;
	}

	public static TransactionType parseType(String value) {
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
