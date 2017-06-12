package com.doojie.domain.vo;

import java.util.List;

import com.doojie.domain.po.Brand;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.po.Models;
import com.doojie.domain.po.Order;
import com.doojie.domain.po.OrderTrack;
import com.doojie.domain.po.Recommend;
import com.doojie.domain.po.Region;
import com.doojie.domain.po.User;

public class OrderDetailVo extends Order{

	/**
	 * 用户对象
	 */
	private User user;
	
	private EmployeeWorkRecordVo employeeWorkRecordVo;
	

	private Brand brand;
	
	private Models models;
	
	private Region regionCity;
	private Region regionDistrict;
	private Region regionBussinessDistrict;
	private Bussiness bussiness;
	private Recommend  recommend;

	
	/**
	 * 订单跟踪记录
	 */
	private List<OrderTrack> orderTrackList;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public EmployeeWorkRecordVo getEmployeeWorkRecordVo() {
		return employeeWorkRecordVo;
	}

	public void setEmployeeWorkRecordVo(EmployeeWorkRecordVo employeeWorkRecordVo) {
		this.employeeWorkRecordVo = employeeWorkRecordVo;
	}

	public List<OrderTrack> getOrderTrackList() {
		return orderTrackList;
	}

	public void setOrderTrackList(List<OrderTrack> orderTrackList) {
		this.orderTrackList = orderTrackList;
	}
	
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Models getModels() {
		return models;
	}

	public void setModels(Models models) {
		this.models = models;
	}

	public Region getRegionCity() {
		return regionCity;
	}

	public void setRegionCity(Region regionCity) {
		this.regionCity = regionCity;
	}

	public Region getRegionDistrict() {
		return regionDistrict;
	}

	public void setRegionDistrict(Region regionDistrict) {
		this.regionDistrict = regionDistrict;
	}

	public Region getRegionBussinessDistrict() {
		return regionBussinessDistrict;
	}

	public void setRegionBussinessDistrict(Region regionBussinessDistrict) {
		this.regionBussinessDistrict = regionBussinessDistrict;
	}
	
	public Bussiness getBussiness() {
		return bussiness;
	}

	public void setBussiness(Bussiness bussiness) {
		this.bussiness = bussiness;
	}
	
	public Recommend getRecommend() {
		return recommend;
	}

	public void setRecommend(Recommend recommend) {
		this.recommend = recommend;
	}
}
