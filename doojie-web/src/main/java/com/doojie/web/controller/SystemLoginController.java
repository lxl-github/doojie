package com.doojie.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.util.MD5Util;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.po.Employee;
import com.doojie.service.service.BussinessService;
import com.doojie.service.service.EmployeeService;
import com.doojie.shiro.dto.BussinessDTO;
import com.doojie.shiro.dto.EmployeeDTO;
import com.doojie.shiro.realm.DoojieUsernamePasswordToken;
import com.doojie.shiro.util.SessionUtil;

@Controller
@RequestMapping("/system")
public class SystemLoginController {
	
	@Autowired
	EmployeeService employeeService;

	@RequestMapping("login")
	public String login(Model model){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()){
			return "redirect:/system/main";
		}else{
			return "system/login";
		}
	}
	
	/**
	 * ajax校验验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("ajaxCheckVerifyCode")
	public @ResponseBody
	Boolean ajaxCheckCHVerifyCode(@RequestParam("verifyCode") String verifyCode) {
		String rs = (String) SessionUtil.getAttribute("validateCode"); // 获取验证码
		return rs != null && rs.equalsIgnoreCase(verifyCode);
	}
	
	@RequestMapping("doLogin")
	public String doLogin(Model model,RedirectAttributes redirectAttributes,@RequestParam("userName") String userName,@RequestParam("userPwd") String userPwd){
		Employee employee = employeeService.getEmployeeByNumber(userName);
		
		if(employee != null){
			if(employee.getIsLoginSystem() == null || employee.getIsLoginSystem().intValue() == 0){
				redirectAttributes.addAttribute("flag",3);
				return "redirect:/system/login";//账户不可登录
			}else{
				if(employee.getPassword().equals(MD5Util.getMD5(userPwd))){
					DoojieUsernamePasswordToken token = new DoojieUsernamePasswordToken();
					token.setUsername(employee.getNumber());
					token.setPassword(employee.getPassword().toCharArray());
					Subject currentUser = SecurityUtils.getSubject();
					currentUser.login(token);
					
					EmployeeDTO employeeDTO = new EmployeeDTO();
					BeanUtils.copyProperties(employee, employeeDTO);
					
					SessionUtil.setAttribute(BaseConstant.SYSTEM_EMPLOYEE,employeeDTO);
					SessionUtil.setAttribute(BaseConstant.BUSSINESS_ID,employeeDTO.getId());
					return "redirect:/system/main";//登录成功
				}else {
					redirectAttributes.addAttribute("flag",2);
					return "redirect:/system/login";//账户密码错误
				}
			}
		}else{
			redirectAttributes.addAttribute("flag",1);
			return "redirect:/system/login";//账户不存在
		}
	}
	
	@RequestMapping("/logOut")
	public String logOut() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:/system/login";
	}
}
