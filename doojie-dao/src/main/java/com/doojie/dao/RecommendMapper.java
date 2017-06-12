package com.doojie.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Recommend;

public interface RecommendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Recommend record);

    Recommend selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Recommend record);
    
    /**
     * 根据用户id查询自己推荐的商家及推荐理由和指数、时间
     * @param page
     * @param userId
     * @return
     */
    List<Map<String,Object>> selectRecommendByUserIdPageList(Page<Map<String,Object>> page,@Param("userId")Integer userId);
    
    /**
     *根据商家id查询被推荐的用户及理由和推荐指数 、时间
     * @param page
     * @param bussinessId
     * @return
     */
    List<Map<String,Object>> selectRecommendByBussinessIdPageList(Page<Map<String,Object>> page,@Param("bussinessId") Integer bussinessId);
    
    /**
     * 查看所有推荐数据,推荐用户，推荐商户，推荐理由，推荐指数，推荐时间，是否置顶  用于管理员查看
     * @param page
     * @param recommendIndex
     * @return
     */
    List<Map<String,Object>> selectRecommendAllPageList(Page<Map<String,Object>> page,@Param("isTop") Integer isTop,@Param("name") String name);
    
    /**
     * 根据用户id和商家id查询是否已经被推荐过
     * @param userId
     * @param bussinessId
     * @return
     */
    Integer isHasRecommended(@Param("userId") Integer userId,@Param("bussinessId")Integer bussinessId);
    
    /**
     * 根据用户id和订单id查询评价信息
     * @param userId
     * @param orderId
     * @return
     */
    Recommend selectRecommendByUserIdAndOrderId(@Param("userId")Integer userId,@Param("orderId")Long orderId);
}