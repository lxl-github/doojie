package com.doojie.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.util.MD5Util;
import com.doojie.domain.po.Employee;
import com.doojie.service.service.EmployeeService;

@Controller
@RequestMapping("webApp")
public class WebAppEmployeeController {

	
	//员工登录
		@Autowired
		EmployeeService employeeService;

		@RequestMapping("employeeLogin")
		public String login(Model model){
				return "webApp/employee/login";
		}
		
		
		@RequestMapping("doEmployeeLogin")
		@ResponseBody
		public Integer doLogin(Model model,HttpServletRequest request,@RequestParam("userName") String userName,@RequestParam("userPwd") String userPwd){
			Employee employee = employeeService.getEmployeeByNumber(userName);
			
			if(employee != null){
				if(employee.getPassword().equals(MD5Util.getMD5(userPwd))){
					request.getSession().setAttribute(BaseConstant.SYSTEM_EMPLOYEE,employee);
					request.getSession().setAttribute(BaseConstant.SYSTEM_EMPLOYEE_ID,employee.getId());
					return 0;//登录成功
				}else {
					return 2;//账户密码错误
				}
			}else{
				return 1;//账户不存在
			}
		}
		
		@RequestMapping("employeeLoginOut")
		public String logOut(HttpServletRequest request) {
			request.getSession().invalidate();
			return "redirect:/webApp/employeeLogin";
		}
		
		@RequestMapping("employee/my")
		public String toMy(){
			return "webApp/employee/my/index";
		}
	
	//接到订单 ---订单列表
	
	//开始洗车---- 必填项，上传洗车前照片
	
	//洗车完成---- 必填项，上传洗车后照片
	
}
