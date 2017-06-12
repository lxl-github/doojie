package com.doojie.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.constant.OrderConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.HttpClientUtil;
import com.doojie.common.util.JsonUtil;
import com.doojie.common.util.MD5Util;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.po.Order;
import com.doojie.domain.po.Product;
import com.doojie.domain.po.User;
import com.doojie.domain.po.UserProduct;
import com.doojie.domain.vo.AppointmentVo;
import com.doojie.domain.vo.OrderDetailVo;
import com.doojie.service.service.BussinessService;
import com.doojie.service.service.OrderService;
import com.doojie.service.service.ProductService;
import com.doojie.service.service.UserService;
import com.doojie.service.service.util.RedisCacheServiceUtil;
import com.doojie.service.service.util.WeiXinServiceUtil;

@Controller
public class WebAppMerchantController extends WeiXinController{

	
	private static final Logger logger = LoggerFactory.getLogger(WebAppMerchantController.class);
	
	//商家登录
	@Autowired
	BussinessService bussinessService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	

	@RequestMapping("merchantLogin")
	public String login(Model model){
			return "webApp/merchant/login";
	}
	
	/**
	 * ajax校验验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxCheckVerifyCode")
	public @ResponseBody
	Boolean ajaxCheckCHVerifyCode(@RequestParam("verifyCode") String verifyCode,HttpServletRequest request) {
		String rs = (String) request.getSession().getAttribute("validateCode"); // 获取验证码
		return rs != null && rs.equalsIgnoreCase(verifyCode);
	}
	
	@RequestMapping("doMerchantLogin")
	@ResponseBody
	public Integer doLogin(Model model,HttpServletRequest request,@RequestParam("userName") String userName,@RequestParam("userPwd") String userPwd){
		Bussiness bussiness = bussinessService.getBussinessByuserName(userName);
		
		if(bussiness != null){
			if(bussiness.getPassword().equals(MD5Util.getMD5(userPwd))){
				request.getSession().setAttribute(BaseConstant.BUSSINESS,bussiness);
				request.getSession().setAttribute(BaseConstant.BUSSINESS_ID,bussiness.getId());
				return 0;//登录成功
			}else {
				return 2;//账户密码错误
			}
		}else{
			return 1;//账户不存在
		}
	}
	
	@RequestMapping("merchantLoginOut")
	public String logOut(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/webApp/merchant/login";
	}
	
	@RequestMapping("toMerchantMyIndex")
	public String toMy(Model model,HttpServletRequest request){
		Integer bussinessId = (Integer)request.getSession().getAttribute(BaseConstant.BUSSINESS_ID);
		if(bussinessId == null){
			return login(model);
		}
		return "webApp/merchant/my/index";
	}
	
	//我的订单
	@RequestMapping("toMyMerchantOrder")
	public String toMyMerchantOrder(Model model,HttpServletRequest request){
		Integer bussinessId = (Integer)request.getSession().getAttribute(BaseConstant.BUSSINESS_ID);
		if(bussinessId == null){
			return login(model);
		}
		return "webApp/merchant/order/index";
	}
	
	@RequestMapping("merchant/myOrder")
	public @ResponseBody String myOrder(Model model,HttpServletRequest request,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage) throws IOException{
		Integer bussinessId = (Integer)request.getSession().getAttribute(BaseConstant.BUSSINESS_ID);
		Page<Order> page = new Page<Order>();
		orderService.getOrderAllPageList(page,null, null,bussinessId);
		return JsonUtil.toJson(page);
	}
	
	//跳转订单详情
	@RequestMapping("toMerchantOrderDetail/{orderId}")
	public String toMyMerchantOrder(Model model,@PathVariable Long orderId){
		model.addAttribute("orderId",orderId);
		return "webApp/merchant/order/detail";
	}
	
	@RequestMapping("merchant/myOrderDetail")
	public @ResponseBody String myOrderDetail(Model model,Long orderId) throws IOException{
		OrderDetailVo orderDetailVo = orderService.getOrderDetailVoByOrderId(orderId);
		return JsonUtil.toJson(orderDetailVo);
	}
	
	//跳转消费验码
	@RequestMapping("toMyMerchantCheckCode")
	public String toMyMerchantCheckCode(Model model,HttpServletRequest request){
		Integer bussinessId = (Integer)request.getSession().getAttribute(BaseConstant.BUSSINESS_ID);
		if(bussinessId == null){
			return login(model);
		}
		this.getWeiXinToken(model, request);
		return "webApp/merchant/my/checkCode";
	}
	
	/**
	 * 消费验码
	 * @param model
	 * @param code
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("merchant/checkCode/{code}")
	public @ResponseBody String checkCode(Model model,HttpServletRequest request,@PathVariable("code")String code) throws IOException{
		try{
			Integer bussinessId = (Integer)request.getSession().getAttribute(BaseConstant.BUSSINESS_ID);
			code = code.toUpperCase();
			String value = this.getTokenCode(code);
			if(value == null){
				return "uncode";
			}
			String[] valueArry = value.split("\\&");
			Integer userId = Integer.valueOf(valueArry[0]);
			String cardId = valueArry[1];
			
			//根据卡号查询用户卡信息
			UserProduct userProduct = userService.getUserProductById(Integer.valueOf(cardId));
			Product product = productService.getProductById(userProduct.getProductId());
			User user = userService.getUserById(userId);
			AppointmentVo appointmentVo = new AppointmentVo();
			appointmentVo.setBussinessId(bussinessId);
			appointmentVo.setCards(cardId);
			appointmentVo.setOrderType(product.getProductCategory());
			appointmentVo.setMobile(user.getMobile());
			appointmentVo.setAppiontmentDate(DateUtil.getDate2());
			appointmentVo.setTime(DateUtil.getTimeSpanByCurrentHour());
			Long returnVal = orderService.saveOrder(appointmentVo, userId, OrderConstant.ORDER_CHECKCODE);
			if(returnVal == 0){
				return "fail";
			}else if(returnVal == 2){
				return "unused";
			}else{
				this.delTokenCode(code);
				return returnVal.toString();
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("checkCode excption {}",e.getMessage());
			return "exception";
		}
	}
	
	//开始处理
	@RequestMapping("merchant/orderStartHandle")
	public @ResponseBody Boolean orderStartHandle(Long orderId,Integer oldStatus){
		try{
			Order order = orderService.getOrderById(orderId);
			boolean f = orderService.updateByOrderIdAndStatus(order,OrderConstant.HANDLE_CURRENT_ORDER_STATUS, oldStatus);
			return f;
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("orderStartHandle exception {}",e.getMessage());
			return false;
		}
	}
	
	//洗车完成
	@RequestMapping("merchant/orderCompleteHandle")
	public @ResponseBody Boolean orderCompleteHandle(Long orderId,Integer oldStatus){
		try{
			Order order = orderService.getOrderById(orderId);
			boolean f = orderService.updateByOrderIdAndStatus(order,OrderConstant.HANDLE_COMPLETE_ORDER_STATUS, oldStatus);
			return f;
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("orderStartHandle exception {}",e.getMessage());
			return false;
		}
	}
	
	
	
}
