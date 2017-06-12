package com.doojie.dao;

import com.doojie.domain.po.ModulePermission;

public interface ModulePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ModulePermission record);

    int insertSelective(ModulePermission record);

    ModulePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModulePermission record);

    int updateByPrimaryKey(ModulePermission record);
}