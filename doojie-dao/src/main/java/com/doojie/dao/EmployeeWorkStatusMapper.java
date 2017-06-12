package com.doojie.dao;

import com.doojie.domain.po.EmployeeWorkStatus;

public interface EmployeeWorkStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeWorkStatus record);

    int insertSelective(EmployeeWorkStatus record);

    EmployeeWorkStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmployeeWorkStatus record);

    int updateByPrimaryKey(EmployeeWorkStatus record);
    
    EmployeeWorkStatus selectByEmployeeId(Integer employeeId);
}