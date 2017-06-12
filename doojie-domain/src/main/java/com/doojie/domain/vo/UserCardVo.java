package com.doojie.domain.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.doojie.domain.po.TradeRecord;

public class UserCardVo {

	static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Integer id;
	
	private Integer userId;
	
//	private String consumeCode;
	
	private Integer consumeNumber;
	
	private String productName;
	
	private Integer productType;
	
	private Integer productIsDoor;
	
	private Integer createTime;
	
	private Integer expiredTime;

	private String createDate;
	
	private String expiredDate;

	private Integer isExpired;

	private Integer status;
	    
	private Integer consumeNumberAlready;
	
	private Integer number;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

//	public String getConsumeCode() {
//		return consumeCode;
//	}
//
//	public void setConsumeCode(String consumeCode) {
//		this.consumeCode = consumeCode;
//	}

	public Integer getConsumeNumber() {
		return consumeNumber;
	}

	public void setConsumeNumber(Integer consumeNumber) {
		this.consumeNumber = consumeNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
	public Integer getProductIsDoor() {
		return productIsDoor;
	}

	public void setProductIsDoor(Integer productIsDoor) {
		this.productIsDoor = productIsDoor;
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
	
	public String getCreateDate() {
		Integer createTime = getCreateTime();
		if(createTime != null){
			Long time = Long.valueOf(String.valueOf(createTime));
			createDate = simpleDateFormat1.format(new Date(time * 1000L));
		}
		return createDate;
	}

	public String getExpiredDate() {
		Integer expiredTime = getExpiredTime();
		if(expiredTime != null){
			Long time = Long.valueOf(String.valueOf(expiredTime));
			expiredDate = simpleDateFormat2.format(new Date(time * 1000L));
		}
		return expiredDate;
	}
	
	public Integer getIsExpired() {
		Long currentTime = Long.valueOf(String.valueOf(System.currentTimeMillis()).substring(0, 10));
		if(currentTime > getExpiredTime()){
			isExpired = 1;
		}else{
			isExpired = 0;
		}
		return isExpired;
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
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
