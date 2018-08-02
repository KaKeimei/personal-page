package com.hh.projectxx.base.db.entity;

import java.util.Date;

public class Novel {
	
	private Integer id;
	
	private String title;
	
	private String source;
	
	private Date websiteTime;
	
	private String content;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private String identifyId;
	
	private Integer topId; //在详情页判断是否为最大id，用于判断是否显示前一页
	
	private Integer status;
	
	private Integer type;

	public String getIdentifyId() {
		return identifyId;
	}

	public void setIdentifyId(String identifyId) {
		this.identifyId = identifyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getWebsiteTime() {
		return websiteTime;
	}

	public void setWebsiteTime(Date websiteTime) {
		this.websiteTime = websiteTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTopId() {
		return topId;
	}

	public void setTopId(Integer topId) {
		this.topId = topId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	

}
