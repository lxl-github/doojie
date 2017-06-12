package com.doojie.service.service;

import java.util.List;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.EmployeeWorkRecord;
import com.doojie.domain.po.Order;
import com.doojie.domain.po.OrderTrack;
import com.doojie.domain.vo.AppointmentVo;
import com.doojie.domain.vo.OrderDetailVo;

public interface OrderService {

	/**
	 * 获取所有订单
	 * @param page
	 * @param orderSn 订单号
	 * @return
	 */
	List<Order> getOrderAllPageList(Page<Order> page, String orderSn, Integer userId, Integer bussinessId);
	
	Long saveOrder(Order order);
	
	boolean updateOrder(Order order);
	
	Order getOrderById(Long orderId);
	
	OrderDetailVo getOrderDetailVoByOrderId(Long orderId);
	
	/**
	 * 手工派单
	 * <br>
	 * 创建时间：2015年6月19日下午2:29:13
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param order
	 * @param orderTrack
	 * @param employeeWorkRecord
	 * @return
	 *
	 */
	boolean updateDistributionOrder(Integer status, Integer oldStatus, Long orderId, OrderTrack orderTrack, EmployeeWorkRecord employeeWorkRecord);
	
	OrderDetailVo getOrderByOrderIdAndStatus(Integer status, Long orderId);
	
	Order getOrderByOrderIdAndOldStatus(Integer oldStatus, Long orderId);
	
	boolean updateByOrderIdAndStatus(Order order, Integer status, Integer oldStatus);
	
	boolean updateOrderByOrderIdToAudit(Order order, Integer auditStatus, Integer oldStatus);
	
	Long getOrderNextId();
	
	boolean saveOrderTrack(OrderTrack orderTrack);
	
	String getTradeNo();
	
	/**
	 * 保存订单
	 * @param appointmentVo
	 * @param userId
	 * @param type 1：预约下单  2:验码下单
	 * @return
	 */
	Long saveOrder(AppointmentVo appointmentVo, Integer userId, Integer type);
}
