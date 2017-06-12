package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.domain.po.Permission;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
    
    List<Permission> selectPermissionByModuleId(@Param("moduleId")Integer moduleId,@Param("level")Integer level);
    
    List<Permission> selectPermissionListByEmployeeId(@Param("employeeId")Integer employeeId,@Param("level")Integer level);
}