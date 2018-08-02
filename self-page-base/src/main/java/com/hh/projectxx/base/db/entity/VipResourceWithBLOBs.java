package com.hh.projectxx.base.db.entity;

public class VipResourceWithBLOBs extends VipResource {
    private String data;

    private String summary;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}