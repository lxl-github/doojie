package com.doojie.domain.po;

public class UserProduct {
    private Integer id;

    private String consumeCode;

    private Integer userId;

    private Integer productId;

    private Integer consumeNumber;

    private Integer createTime;

    private Integer expiredTime;

    private String remark;
    
    private Integer status;
    
    private Integer consumeNumberAlready;


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsumeCode() {
        return consumeCode;
    }

    public void setConsumeCode(String consumeCode) {
        this.consumeCode = consumeCode == null ? null : consumeCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getConsumeNumber() {
        return consumeNumber;
    }

    public void setConsumeNumber(Integer consumeNumber) {
        this.consumeNumber = consumeNumber;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getConsumeNumberAlready() {
		return consumeNumberAlready;
	}

	public void setConsumeNumberAlready(Integer consumeNumberAlready) {
		this.consumeNumberAlready = consumeNumberAlready;
	}
}