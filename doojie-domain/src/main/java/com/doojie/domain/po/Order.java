package com.doojie.domain.po;

public class Order {
    private Long id;

    private String orderSn;

    private Integer status;

    private Integer price;

    private Integer userId;

    private Integer bussinessId;

    private Integer createTime;

    private Integer modifyTime;

    private String remark;

    private Integer orderType;

    private String appointmentTime;

    private Integer brandId;

    private Integer modelId;

    private String color;

    private String plateNumber;

    private String consumeCode;

    private Integer orderOwn;

    private Integer auditStatus;

    private String auditRefusalReason;

    private Integer city;

    private Integer district;

    private Integer bussinessDistrict;

    private String regionDetail;
    
    private String mobileNumber;
   
    private String createDate;
	
    private String isRecommend;
    
    private Integer userProductId;

	public Integer getUserProductId() {
		return userProductId;
	}

	public void setUserProductId(Integer userProductId) {
		this.userProductId = userProductId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Integer bussinessId) {
        this.bussinessId = bussinessId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime == null ? null : appointmentTime.trim();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public String getConsumeCode() {
        return consumeCode;
    }

    public void setConsumeCode(String consumeCode) {
        this.consumeCode = consumeCode == null ? null : consumeCode.trim();
    }

    public Integer getOrderOwn() {
        return orderOwn;
    }

    public void setOrderOwn(Integer orderOwn) {
        this.orderOwn = orderOwn;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditRefusalReason() {
        return auditRefusalReason;
    }

    public void setAuditRefusalReason(String auditRefusalReason) {
        this.auditRefusalReason = auditRefusalReason == null ? null : auditRefusalReason.trim();
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public Integer getBussinessDistrict() {
        return bussinessDistrict;
    }

    public void setBussinessDistrict(Integer bussinessDistrict) {
        this.bussinessDistrict = bussinessDistrict;
    }

    public String getRegionDetail() {
        return regionDetail;
    }

    public void setRegionDetail(String regionDetail) {
        this.regionDetail = regionDetail == null ? null : regionDetail.trim();
    }
    
    public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
    
    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}
}