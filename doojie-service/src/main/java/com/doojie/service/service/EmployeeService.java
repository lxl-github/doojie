package com.doojie.service.service;

import java.util.List;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Employee;
import com.doojie.domain.vo.EmployeeVo;

public interface EmployeeService {
	
	Employee getEmployeeById(Integer employeeId);

	List<EmployeeVo> getEmployeePageList(Page<Employee> page, String number);
	
	Boolean saveEmployee(Employee employee);
	
	Boolean updateEmployee(Employee employee);
	
	/**
	 * 根据订单类型和区域查找工作状态为开启并负责区域及空闲的员工，进行手工下单 
	 * @param page
	 * @param regionId
	 * @param orderType
	 * @return
	 */
	List<Employee> getEmployeeByOrderTypeAndRegionIdPageList(Page<Employee> page,Integer regionId,Integer orderType);
	
	Employee getEmployeeByNumber(String number);
}
