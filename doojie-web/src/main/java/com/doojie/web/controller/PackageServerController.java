package com.doojie.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.constant.ServerConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.domain.po.PackageServer;
import com.doojie.domain.po.Server;
import com.doojie.domain.vo.PackageServerVo;
import com.doojie.service.service.PackageServerService;
import com.doojie.service.service.ServerService;
import com.doojie.shiro.util.SessionUtil;

@Controller
@RequestMapping("/bussiness/package")
public class PackageServerController {

	@Autowired
	private PackageServerService packageServerService;
	
	@Autowired
	private ServerService serverService;
	
	@RequestMapping("list")
	@RequiresPermissions("bussiness/package/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,String name,String status){
		Page<PackageServer> page = new Page<PackageServer>();
		
		page.setCurrentPage(currentPage);
		
		List<PackageServerVo> packageServerList = packageServerService.getPackageServerByBussinessIdPageLsit(page,0,name,status);
		
		model.addAttribute("list",packageServerList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("name",name);
		
		model.addAttribute("status",status);
		
		return "system/package/list"; 
	}
	
	/**
	 * 跳转保存页面
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model){
		List<Server> serverList = serverService.getServerByBussinessIdLsit(0);
		model.addAttribute("serverList",serverList);
		return "system/package/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String name){
		return packageServerService.isExistsPackageServerByName(name,0);
	}
	
	/**
	 * 保存套餐服务
	 * @param model
	 * @param server
	 * @return
	 */
	@RequestMapping("save")
	public String save(Model model,PackageServer packageServer,Integer[] serverId,RedirectAttributes redirectAttributes){
		packageServer.setStatus(ServerConstant.ENABLED);
		packageServer.setCreateTime(DateUtil.getCurrentTimespan());
		packageServer.setBussinessId(0);
		packageServer.setModifyTime(DateUtil.getCurrentTimespan());
		packageServer.setPrice(packageServer.getPrice()*100);
		packageServer.setVipPrice(packageServer.getVipPrice()*100);
		Boolean flag = packageServerService.savePackage(packageServer, serverId);
		if(flag){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/bussiness/package/list";
	}
	
	/**
	 * 跳转编辑页
	 * @param model
	 * @param serverId
	 * @return
	 */
	@RequestMapping("edit/{packageId}")
	public String edit(Model model,@PathVariable Integer packageId){
		PackageServer packageServer = packageServerService.getPackageServerById(packageId);
		model.addAttribute("packageServer", packageServer);
		return "system/package/edit";
	}
	
	
	/**
	 * 修改套餐
	 * @param model
	 * @param server
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model,PackageServer packageServer,Integer[] serverId,RedirectAttributes redirectAttributes){
		packageServer.setModifyTime(DateUtil.getCurrentTimespan());
		packageServer.setPrice(packageServer.getPrice()*100);
		packageServer.setVipPrice(packageServer.getVipPrice()*100);
		Boolean flag = packageServerService.updatePackage(packageServer, serverId);
		if(flag){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/bussiness/package/list";
	}
	
	/**
	 * 启用/未启用
	 * @param model
	 * @param serverId
	 * @param status
	 * @return
	 */
	@RequestMapping("enable/{packageId}/{status}")
	public @ResponseBody Integer isEnable(Model model,@PathVariable Integer packageId,@PathVariable Integer status){
		PackageServer packageServer = packageServerService.getPackageServerById(packageId);
		packageServer.setStatus(status);
		packageServer.setModifyTime(DateUtil.getCurrentTimespan());
		int count = packageServerService.updatePackageServer(packageServer);
		if(count > 0){
			return 1;//启停用成功
		}else{
			return 2;//失败
		}
	}
}
