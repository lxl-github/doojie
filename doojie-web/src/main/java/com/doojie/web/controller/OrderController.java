package com.doojie.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.constant.OrderConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.domain.po.Employee;
import com.doojie.domain.po.EmployeeWorkRecord;
import com.doojie.domain.po.Order;
import com.doojie.domain.po.OrderTrack;
import com.doojie.domain.vo.OrderDetailVo;
import com.doojie.service.service.EmployeeService;
import com.doojie.service.service.OrderService;

@Controller
@RequestMapping("/system/order")
public class OrderController {

	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping("list")
	@RequiresPermissions("system/order/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,String orderSn){
		if("".equals(orderSn)){
			orderSn = null;
		}
		Page<Order> page = new Page<Order>();
		page.setCurrentPage(currentPage);
		List<Order> orderList = orderService.getOrderAllPageList(page, orderSn,null,null);
		
		model.addAttribute("list",orderList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("orderSn", orderSn);
		
		return "system/order/list";
	}
	
	@RequestMapping("detail/{orderId}")
	public String detail(Model model,@PathVariable Long orderId){
		
		long startTime = System.currentTimeMillis();
		
		OrderDetailVo orderDetailVo = orderService.getOrderDetailVoByOrderId(orderId);
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("详情查询执行时间:" + (endTime - startTime) + "ms");
		
		model.addAttribute("orderDetailVo",orderDetailVo);
		
		return "system/order/detail";
	}
	
	@RequestMapping("selectEmployee")
	public String selectEmployee(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,Integer regionId,Integer orderType,Long orderId,Integer status){
		Page<Employee> page = new Page<Employee>();
		page.setCurrentPage(currentPage);
		
		List<Employee> employeeList = employeeService.getEmployeeByOrderTypeAndRegionIdPageList(page, regionId, orderType);
		
		model.addAttribute("list",employeeList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("regionId",regionId);
		
		model.addAttribute("orderType",orderType);
		
		model.addAttribute("orderId",orderId);
		
		model.addAttribute("oldStatus",status);
		
		return "system/order/selectEmployee";
	}
	
	/**
	 * 手工派单
	 * <br>
	 * 创建时间：2015年6月19日下午6:12:42
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param oldStatus
	 * @param orderId
	 * @param employeeId
	 * @return
	 *
	 */
	@RequestMapping("distributionOrder/{oldStatus}/{orderId}/{employeeId}")
	public @ResponseBody Integer doDistributionOrder(@PathVariable Integer oldStatus,@PathVariable Long orderId,@PathVariable Integer employeeId){
		
		OrderDetailVo order = orderService.getOrderByOrderIdAndStatus(oldStatus, orderId);
		
		if(order != null){
			try{
				OrderTrack orderTrack = new OrderTrack();
				orderTrack.setOrderId(orderId);
				orderTrack.setCreateTime(DateUtil.getCurrentTimespan());
				orderTrack.setContent(OrderConstant.SELF_ORDER_STATUS_3);
				
				EmployeeWorkRecord employeeWorkRecord = new EmployeeWorkRecord();
				employeeWorkRecord.setCreateTime(DateUtil.getCurrentTimespan());
				employeeWorkRecord.setEmployeeId(employeeId);
				employeeWorkRecord.setOrderId(orderId);
				
				boolean flag = orderService.updateDistributionOrder(OrderConstant.DISTRIBUTION_ORDER_STATUS, oldStatus, orderId, orderTrack, employeeWorkRecord);
				if(flag){
					order = orderService.getOrderByOrderIdAndStatus(OrderConstant.DISTRIBUTION_ORDER_STATUS, orderId);
					String userWeixinNumber = order.getUser().getWeixinNumber();
					String employeeWxinNumber = order.getEmployeeWorkRecordVo().getEmployee().getWeixinNumber();
					
					
					//可以异步将消息发送于订单客户，及处理订单的工作人员的微信号中
					return 1;//派单成功
				}else{
					return 2;//派单失败，稍后再试
				}
			}catch (Exception e) {
				e.printStackTrace();
				logger.debug("doDistributionOrder exception :{}",e.getMessage());
				return 3;//系统异常
			}
		}else{
			return 4;//无法派单，请刷新订单列表，查看状态
		}
	}
	
	@RequestMapping("edit/{orderId}")
	public String toEdit(Model model,@PathVariable Long orderId){
		OrderDetailVo detailVo = orderService.getOrderDetailVoByOrderId(orderId);
		model.addAttribute("orderDetailVo", detailVo);
		return "system/order/edit";
	}
	
	@RequestMapping("update")
	public String update(RedirectAttributes redirectAttributes,Long orderId,String appointmentDate,String time){
		Order order = orderService.getOrderById(orderId);
		if(order != null){
			//订单状态等于1或者3可修改
			if(order.getStatus() == OrderConstant.SUCCESS_ORDER_STATUS || order.getStatus() == OrderConstant.DISTRIBUTION_ORDER_STATUS){
				//预约时间不能小于当前时间
				Integer appointmentTime = DateUtil.getTimespan2(appointmentDate);
				Integer currentTime = DateUtil.getTimespan2(DateUtil.getDate2());
				if(appointmentTime < currentTime){
					redirectAttributes.addFlashAttribute("flag", "timeLess");
					return "redirect:/system/order/edit/"+orderId;
				}
				
				if(appointmentTime.intValue() == currentTime.intValue()){
					String[] timeArry = time.split("~");
					Integer timeStart = Integer.valueOf(timeArry[0].split(":")[0]);
					Integer timeEnd = Integer.valueOf(timeArry[1].split(":")[0]);
					Integer currentHour = DateUtil.getHour();
					if(!(timeStart.intValue() <= currentHour.intValue() && currentHour.intValue() <= timeEnd.intValue())){
						redirectAttributes.addFlashAttribute("flag", "hourLess");
						return "redirect:/system/order/edit/"+orderId;
					}
				}
				
				order.setAppointmentTime(appointmentDate.concat(" ").concat(time));
				order.setModifyTime(DateUtil.getCurrentTimespan());
				boolean f = orderService.updateOrder(order);
				if(f){
					redirectAttributes.addFlashAttribute("flag", "true");
				}else{
					redirectAttributes.addFlashAttribute("flag","false");
				}
				return "redirect:/system/order/list";
			}else{
				redirectAttributes.addFlashAttribute("flag","changeStatus");
			}
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/order/edit/"+orderId;
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @param oldStatus
	 * @return
	 */
	@RequestMapping("cancel/{orderId}/{oldStatus}")
	public @ResponseBody Integer cancel(@PathVariable Long orderId,@PathVariable Integer oldStatus){
		try{
			Order order = orderService.getOrderByOrderIdAndOldStatus(oldStatus, orderId);
			if(order != null){
				boolean f = orderService.updateByOrderIdAndStatus(order,OrderConstant.CANEAL_ORDER_STATUS, oldStatus);
				if(f){
					return 1;//订单取消成功
				}else{
					return 3;//订单取消失败
				}
			}else{
				//订单状态已变更，取消失败，请刷新列表
				return 2;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("cancel exception :{}", ex.getMessage());
		}
		//订单状态已变更，取消失败，请刷新列表
		return 2;
	}
	
	/**
	 * 审核
	 * <br>
	 * 创建时间：2015年6月24日下午3:31:45
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param orderId
	 * @param oldStatus
	 * @param auditStatus
	 * @return
	 *
	 */
	@RequestMapping("audit/{orderId}/{oldStatus}/{auditStatus}")
	public @ResponseBody Integer audit(@PathVariable Long orderId,@PathVariable Integer oldStatus,@PathVariable Integer auditStatus){
		try{
			Order order = orderService.getOrderByOrderIdAndOldStatus(oldStatus, orderId);
			boolean f = orderService.updateOrderByOrderIdToAudit(order, auditStatus, oldStatus);
			if(f){
				return 1;//操作成功
			}else{
				return 2;//操作失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.debug("updateOrderByOrderIdToAudit exception :{}", ex.getMessage());
		}
		return 2;
	}
}
