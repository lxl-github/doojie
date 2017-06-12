package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    Product selectProductByName(String name);
    
    Product selectProductByProductCode(String productCode);
    
    int updateProductIsShow(@Param("isShow")Integer isShow,@Param("id")Integer id);
    
    int updateProductIsDeleted(@Param("isDeleted")Integer isDeleted,@Param("id")Integer id);
    
    List<Product> selectProductPageList(Page<Product> page,@Param("type") Integer type,@Param("category") Integer category);
    
    List<Product> selectProductList(@Param("type") Integer type,@Param("category") Integer category,@Param("regionId") Integer regionId);
}