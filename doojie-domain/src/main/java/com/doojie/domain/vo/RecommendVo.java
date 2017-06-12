package com.doojie.domain.vo;

public class RecommendVo {

	private Integer id;
	
	private Integer bussinessId;
	
	private String bName;
	
	private Integer userId;
	
	private String mobile;
	
	private String reasons;
	
	private String createDate;
	
	private String modifyDate;
	
	private Integer recommendIndex;
	
	private Integer isTop;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId) {
		this.bussinessId = bussinessId;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

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

	public Integer getRecommendIndex() {
		return recommendIndex;
	}

	public void setRecommendIndex(Integer recommendIndex) {
		this.recommendIndex = recommendIndex;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
}
