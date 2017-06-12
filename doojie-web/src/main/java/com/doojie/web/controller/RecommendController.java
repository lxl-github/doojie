package com.doojie.web.controller;

import java.util.Map;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.domain.po.Recommend;
import com.doojie.domain.vo.RecommendVo;
import com.doojie.service.service.RecommendService;

@Controller
@RequestMapping("/system/recommend")
public class RecommendController {

	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("list")
	@RequiresPermissions("system/recommend/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue = "1") Integer currentPage,String name,Integer isTop){
		Page<Map<String,Object>> mapPage = new Page<Map<String,Object>>();
		
		mapPage.setCurrentPage(currentPage);
		
		Page<RecommendVo> page = recommendService.getRecommendAllPageList(mapPage, isTop,name);
		
		model.addAttribute("page",page);
		
		model.addAttribute("isTop", isTop);
		
		model.addAttribute("name", name);
		
		return "system/recommend/list";
	}
	
	@RequestMapping("isTop/{id}/{isTop}")
	public @ResponseBody Boolean isTop(@PathVariable Integer id,@PathVariable Integer isTop){
		Recommend recommend = recommendService.getRecommendById(id);
		recommend.setIsTop(isTop);
		recommend.setModifyTime(DateUtil.getCurrentTimespan());
		int count = recommendService.updateRecommend(recommend);
		return count > 0;
	}
}
