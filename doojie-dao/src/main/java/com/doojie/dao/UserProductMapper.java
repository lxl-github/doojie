package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.UserProduct;
import com.doojie.domain.vo.UserCardVo;
import com.doojie.domain.vo.UserProductVo;

public interface UserProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserProduct record);

    int insertSelective(UserProduct record);

    UserProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserProduct record);

    int updateByPrimaryKey(UserProduct record);
    
    List<UserProductVo> getUserProductVoPageList(Page<UserProductVo> page,@Param("userId") Integer userId);
    
    List<UserCardVo> getUserCardVoList(@Param("userId") Integer userId,@Param("category") Integer category,@Param("isDoor") Integer isDoor);
    
    int updateStatusById(@Param("userProductId")Integer userProductId,@Param("status")Integer status);
    
    int updateConsumNumberAlreadyById(@Param("userProductId")Integer userProductId);
    
    Integer selectUserProdcutNextId();
}