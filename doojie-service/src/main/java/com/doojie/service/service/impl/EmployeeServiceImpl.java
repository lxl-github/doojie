package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.dao.EmployeeMapper;
import com.doojie.domain.po.Employee;
import com.doojie.domain.vo.EmployeeVo;
import com.doojie.service.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Override
	public Employee getEmployeeById(Integer employeeId) {
		return employeeMapper.selectByPrimaryKey(employeeId);
	}

	@Override
	public List<EmployeeVo> getEmployeePageList(Page<Employee> page,String number) {
		List<EmployeeVo> employeeVoList = new ArrayList<EmployeeVo>();
		List<Employee> employeeList = employeeMapper.getEmployeePageList(page,number);
		if(employeeList != null && employeeList.size() > 0){
			for (Employee employee : employeeList) {
				EmployeeVo employeeVo = new EmployeeVo();
				employeeVo.setEmployee(employee);
				employeeVo.setCreateDate(DateUtil.getDatetime(employee.getCreateTime()));
				employeeVo.setModifyDate(DateUtil.getDatetime(employee.getModifyTime()));
				employeeVo.setEntryDate(DateUtil.getDatetime1(employee.getEntryTime()));
				employeeVoList.add(employeeVo);
			}
		}
		page.setResults(employeeList);
		return employeeVoList;
	}

	@Override
	public Boolean saveEmployee(Employee employee) {
		try{
			Integer nextId = employeeMapper.selectEmployeeNextId();
			employee.setNumber(String.valueOf(BaseConstant.EMPLOYEE_COMMON + nextId));
			int count = employeeMapper.insertSelective(employee);
			return count > 0;
		}catch (Exception e) {
			logger.debug("saveEmployee exception : {}",e.getMessage());
		}
		return false;
	}

	@Override
	public Boolean updateEmployee(Employee employee) {
		try{
			int count = employeeMapper.updateByPrimaryKeySelective(employee);
			return count > 0;
		}catch (Exception e) {
			logger.debug("updateEmployee exception : {}",e.getMessage());
		}
		return false;
	}

	@Override
	public List<Employee> getEmployeeByOrderTypeAndRegionIdPageList(
			Page<Employee> page, Integer regionId, Integer orderType) {
		List<Employee> employeeList = employeeMapper.selectEmployeeByOrderTypeAndRegionIdPageList(page, regionId, orderType);
		page.setResults(employeeList);
		return employeeList;
	}

	@Override
	public Employee getEmployeeByNumber(String number) {
		return employeeMapper.selectEmployeeByNumber(number);
	}

}
