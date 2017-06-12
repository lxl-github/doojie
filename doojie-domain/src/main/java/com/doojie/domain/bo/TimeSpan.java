package com.doojie.domain.bo;

public class TimeSpan{
	private String time;//时段
	private Integer isShow = 0;//是否可以选 0:是 1:否
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	
}
