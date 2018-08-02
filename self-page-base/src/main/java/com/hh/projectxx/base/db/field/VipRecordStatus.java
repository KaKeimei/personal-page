package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

public enum VipRecordStatus {
	NORMAL(0),
	DELETED(3),
	EXPIRED(2) ;

	private static Map<Integer, VipRecordStatus> valueMap = Maps.newHashMap();
	static {
		for (VipRecordStatus t : VipRecordStatus.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private VipRecordStatus(int value) {
		this.value = value;
	}
	
	public static VipRecordStatus valueOf(Integer value) {
		if (value == null) {
			return NORMAL;
		}
		VipRecordStatus t = valueMap.get(value);
		return t != null ? t : NORMAL;
	}

	public int value() {
		return this.value;
	}
}
