package com.doojie.dao;

import com.doojie.domain.po.RoleModule;

public interface RoleModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleModule record);

    int insertSelective(RoleModule record);

    RoleModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleModule record);

    int updateByPrimaryKey(RoleModule record);
}