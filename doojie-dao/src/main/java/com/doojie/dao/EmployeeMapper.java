package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    List<Employee> getEmployeePageList(Page<Employee> page,@Param("number")String number);
    
    List<Employee> selectEmployeeByOrderTypeAndRegionIdPageList(Page<Employee> page,@Param("regionId")Integer regionId,@Param("orderType")Integer orderType);
    
    Integer selectEmployeeNextId();
    
    Employee selectEmployeeByNumber(String number);
}