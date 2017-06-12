package com.doojie.dao;

import com.doojie.domain.po.EmployeeRole;

public interface EmployeeRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeRole record);

    int insertSelective(EmployeeRole record);

    EmployeeRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmployeeRole record);

    int updateByPrimaryKey(EmployeeRole record);
    
}