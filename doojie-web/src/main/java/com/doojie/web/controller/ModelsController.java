package com.doojie.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.domain.po.Models;
import com.doojie.service.service.ModelsService;

@Controller
@RequestMapping("/system/models")
public class ModelsController {

	
	private static final Logger logger = LoggerFactory.getLogger(ModelsController.class);
	
	@Autowired
	private ModelsService modelsService;
	
	@RequestMapping("list")
	public String list(Model model,Integer brandId){
		Page<Models> page = new Page<Models>();
		
		List<Models> modelsList = modelsService.findModelsByBrandId(brandId);
		
		page.setTotalPage(1);
		
		page.setTotalRecord(modelsList.size());
		
		page.setResults(modelsList);
		
		model.addAttribute("list",modelsList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("brandId",brandId);
		
		return "system/models/list";
	}
	
	@RequestMapping("add")
	public String add(Model model,String brandId){
		model.addAttribute("brandId",brandId);
		return "system/models/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String name,Integer brandId,String originalName){
		//判断原名是否相同
		if(originalName != null){
			if(name.equals(originalName)){
				return true;
			}
		}
		
		Models models = modelsService.getModelsByName(name,brandId);
		return models == null;
	}
	
	@RequestMapping("save")
	public String save(RedirectAttributes redirectAttributes,Models models){
		models.setCreateTime(DateUtil.getCurrentTimespan());
		models.setModifyTime(DateUtil.getCurrentTimespan());
		boolean f = modelsService.saveModel(models);
		if(f){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		redirectAttributes.addAttribute("brandId",models.getBrandId());
		return "redirect:/system/models/list";
	}
	
	/**
	 * 跳转编辑页
	 * @param model
	 * @param modelsId
	 * @return
	 */
	@RequestMapping("edit/{modelsId}")
	public String edit(Model model,@PathVariable Integer modelsId){
		Models models = modelsService.getModelsById(modelsId);
		model.addAttribute("models", models);
		return "system/models/edit";
	}
	
	/**
	 * 修改型号
	 * @param model
	 * @param models
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model,Models models,RedirectAttributes redirectAttributes){
		models.setModifyTime(DateUtil.getCurrentTimespan());
		boolean f = modelsService.updateModel(models);
		if(f){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		redirectAttributes.addAttribute("brandId",models.getBrandId());
		return "redirect:/system/models/list";
	}
	
	/**
	 * 删除
	 * @param model
	 * @param brandId
	 * @return
	 */
	@RequestMapping("del/{modelsId}")
	public @ResponseBody Integer isDeleted(Model model,@PathVariable Integer modelsId){
		boolean f = modelsService.deleteModel(modelsId);
		if(f){
			return 1;//删除成功
		}else{
			return 2;//失败
		}
	}
}
