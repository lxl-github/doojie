package com.doojie.service.service;

import com.doojie.domain.po.EmployeeWorkStatus;

public interface EmployeeWorkStatusService {

	EmployeeWorkStatus getEmployeeWorkStatusByEmployeeId(Integer employeeId);
	
	Boolean saveEmployeeWorkStatus(EmployeeWorkStatus employeeWorkStatus);
	
	Boolean updateEmployeeWorkStatus(EmployeeWorkStatus employeeWorkStatus);
	
}
