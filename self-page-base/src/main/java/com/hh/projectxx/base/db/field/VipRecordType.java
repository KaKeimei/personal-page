package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

public enum VipRecordType {
	DAILY(1),  ///日会员体验
	MONTHLY(30),   ///月度会员
	SEASONLY(90),  ////季度会员
	ANNUALLY(365),  ///年度会员
	OTHER(0);   ///其他情形

	private static Map<Integer, VipRecordType> valueMap = Maps.newHashMap();
	static {
		for (VipRecordType t : VipRecordType.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private VipRecordType(int value) {
		this.value = value;
	}
	
	public static VipRecordType valueOf(Integer value) {
		if (value == null) {
			return DAILY;
		}
		VipRecordType t = valueMap.get(value);
		return t != null ? t : DAILY;
	}

	public int value() {
		return this.value;
	}
}
