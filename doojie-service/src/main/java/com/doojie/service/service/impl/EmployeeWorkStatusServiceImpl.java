package com.doojie.service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.dao.EmployeeWorkStatusMapper;
import com.doojie.domain.po.EmployeeWorkStatus;
import com.doojie.service.service.EmployeeWorkStatusService;

@Service
public class EmployeeWorkStatusServiceImpl implements EmployeeWorkStatusService {

	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeWorkStatusServiceImpl.class);
	
	@Autowired
	private EmployeeWorkStatusMapper employeeWorkStatusMapper;

	@Override
	public EmployeeWorkStatus getEmployeeWorkStatusByEmployeeId(Integer employeeId) {
		return employeeWorkStatusMapper.selectByEmployeeId(employeeId);
	}

	@Override
	public Boolean saveEmployeeWorkStatus(EmployeeWorkStatus employeeWorkStatus) {
		try{
			int count = employeeWorkStatusMapper.insertSelective(employeeWorkStatus);
			if(count > 0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("saveEmployeeWorkStatus exception:{}",e.getMessage());
		}
		return false;
	}

	@Override
	public Boolean updateEmployeeWorkStatus(EmployeeWorkStatus employeeWorkStatus) {
		try{
			int count = employeeWorkStatusMapper.updateByPrimaryKeySelective(employeeWorkStatus);
			if(count > 0){
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("updateEmployeeWorkStatus exception:{}",e.getMessage());
		}
		return false;
	}
	

}
