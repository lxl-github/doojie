package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.domain.po.Models;

public interface ModelsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Models record);

    int insertSelective(Models record);

    Models selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Models record);

    int updateByPrimaryKey(Models record);
    
    Models selectModelsByName(@Param("name")String name,@Param("brandId")Integer brandId);
    
    List<Models> selectModelsList(Integer brandId);
}