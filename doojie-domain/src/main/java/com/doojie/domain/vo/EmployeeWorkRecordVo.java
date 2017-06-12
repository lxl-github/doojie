package com.doojie.domain.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.doojie.domain.po.Employee;
import com.doojie.domain.po.EmployeeWorkRecord;
import com.doojie.domain.po.Order;

public class EmployeeWorkRecordVo extends EmployeeWorkRecord{

	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	private String createDate;

	private String endDate;
	
	private String startDate;
	
	private Integer status;
	
	private Order order;
	

	private Employee employee;



	public String getEndDate() {
		Integer endTime = getEndTime();
		if(endTime != null){
			Long time = Long.valueOf(String.valueOf(endTime));
			endDate = simpleDateFormat.format(new Date(time * 1000L));
		}
		return endDate;
	}

	public String getStartDate() {
		Integer startTime = getStartTime();
		if(startTime != null){
			Long time = Long.valueOf(String.valueOf(startTime));
			startDate = simpleDateFormat.format(new Date(time * 1000L));
		}
		
		return startDate;
	}
	
	
	public String getCreateDate() {
		Integer createTime = getCreateTime();
		if(createTime != null){
			Long time = Long.valueOf(String.valueOf(createTime));
			createDate = simpleDateFormat.format(new Date(time * 1000L));
		}
		return createDate;
	}
	
	public Integer getStatus() {
		status = 1;//未处理
		if(getStartTime() != null){//状态为处理中
			status = 2;
		}
		if(getEndTime() != null){//状态为处理完成
			status = 3;
		}
		return status;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
