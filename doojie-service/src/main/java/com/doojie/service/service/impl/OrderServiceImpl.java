package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.constant.OrderConstant;
import com.doojie.common.constant.UserConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.RandomUtil;
import com.doojie.dao.EmployeeWorkRecordMapper;
import com.doojie.dao.OrderMapper;
import com.doojie.dao.OrderTrackMapper;
import com.doojie.dao.TradeRecordMapper;
import com.doojie.dao.UserProductMapper;
import com.doojie.domain.bo.TimeSpan;
import com.doojie.domain.po.EmployeeWorkRecord;
import com.doojie.domain.po.Order;
import com.doojie.domain.po.OrderTrack;
import com.doojie.domain.po.UserProduct;
import com.doojie.domain.vo.AppointmentVo;
import com.doojie.domain.vo.OrderDetailVo;
import com.doojie.service.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderTrackMapper orderTrackMapper;
	
	@Autowired
	private EmployeeWorkRecordMapper employeeWorkRecordMapper;
	
	@Autowired
	private UserProductMapper userProductMapper;
	
	@Autowired
	private TradeRecordMapper tradeRecordMapper;
	
	@Override
	public List<Order> getOrderAllPageList(Page<Order> page, String orderSn,Integer userId,Integer bussinessId) {
		List<Order> orderList = orderMapper.selectOrderAllPageList(page, orderSn,userId,bussinessId);
		page.setResults(orderList);
		return orderList;
	}

	@Override
	public Long saveOrder(Order order) {
		Long count = orderMapper.insertSelective(order);
		return count;
	}

	@Override
	public boolean updateOrder(Order order) {
        order.setModifyTime(DateUtil.getCurrentTimespan());
		int count = orderMapper.updateByPrimaryKeySelective(order);
		return count > 0;
	}

	@Override
	public Order getOrderById(Long orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public OrderDetailVo getOrderDetailVoByOrderId(Long orderId) {
		return orderMapper.selectOrderDetailByOrderId(orderId);
	}

	@Override
	public boolean updateDistributionOrder(Integer status,Integer oldStatus,Long orderId,OrderTrack orderTrack, EmployeeWorkRecord employeeWorkRecord) {
		int count = orderMapper.updateByOrderIdAndStatus(orderId, status, oldStatus);
		if(count > 0){
			int orderTrackCount = orderTrackMapper.insertSelective(orderTrack);
			if(orderTrackCount > 0){
				int employeeWorkRecordCount = employeeWorkRecordMapper.insertSelective(employeeWorkRecord);
				if(employeeWorkRecordCount > 0){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public OrderDetailVo getOrderByOrderIdAndStatus(Integer status, Long orderId) {
		return orderMapper.selectByOrderIdAndStatus(orderId, status);
	}

	@Override
	public Order getOrderByOrderIdAndOldStatus(Integer oldStatus, Long orderId) {
		return orderMapper.selectOrderByOrderIdAndStatus(orderId,oldStatus);
	}

	@Override
	public boolean updateByOrderIdAndStatus(Order order, Integer status,Integer oldStatus) {
		//取消订单，修改订单状态为2、增加跟踪记录，修改卡状态
		int count = orderMapper.updateByOrderIdAndStatus(order.getId(), status, oldStatus);
		if(count > 0){
			OrderTrack orderTrack = new OrderTrack();
			if(order.getOrderOwn() == OrderConstant.ORDER_OWN_BUSSINESS){
				orderTrack.setContent(OrderConstant.BUSSINESS_CONTENT_MAP.get(status));
			}else{
				orderTrack.setContent(OrderConstant.SELF_CONTENT_MAP.get(status));
			}
			orderTrack.setCreateTime(DateUtil.getCurrentTimespan());
			orderTrack.setOrderId(order.getId());
			int orderTrackCount = orderTrackMapper.insertSelective(orderTrack);
			if(orderTrackCount > 0){
				//修改卡状态的条件：当订单状态为(取消、无效、消费完成)改为未使用
				if(status == OrderConstant.CANEAL_ORDER_STATUS || status == OrderConstant.INVALID_ORDER_STATUS || status == OrderConstant.CONSUM_COMPLETE_ORDER_STATUS){
					userProductMapper.updateStatusById(order.getUserProductId(), UserConstant.CARD_STATUS_NOT_USED);
				}
				//订单状态为消费完成时 则扣减卡可消费次数，叠加已消费次数
				if(status == OrderConstant.CONSUM_COMPLETE_ORDER_STATUS){
					userProductMapper.updateConsumNumberAlreadyById(order.getUserProductId());
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateOrderByOrderIdToAudit(Order order, Integer auditStatus, Integer oldStatus) {
		int auditCount = orderMapper.updateByOrderIdToAudit(order.getId(), auditStatus);
		if (auditStatus == 1) {// 审核通过
			if (auditCount > 0) {
				return true;
			}
		} else if (auditStatus == 2) {// 审核未通过，修改状态为无效订单6、增加跟踪记录
			if (auditCount > 0) {
				int count = orderMapper
						.updateByOrderIdAndStatus(order.getId(), OrderConstant.INVALID_ORDER_STATUS, oldStatus);
				if (count > 0) {
					OrderTrack orderTrack = new OrderTrack();
					if(order.getOrderOwn() == OrderConstant.ORDER_OWN_BUSSINESS){
						orderTrack.setContent(OrderConstant.BUSSINESS_CONTENT_MAP.get(OrderConstant.INVALID_ORDER_STATUS));
					}else{
						orderTrack.setContent(OrderConstant.SELF_CONTENT_MAP.get(OrderConstant.INVALID_ORDER_STATUS));
					}
					orderTrack.setCreateTime(DateUtil.getCurrentTimespan());
					orderTrack.setOrderId(order.getId());
					int orderTrackCount = orderTrackMapper.insertSelective(orderTrack);
					if (orderTrackCount > 0) {
						userProductMapper.updateStatusById(order.getUserProductId(), UserConstant.CARD_STATUS_NOT_USED);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Long getOrderNextId() {
		return orderMapper.selectOrderNextId();
	}

	@Override
	public boolean saveOrderTrack(OrderTrack orderTrack) {
		int count = orderTrackMapper.insertSelective(orderTrack);
		return count > 0;
	}

	@Override
	public Long saveOrder(AppointmentVo appointmentVo, Integer userId,Integer type) {
		if(type == OrderConstant.ORDER_APPOI){
			return appointmentOrder(appointmentVo, userId);
		}else{
			return checkCodeOrder(appointmentVo, userId);
		}
	}

	/**
	 * 验码下单
	 * @param appointmentVo
	 * @param userId
	 * @return
	 */
	private Long checkCodeOrder(AppointmentVo appointmentVo, Integer userId) {
		UserProduct userProduct = userProductMapper.selectByPrimaryKey(Integer.valueOf(appointmentVo.getCards()));
		if(userProduct.getStatus() == UserConstant.CARD_STATUS_IN_USED || userProduct.getConsumeNumber() == 0){
			return 2l;//卡在使用中或消费次数已用完
		}
		
		Long orderId = getOrderNextId();
		Order order = new Order();
		String prefix = "XC";
		if(appointmentVo.getOrderType() == 2){
			prefix = "WX";
		}else if(appointmentVo.getOrderType() == 3){
			prefix = "BY";
		}
		//服务类型+8位日期+订单表当前最大值
		String orderSn = prefix + DateUtil.getDate() + orderId;
		order.setOrderSn(orderSn);
		order.setAppointmentTime(appointmentVo.getAppiontmentDate()+" "+appointmentVo.getTime());
		order.setConsumeCode(userProduct.getConsumeCode());
		order.setUserProductId(userProduct.getId());
		order.setCreateTime(DateUtil.getCurrentTimespan());
		order.setUserId(userId);
		order.setPlateNumber("无");
		order.setMobileNumber(appointmentVo.getMobile());
		order.setOrderType(appointmentVo.getOrderType());
		order.setStatus(OrderConstant.HANDLE_CURRENT_ORDER_STATUS);
		order.setAuditStatus(1);
		order.setIsRecommend("0");
        order.setModifyTime(DateUtil.getCurrentTimespan());
		if(appointmentVo.getBussinessId() != null && appointmentVo.getBussinessId().intValue() != 0 ){
			bussinessOrder(order,appointmentVo);
		}else{
			selfOrder(order, appointmentVo);
		}
		//保存订单
		Long resultOrderId = saveOrder(order);
		if(resultOrderId > 0){
			List<OrderTrack> orderTrackList = new ArrayList<OrderTrack>();
			//保存订单跟踪记录
			OrderTrack orderTrack = new OrderTrack();
			if(order.getOrderOwn().intValue() == OrderConstant.ORDER_OWN_BUSSINESS.intValue()){
				orderTrack.setContent(OrderConstant.BUSSINESS_ORDER_STATUS_1_1);
			}else{
				orderTrack.setContent(OrderConstant.SELF_ORDER_STATUS_1);
			}
			orderTrack.setCreateTime(DateUtil.getCurrentTimespan());
			orderTrack.setOperationType(4);
			orderTrack.setOrderId(orderId);
			
			orderTrackList.add(orderTrack);
			
			//保存订单跟踪记录
			orderTrack = new OrderTrack();
			if(order.getOrderOwn().intValue() == OrderConstant.ORDER_OWN_BUSSINESS.intValue()){
				orderTrack.setContent(OrderConstant.BUSSINESS_ORDER_STATUS_4);
			}else{
				orderTrack.setContent(OrderConstant.SELF_ORDER_STATUS_4);
			}
			orderTrack.setCreateTime(DateUtil.getCurrentAfterOneTimespan());
			orderTrack.setOperationType(4);
			orderTrack.setOrderId(orderId);
			
			orderTrackList.add(orderTrack);
			
			orderTrackMapper.insertBatch(orderTrackList);
			
			//修改都捷卡状态为使用中
			userProduct.setStatus(UserConstant.CARD_STATUS_IN_USED);
			userProductMapper.updateByPrimaryKeySelective(userProduct);
			return orderId;//成功
		}else{
			return 0l;//失败
		}
	}
	
	/**
	 * 预约下单
	 * @param appointmentVo
	 * @param userId
	 * @return
	 */
	private Long appointmentOrder(AppointmentVo appointmentVo, Integer userId) {
		UserProduct userProduct = userProductMapper.selectByPrimaryKey(Integer.valueOf(appointmentVo.getCards()));
		if(userProduct.getStatus() == UserConstant.CARD_STATUS_IN_USED || userProduct.getConsumeNumber() == 0){
			return 2l;//卡在使用中或消费次数已用完
		}
		
		//只有预约当天 才做效验
		if(appointmentVo.getAppiontmentDate().equals(DateUtil.getDate2())){
			boolean f = DateUtil.isTimeSpanChoose(appointmentVo.getTime());
			if(!f){
				return 4l;//时间段已过期，请重新选择
			}
		}
		
		Long orderId = getOrderNextId();
		Order order = new Order();
		String prefix = "XC";
		if(appointmentVo.getOrderType() == 2){
			prefix = "WX";
		}else if(appointmentVo.getOrderType() == 3){
			prefix = "BY";
		}
		//服务类型+8位日期+订单表当前最大值
		String orderSn = prefix + DateUtil.getDate() + orderId;
		order.setOrderSn(orderSn);
		order.setAppointmentTime(appointmentVo.getAppiontmentDate()+" "+appointmentVo.getTime());
		order.setConsumeCode(userProduct.getConsumeCode());
		order.setUserProductId(userProduct.getId());
		order.setCreateTime(DateUtil.getCurrentTimespan());
		order.setUserId(userId);
		order.setPlateNumber(appointmentVo.getPlate()+appointmentVo.getLetter()+appointmentVo.getNumber());
		order.setMobileNumber(appointmentVo.getMobile());
		order.setOrderType(appointmentVo.getOrderType());
		order.setStatus(OrderConstant.SUCCESS_ORDER_STATUS);
		order.setAuditStatus(0);
		order.setIsRecommend("0");
        order.setModifyTime(DateUtil.getCurrentTimespan());
		if(appointmentVo.getBussinessId() != null && appointmentVo.getBussinessId().intValue() != 0 ){
			bussinessOrder(order,appointmentVo);
		}else{
			selfOrder(order, appointmentVo);
		}
		//保存订单
		Long resultOrderId = saveOrder(order);
		if(resultOrderId > 0){
			//保存订单跟踪记录
			OrderTrack orderTrack = new OrderTrack();
			if(order.getOrderOwn().intValue() == OrderConstant.ORDER_OWN_BUSSINESS.intValue()){
				orderTrack.setContent(OrderConstant.BUSSINESS_ORDER_STATUS_1);
			}else{
				orderTrack.setContent(OrderConstant.SELF_ORDER_STATUS_1);
			}
			orderTrack.setCreateTime(DateUtil.getCurrentTimespan());
			orderTrack.setOperationType(4);
			orderTrack.setOrderId(orderId);
			saveOrderTrack(orderTrack);
			//修改都捷卡状态为使用中
			userProduct.setStatus(UserConstant.CARD_STATUS_IN_USED);
			userProductMapper.updateByPrimaryKeySelective(userProduct);
			return orderId;//成功
		}else{
			return 0l;//失败
		}
	}
	
	private void bussinessOrder(Order order,AppointmentVo appointmentVo){
		order.setBussinessId(appointmentVo.getBussinessId());
		order.setOrderOwn(OrderConstant.ORDER_OWN_BUSSINESS);
	}
	
	private void selfOrder(Order order,AppointmentVo appointmentVo){
		order.setOrderOwn(OrderConstant.ORDER_OWN_SELF);
	}

	@Override
	public String getTradeNo() {
		String randomNum = UUID.randomUUID().toString().substring(0,8);
		return DateUtil.getDate().concat(randomNum);
	}

}
