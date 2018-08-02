package com.hh.projectxx.base.db.field;

import com.google.common.collect.Maps;

import java.util.Map;

public enum CrawlStatus {

	NORMAL(0), // 正常状态，从列表获得还未开始爬行的
	CRAWLING(1), //正在爬行的，防止其他线程重复调用
	SUCCESS(2), //爬行成功的标志
	DELETED(3), // 删除的文章
	UNSENT(4),  //爬行成功但是发送失败的
	FAILED(99); //爬行失败的，可能是网络问题，程序崩溃，解析失败等原因
	
	private static Map<Integer, CrawlStatus> valueMap = Maps.newHashMap();
	static {
		for (CrawlStatus t : CrawlStatus.values()) {
			valueMap.put(t.value, t);
		}
	}
	
	private int value = 0;

	private CrawlStatus(int value) {
		this.value = value;
	}
	
	public static CrawlStatus valueOf(Integer value) {
		if (value == null) {
			return NORMAL;
		}
		CrawlStatus t = valueMap.get(value);
		return t != null ? t : NORMAL;
	}

	public int value() {
		return this.value;
	}
}
