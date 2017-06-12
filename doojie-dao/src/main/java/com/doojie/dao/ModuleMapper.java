package com.doojie.dao;

import java.util.List;

import com.doojie.domain.po.Module;
import com.doojie.domain.po.Role;

public interface ModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Module record);

    int insertSelective(Module record);

    Module selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Module record);

    int updateByPrimaryKey(Module record);
    
    List<Module> selectModuleListByRoleId(List<Role> list);
}