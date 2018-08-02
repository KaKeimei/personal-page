package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 花费coin的类型，动图小说还是套图等
 *
 */
public enum CoinConsumeType {
	NOVEL(0),
	EVILGIF(1),
	PHOTO(2),
	OTHER(99); 

	private static Map<Integer, CoinConsumeType> valueMap = Maps.newHashMap();
	static {
		for (CoinConsumeType t : CoinConsumeType.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private CoinConsumeType(int value) {
		this.value = value;
	}
	
	public static CoinConsumeType valueOf(Integer value) {
		if (value == null) {
			return OTHER;
		}
		CoinConsumeType t = valueMap.get(value);
		return t != null ? t : OTHER;
	}

	public int value() {
		return this.value;
	}
}
