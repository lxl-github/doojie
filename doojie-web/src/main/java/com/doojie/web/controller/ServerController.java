package com.doojie.web.controller;

import java.util.ArrayList;
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
import com.doojie.domain.po.BaseServer;
import com.doojie.domain.po.Server;
import com.doojie.domain.vo.ServerVo;
import com.doojie.service.service.PackageServerService;
import com.doojie.service.service.ServerService;
import com.doojie.shiro.util.SessionUtil;

@Controller
@RequestMapping("/bussiness/server")
public class ServerController {

	@Autowired
	private ServerService serverService;
	
	@Autowired
	private PackageServerService packageServerService;
	
	@RequestMapping("list")
	@RequiresPermissions("bussiness/server/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,String name,String status){
		Page<Server> page = new Page<Server>();
		
		page.setCurrentPage(currentPage);
		
		List<ServerVo> serverList = serverService.getServerByBussinessIdPageLsit(page,0,name,status);
		
		model.addAttribute("list",serverList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("name",name);
		
		model.addAttribute("status",status);
		
		return "system/server/list"; 
	}
	
	/**
	 * 跳转保存页面
	 * @return
	 */
	@RequestMapping("add")
	public String add(){
		return "system/server/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String name){
		Server server = serverService.getServerByName(name,0);
		return server == null;
	}
	
	/**
	 * 保存服务
	 * @param model
	 * @param server
	 * @return
	 */
	@RequestMapping("save")
	public String save(Model model,Server server,RedirectAttributes redirectAttributes){
		server.setPrice(server.getPrice()*100);
		server.setVipPrice(server.getVipPrice()*100);
		server.setStatus(ServerConstant.ENABLED);
		server.setCreateTime(DateUtil.getCurrentTimespan());
		server.setBussinessId(0);
		server.setModifyTime(DateUtil.getCurrentTimespan());
		int count = serverService.saveServer(server);
		if(count > 0){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/bussiness/server/list";
	}
	
	/**
	 * 启用/未启用
	 * @param model
	 * @param serverId
	 * @param status
	 * @return
	 */
	@RequestMapping("del/{serverId}")
	public @ResponseBody Integer del(Model model,@PathVariable Integer serverId){
		//判断是否在套餐使用中
		Boolean isExists = packageServerService.isExistsPackageRelServerByServerId(serverId);
		if(isExists){
			return 3;//服务正在套餐中使用不得删除
		}
		int count = serverService.deleteServer(serverId);
		if(count > 0){
			return 1;//删除成功
		}else{
			return 2;//删除失败
		}
	}
	
	/**
	 * 跳转编辑页
	 * @param model
	 * @param serverId
	 * @return
	 */
	@RequestMapping("edit/{serverId}")
	public String edit(Model model,@PathVariable Integer serverId){
		Server server = serverService.getServerById(serverId);
		server.setPrice(server.getPrice()/100);
		server.setVipPrice(server.getVipPrice()/100);
		model.addAttribute("server", server);
		return "system/server/edit";
	}
	
	
	/**
	 * 修改服务
	 * @param model
	 * @param server
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model,Server server,RedirectAttributes redirectAttributes){
		server.setModifyTime(DateUtil.getCurrentTimespan());
		server.setPrice(server.getPrice()*100);
		server.setVipPrice(server.getVipPrice()*100);
		int count = serverService.updateServer(server);
		if(count > 0){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/bussiness/server/list";
	}
	
	/**
	 * 启用/未启用
	 * @param model
	 * @param serverId
	 * @param status
	 * @return
	 */
	@RequestMapping("enable/{serverId}/{status}")
	public @ResponseBody Integer isEnable(Model model,@PathVariable Integer serverId,@PathVariable Integer status){
		//如果等于停用 则判断是否在套餐使用中
		if(ServerConstant.ENABLED == status){
			Boolean isExists = packageServerService.isExistsPackageRelServerByServerId(serverId);
			if(isExists){
				return 3;//服务正在套餐中使用不得停用
			}
		}
		Server server = serverService.getServerById(serverId);
		server.setStatus(status);
		server.setModifyTime(DateUtil.getCurrentTimespan());
		int count = serverService.updateServer(server);
		if(count > 0){
			return 1;//启停用成功
		}else{
			return 2;//失败
		}
	}
	
	@RequestMapping("base")
	@RequiresPermissions("bussiness/server/base")
	public String base(Model model){
		//根据商家id查询基础洗车服务
		List<BaseServer> baseServerList = serverService.getBaseServerByBussinessId(0);
		Integer[] ids =new Integer[3];
		Integer[] prices = new Integer[3];
		Integer[] vipPrices = new Integer[3];
		Integer[] isShow = {0,0,0};
		if(baseServerList !=null && baseServerList.size() > 0){
			for (int i = 0;i < baseServerList.size();i++) {
					BaseServer baseServer = baseServerList.get(i);
					ids[i] = baseServer.getId();
					prices[i] = baseServer.getPrice()/100;
					vipPrices[i] = baseServer.getVipPrice()/100;
					isShow[i] = baseServer.getIsShow();
			}
		}
		
		model.addAttribute("ids",ids);
		model.addAttribute("prices",prices);
		model.addAttribute("vipPrices",vipPrices);
		model.addAttribute("isShow",isShow);
		return "system/server/base";
	}
	
	@RequestMapping("baseSave")
	public String baseSave(Integer[] id,Integer[] carType,Integer[] price,Integer[] vipPrice,String[] isShow,Integer type,RedirectAttributes redirectAttributes){
		Integer[] isShowArr = {0,0,0};
		if(isShow != null){
			for (int i = 0; i < isShow.length; i++) {
				//防止不知道更新的是哪个车型的是否显示，增加了(位置固定+是否显示)的数组
				char isPoint = isShow[i].charAt(0);
				char isShows = isShow[i].charAt(1);
				isShowArr[Integer.valueOf(String.valueOf(isPoint))] = Integer.valueOf(String.valueOf(isShows));
			}
		}
		int count = 0;
		//type == 0 ? 保存 ：修改
		if(type == null){
			List<BaseServer> baseServerList = new ArrayList<BaseServer>();
			for(int i = 0;i < 3;i++){
				BaseServer baseServer = new BaseServer();
				baseServer.setBussinessId(0);
				baseServer.setCarType(carType[i]);
				baseServer.setIsShow(isShowArr[i]);
				Integer prices = price[i];
				if (prices != null) {
					baseServer.setPrice(prices*100);
				}
				Integer vPrice = vipPrice[i]; 
				if (vPrice != null) {
					baseServer.setVipPrice(vPrice*100);
				}
				baseServerList.add(baseServer);
			}
			count = serverService.saveBaseServer(baseServerList);
		}else{
			List<BaseServer> baseServerList = new ArrayList<BaseServer>();
			for(int i = 0;i < 3;i++){
				BaseServer baseServer = new BaseServer();
				baseServer.setId(id[i]);
				baseServer.setBussinessId(0);
				baseServer.setCarType(carType[i]);
				baseServer.setIsShow(isShowArr[i]);
				Integer prices = price[i];
				if (prices != null) {
					baseServer.setPrice(prices*100);
				}
				Integer vPrice = vipPrice[i]; 
				if (vPrice != null) {
					baseServer.setVipPrice(vPrice*100);
				}
				baseServerList.add(baseServer);
			}
			count = serverService.updateBaseServer(baseServerList);
		}
		
		if(count > 0){
			redirectAttributes.addFlashAttribute("flag","true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/bussiness/server/base";
	}
	
//	@RequestMapping("baseSave")
//	public String baseSave(List<BaseServer> baseServerList,Integer type,RedirectAttributes redirectAttributes){
//		int count = 0;
//		//type == 0 ? 保存 ：修改
//		if(type == 0){
//			for(BaseServer baseServer :baseServerList){
//				baseServer.setBussinessId(SessionUtil.getCurrentBussinessId());
//				baseServerList.add(baseServer);
//			}
//			count = serverService.saveBaseServer(baseServerList);
//		}else{
//			count = serverService.updateBaseServer(baseServerList);
//		}
//		
//		if(count > 0){
//			redirectAttributes.addFlashAttribute("flag","true");
//		}else{
//			redirectAttributes.addFlashAttribute("flag","false");
//		}
//		return "redirect:/server/base";
//	}
}
