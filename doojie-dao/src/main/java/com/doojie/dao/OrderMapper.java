package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Order;
import com.doojie.domain.vo.OrderDetailVo;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    Long insert(Order record);

    Long insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    int updateIsRecommendById(Long orderId);
    
    List<Order> selectOrderAllPageList(Page<Order> page,@Param("orderSn")String orderSn,@Param("userId")Integer userId,@Param("bussinessId")Integer bussinessId);
    
    OrderDetailVo selectOrderDetailByOrderId(Long orderId);
    
    int updateByOrderIdAndStatus(@Param("orderId")Long orderId,@Param("status")Integer status,@Param("oldStatus")Integer oldStatus);
    
    OrderDetailVo selectByOrderIdAndStatus(@Param("orderId")Long orderId,@Param("status")Integer status);
    
    Order selectOrderByOrderIdAndStatus(@Param("orderId")Long orderId,@Param("status")Integer status);
    
    int updateByOrderIdToAudit(@Param("orderId")Long orderId,@Param("auditStatus")Integer auditStatus);
    
    /**
     * 获取下一个自增id
     * <br>
     * 创建时间：2015年6月29日下午4:32:25
     * <br>
     * @author lixiaoliang
     * <br>
     * @return
     *
     */
    Long selectOrderNextId();
}