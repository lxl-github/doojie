package com.doojie.domain.vo;

/**
 * 下单数据中转对象
 * @author lxl
 *
 */
public class AppointmentVo {
	
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * 商家id
	 */
	private Integer bussinessId;
	
	/**
	 * 服务类型 1：洗车 2:维修 3:保养
	 */
	private Integer orderType;
	
	/**
	 * 车牌简称
	 */
	private String plate;
	
	/**
	 * 车牌区域码
	 */
	private String letter;
	
	/**
	 * 车牌号
	 */
	private String number;
	
	/**
	 * 预约日期
	 */
	private String appiontmentDate;
	
	/**
	 * 预约时段
	 */
	private String time;
	
	/**
	 * 结算时都捷卡id
	 */
	private String cards;
	
	/**
	 * 位置详情
	 */
	private String address;
	
	/**
	 * 位置城市id
	 */
	private Integer city;
	
	/**
	 * 位置区县id
	 */
	private Integer district;
	
	/**
	 * 位置商圈id
	 */
	private Integer bussinessDistrict;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getBussinessId() {
		return bussinessId;
	}

	public void setBussinessId(Integer bussinessId) {
		this.bussinessId = bussinessId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAppiontmentDate() {
		return appiontmentDate;
	}

	public void setAppiontmentDate(String appiontmentDate) {
		this.appiontmentDate = appiontmentDate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	
	
}
