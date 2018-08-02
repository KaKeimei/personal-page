package com.hh.projectxx.server.manager.model;

/**
 * 面包屑导航使用
 * Created by zhangli on 2018/4/10
 */
public class BreadCrumb {
	private String secondName;

	private String secondUrl;

	private String thirdName;

	private String thirdUrl;

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondUrl() {
		return secondUrl;
	}

	public void setSecondUrl(String secondUrl) {
		this.secondUrl = secondUrl;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getThirdUrl() {
		return thirdUrl;
	}

	public void setThirdUrl(String thirdUrl) {
		this.thirdUrl = thirdUrl;
	}
}
