package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

public enum CoinBillType {
	INCOME_LOGIN(0),
	INCOME_ACTIVITY(1),
	OUTCOME(2);

	private static Map<Integer, CoinBillType> valueMap = Maps.newHashMap();
	static {
		for (CoinBillType t : CoinBillType.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private CoinBillType(int value) {
		this.value = value;
	}
	
	public static CoinBillType valueOf(Integer value) {
		if (value == null) {
			return INCOME_LOGIN;
		}
		CoinBillType t = valueMap.get(value);
		return t != null ? t : INCOME_LOGIN;
	}

	public int value() {
		return this.value;
	}
}
