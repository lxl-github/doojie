package com.doojie.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.doojie.domain.po.BaseServer;
import com.doojie.domain.po.Bussiness;

public class BussinessVo implements Serializable{

	/**
	 * 字段说明
	 */
	private static final long serialVersionUID = 8588867344499883389L;

	private Bussiness bussiness;
	
	private String createDate;
	
	private String modifyDate;
	
	private double distance;//距离 * 100整数存储
	
	private List<BaseServer> baseServerList;
	
	/**推荐数**/
	private Integer recommendCount;
	
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public Bussiness getBussiness() {
		return bussiness;
	}

	public void setBussiness(Bussiness bussiness) {
		this.bussiness = bussiness;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public List<BaseServer> getBaseServerList() {
		return baseServerList;
	}

	public void setBaseServerList(List<BaseServer> baseServerList) {
		this.baseServerList = baseServerList;
	}
	
	public Integer getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}
}
