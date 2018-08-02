package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

public enum PhotoSeriesType {
	UNDEFINED(0),
	QINGCHUN(1),
	MEITUI(2),
	YAZHOU(3),
	OUMEI(4),
	ZIPAITOUPAI(5),
	DONGMAN(6),
	LINGLEI(7),
	ZHIMINGSHIJIAN(8); 

	private static Map<Integer, PhotoSeriesType> valueMap = Maps.newHashMap();
	static {
		for (PhotoSeriesType t : PhotoSeriesType.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private PhotoSeriesType(int value) {
		this.value = value;
	}
	
	public static PhotoSeriesType valueOf(Integer value) {
		if (value == null) {
			return UNDEFINED;
		}
		PhotoSeriesType t = valueMap.get(value);
		return t != null ? t : UNDEFINED;
	}

	public int value() {
		return this.value;
	}
}
