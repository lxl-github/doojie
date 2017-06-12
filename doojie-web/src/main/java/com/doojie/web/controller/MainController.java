package com.doojie.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.doojie.common.constant.BaseConstant;
import com.doojie.service.service.MainService;
import com.doojie.shiro.util.SessionUtil;

@Controller
@RequestMapping("/system")
public class MainController {

	@Autowired
	private MainService mainService;
	
	@RequestMapping("main")
	public String main(Model model){
		model.addAttribute(BaseConstant.SYSTEM_EMPLOYEE,SessionUtil.getCurrentEmployee());
		return "system/main";
	}
	
	@RequestMapping("index")
	public String index(Model model){
		return "system/index";
	}
	
	@RequestMapping("left")
	public String left(Model model,HttpServletRequest request){
		Integer employeeId = SessionUtil.getCurrentEmployeeId();
		String newTree = mainService.createFunctionTree(employeeId,request.getContextPath());
		model.addAttribute("mis3newTree", newTree);
		return "system/left";
	}
	
	@RequestMapping("top")
	public String top(Model model){
		return "system/top";
	}
	
//	@RequestMapping("/logOut")
//	public String logOut() {
//		Subject currentUser = SecurityUtils.getSubject();
//		currentUser.logout();
//		return "redirect:bussiness/login";
//	}
	
	@RequestMapping("/unAuthor")
	public String unAuthor(){
		return "error/unauthor";
	}
}
