package com.doojie.dao;

import java.util.List;

import com.doojie.domain.po.Brand;

public interface BrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);
    
    Brand selectBrandByBrandName(String brandName);
    
    List<Brand> selectBrandList();
}