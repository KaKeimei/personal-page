package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by zhangli on 2018/4/12
 */
public enum BaseStatus {
	NORMAL(0),
	DELETED(3);

	private static Map<Integer, BaseStatus> valueMap = Maps.newHashMap();
	static {
		for (BaseStatus t : BaseStatus.values()) {
			valueMap.put(t.value, t);
		}
	}

	private int value = 0;

	BaseStatus(int value) {
		this.value = value;
	}

	public static BaseStatus valueOf(Integer value) {
		if (value == null) {
			return NORMAL;
		}
		BaseStatus t = valueMap.get(value);
		return t != null ? t : NORMAL;
	}

	public int value() {
		return this.value;
	}
}
