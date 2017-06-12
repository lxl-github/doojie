package com.doojie.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
import com.doojie.dao.EmployeeWorkRecordMapper;
import com.doojie.domain.vo.EmployeeWorkRecordVo;
import com.doojie.service.service.EmployeeWorkRecordService;

@Service
public class EmployeeWorkRecordServiceImpl implements EmployeeWorkRecordService {

	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeWorkRecordServiceImpl.class);
	
	@Autowired
	private EmployeeWorkRecordMapper employeeWorkRecordMapper;
	
	@Override
	public List<EmployeeWorkRecordVo> getEmployeeWorkRecordVoPageList(Page<EmployeeWorkRecordVo> page, String number) {
		List<EmployeeWorkRecordVo> employeeWorkRecordVoList = employeeWorkRecordMapper.selectEmployeeWorkRecordVoPageList(page, number);
		logger.info("employeeWorkRecordVoList number : {}",employeeWorkRecordVoList.size());
		return employeeWorkRecordVoList;
	}

}
