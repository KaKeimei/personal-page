package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

public enum UserStatus {
	NORMAL(0),
	DELETED(3),
	VIP(99) ;

	private static Map<Integer, UserStatus> valueMap = Maps.newHashMap();
	static {
		for (UserStatus t : UserStatus.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private UserStatus(int value) {
		this.value = value;
	}
	
	public static UserStatus valueOf(Integer value) {
		if (value == null) {
			return NORMAL;
		}
		UserStatus t = valueMap.get(value);
		return t != null ? t : NORMAL;
	}

	public int value() {
		return this.value;
	}
}
