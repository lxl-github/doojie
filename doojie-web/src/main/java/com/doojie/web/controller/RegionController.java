package com.doojie.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.constant.RegionConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.domain.po.Region;
import com.doojie.service.service.RegionService;

@Controller
@RequestMapping("/system/region")
public class RegionController {

	
	private static final Logger logger = LoggerFactory.getLogger(RegionController.class);
	
	@Autowired
	private RegionService regionService;
	
	@RequestMapping("list")
	@RequiresPermissions("system/region/list")
	public String list(Model model,Integer parentId){
		
		if(parentId == null){
			parentId = 0;
		}
		
		Page<Region> page = new Page<Region>();
		List<Region> regionList = regionService.getRegionByParentId(parentId);
		page.setResults(regionList);
		
		page.setTotalPage(1);
		
		page.setTotalRecord(regionList.size());
		
		model.addAttribute("list",regionList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("parentId", parentId);
		
		return "system/region/list"; 
	}
	
	@RequestMapping("add")
	public String add(Model model,Integer parentId){
		if(parentId != null && parentId != 0){
 			Region region = regionService.getRegionById(parentId);
 			model.addAttribute("regionName", region.getName());
 			model.addAttribute("level", region.getLevel());
		}else{
			parentId = 0;
		}
		model.addAttribute("parentId",parentId);
		return "system/region/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String name,Integer parentId,String originalName){
		//修改时检测是否是更改的原名
		if(originalName != null){
			if(originalName.equals(name)){
				return true;
			}
		}
		Region region = regionService.getRegionByName(name,parentId);
		return region == null;
	}
	
	@RequestMapping("check/code")
	public @ResponseBody Boolean checkCode(String code,String originalCode){
		//修改时检测是否是更改的原名
		if(originalCode != null){
			if(originalCode.equals(code)){
				return true;
			}
		}
		Region region = regionService.getRegionByCode(code);
		return region == null;
	}
	
	@RequestMapping("save")
	public String save(Region region,RedirectAttributes redirectAttributes){
		region.setIsDeleted(0);
		region.setIsEnabled(0);
		region.setModifyTime(DateUtil.getCurrentTimespan());
		region.setCreateTime(DateUtil.getCurrentTimespan());
		boolean f = regionService.saveRegion(region);
		if(f){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		redirectAttributes.addAttribute("parentId",region.getParentId());
		return "redirect:/system/region/list";
	}
	
	/**
	 * 跳转编辑页
	 * @param model
	 * @param serverId
	 * @return
	 */
	@RequestMapping("edit/{regionId}")
	public String edit(Model model,@PathVariable Integer regionId){
		Region region = regionService.getRegionById(regionId);
		Region parentRegion = regionService.getRegionById(region.getParentId());
		model.addAttribute("region", region);
		if(parentRegion != null){
			model.addAttribute("regionName", parentRegion.getName());
		}
		
		return "system/region/edit";
	}
	
	
	/**
	 * 修改区域
	 * @param model
	 * @param region
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model,Region region,RedirectAttributes redirectAttributes){
		region.setModifyTime(DateUtil.getCurrentTimespan());
		boolean f = regionService.updateRegion(region);
		if(f){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		redirectAttributes.addAttribute("parentId",region.getParentId());
		return "redirect:/system/region/list";
	}
	
	/**
	 * 启用/未启用
	 * @param model
	 * @param regionId
	 * @param isEnabled
	 * @return
	 */
	@RequestMapping("enable/{regionId}/{isEnabled}")
	public @ResponseBody Integer isEnable(Model model,@PathVariable Integer regionId,@PathVariable Integer isEnabled){
		Region region = regionService.getRegionById(regionId);
		region.setIsEnabled(isEnabled);
		region.setModifyTime(DateUtil.getCurrentTimespan());
		boolean f = regionService.updateRegion(region);
		if(f){
			return 1;//启停用成功
		}else{
			return 2;//失败
		}
	}
	
	/**
	 * 删除
	 * @param model
	 * @param regionId
	 * @return
	 */
	@RequestMapping("del/{regionId}")
	public @ResponseBody Integer isDeleted(Model model,@PathVariable Integer regionId){
		List<Region> regionList = new ArrayList<Region>();
		regionService.getRegionByParentId(regionId, regionList);
		//删除之前检测是否存在下级有启用的区域，有则不可删除
		boolean isExists = false;
		if(regionList.size() > 0){
			for (Region region : regionList) {
				if(region.getIsEnabled() == RegionConstant.ENABLE){
					isExists = true;
					break;
				}
			}
		}
		if(isExists){
			logger.info("区域id:{},存在下级启用的区域",regionId);
			return 3;//存在启用的下级区域
		}
		
		//查询当前区域对象
		Region region = regionService.getRegionById(regionId);
		regionList.add(region);
		boolean f = regionService.updateRegionIsDeleted(RegionConstant.DELETE,regionList);
		if(f){
			return 1;//删除成功
		}else{
			return 2;//失败
		}
	}
}
