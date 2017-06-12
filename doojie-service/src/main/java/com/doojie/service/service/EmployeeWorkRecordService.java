package com.doojie.service.service;

import java.util.List;

import com.doojie.common.pagination.Page;
import com.doojie.domain.vo.EmployeeWorkRecordVo;

public interface EmployeeWorkRecordService {

	List<EmployeeWorkRecordVo> getEmployeeWorkRecordVoPageList(Page<EmployeeWorkRecordVo> page, String number);
	
}
