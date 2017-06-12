package com.doojie.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.User;
import com.doojie.domain.vo.SuggestVo;
import com.doojie.domain.vo.UserProductVo;
import com.doojie.domain.vo.UserVo;
import com.doojie.service.service.UserService;

@Controller
@RequestMapping("/system/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	/**
	 * 所有用户列表 用于管理员查看
	 * @param model
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("system/user/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage){
		Page<User> page = new Page<User>();
		
		page.setCurrentPage(currentPage);
		
		List<UserVo> userList = userService.getUserPageList(page);
		
		model.addAttribute("list",userList);
		
		model.addAttribute("page",page);
		
		return "system/user/list"; 
	}
	
	
	
	@RequestMapping("suggestList")
	@RequiresPermissions("system/user/suggestList")
	public String suggestLsit(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage){
		Page<SuggestVo> suggestVoPage = userService.getSuggestAllPageList(currentPage);
		model.addAttribute("page",suggestVoPage);
		return "system/user/suggestList";
	}
	
	@RequestMapping("userProductList/{userId}")
	public String userProductList(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,@PathVariable Integer userId){
		Page<UserProductVo> page = new Page<UserProductVo>();
		page.setCurrentPage(currentPage);
		List<UserProductVo> userProductVoList = userService.getUserProductVoPageList(page, userId);
		model.addAttribute("list",userProductVoList);
		model.addAttribute("page",page);
		model.addAttribute("userId",userId);
		return "system/user/userProductList";
	}
}
