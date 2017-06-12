package com.doojie.domain.po;

public class BaseServer {

	private Integer id;
	
	private Integer carType;
	
	private Integer price;
	
	private Integer vipPrice;
	
	private Integer isShow;
	
	private Integer bussinessId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Integer vipPrice) {
		this.vipPrice = vipPrice;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	public Integer getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId) {
		this.bussinessId = bussinessId;
	}
}
