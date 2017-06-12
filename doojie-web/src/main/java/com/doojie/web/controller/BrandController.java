package com.doojie.web.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.domain.po.Brand;
import com.doojie.domain.po.Models;
import com.doojie.service.service.BrandService;
import com.doojie.service.service.ModelsService;

@Controller
@RequestMapping("/system/brand")
public class BrandController {

	
	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ModelsService modelsService;
	
	@RequestMapping("list")
	@RequiresPermissions("system/brand/list")
	public String list(Model model){
		Page<Brand> page = new Page<Brand>();
		List<Brand> brandList = brandService.findBrands();
		page.setResults(brandList);
		
		page.setTotalPage(1);
		
		page.setTotalRecord(brandList.size());
		
		model.addAttribute("list",brandList);
		
		model.addAttribute("page", page);
		
		return "system/brand/list";
	}
	
	@RequestMapping("add")
	public String add(){
		return "system/brand/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String name,String origialName){
		if(origialName != null){
			if(name.equals(origialName)){
				return true;
			}
		}
		Brand brand = brandService.getBrandByName(name);
		return brand == null;
	}
	
	@RequestMapping("save")
	public String save(Brand brand,RedirectAttributes redirectAttributes,HttpServletRequest request){
		 //构建图片保存的目录
        String logoPathDir = "/static/brandLogo";
        //得到图片保存目录的真实路径
        String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
        //根据真实路径创建目录
        File logoSaveFile = new File(logoRealPathDir);
        if (!logoSaveFile.exists())
            logoSaveFile.mkdirs();
        
		//使用MultipartHttpServletRequest包装文件数据
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("logoFile");
     // 获取文件名/
        String filename = multipartFile.getOriginalFilename();
        long time = DateUtil.getCurrentTimespan(); // 时间
        int index = filename.lastIndexOf(".");
        //重命名
        filename = time + filename.substring(index);
        logger.info("新文件名："+filename);
        //拼成完整的文件保存路径加文件
        String fileName = logoRealPathDir + File.separator + filename;
        File file = new File(fileName);
        try{
        	multipartFile.transferTo(file);
        	logger.info(filename+"上传成功！！！");
        }catch (Exception e) {
        	logger.debug("上传品牌logo错误，{}",e.getLocalizedMessage());
        	redirectAttributes.addFlashAttribute("flag", "uploadFaile");
        	return "system/brand/add";
        }
       
		brand.setBrandLogo(filename);
		brand.setLogoPath(fileName);
		brand.setModifyTime(DateUtil.getCurrentTimespan());
		brand.setCreateTime(DateUtil.getCurrentTimespan());
		boolean f = brandService.saveBrand(brand);
		if(f){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/brand/list";
	}
	
	/**
	 * 跳转编辑页
	 * @param model
	 * @param brandId
	 * @return
	 */
	@RequestMapping("edit/{brandId}")
	public String edit(Model model,@PathVariable Integer brandId){
		Brand brand = brandService.getBrandById(brandId);
		model.addAttribute("brand", brand);
		return "system/brand/edit";
	}
	
	/**
	 * 修改品牌
	 * @param model
	 * @param brand
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model,Brand brand,RedirectAttributes redirectAttributes,HttpServletRequest request){
		
		//使用MultipartHttpServletRequest包装文件数据
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("logoFile");
        // 获取文件名/
        String filename = multipartFile.getOriginalFilename();
        if(filename != null && !filename.equals("")){
        	//先将原有logo图片删除
    		if(brand.getLogoPath() != null){
    			//删除logo图像
    			File imgFile = new File(brand.getLogoPath());
    			if(imgFile.exists()){
    				imgFile.delete();
    			}
    		}
    		
    		 //构建图片保存的目录
            String logoPathDir = "/static/brandLogo";
            //得到图片保存目录的真实路径
            String logoRealPathDir = request.getSession().getServletContext().getRealPath(logoPathDir);
            //根据真实路径创建目录
            File logoSaveFile = new File(logoRealPathDir);
            if (!logoSaveFile.exists())
                logoSaveFile.mkdirs();
            
    		
            long time = DateUtil.getCurrentTimespan(); // 时间
            int index = filename.lastIndexOf(".");
            //重命名
            filename = time + filename.substring(index);
            logger.info("新文件名："+filename);
            //拼成完整的文件保存路径加文件
            String newfileName = logoRealPathDir + File.separator + filename;
            File file = new File(newfileName);
            try{
            	multipartFile.transferTo(file);
            	logger.info(filename+"上传成功！！！");
            }catch (Exception e) {
            	logger.debug("上传品牌logo错误，{}",e.getLocalizedMessage());
            	redirectAttributes.addFlashAttribute("flag", "uploadFaile");
            	redirectAttributes.addAttribute("brandId", brand.getId());
            	return "system/brand/edit";
            }
    		//如果替换logo则修改
    		brand.setBrandLogo(filename);
    		brand.setLogoPath(newfileName);
        }
		
		brand.setModifyTime(DateUtil.getCurrentTimespan());
		boolean f = brandService.updateBrand(brand);
		if(f){
			redirectAttributes.addFlashAttribute("flag","true");
		}else {
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/brand/list";
	}
	
	/**
	 * 删除
	 * @param model
	 * @param brandId
	 * @return
	 */
	@RequestMapping("del/{brandId}")
	public @ResponseBody Integer isDeleted(Model model,@PathVariable Integer brandId){
		//删除之前检测是否存在型号，有则不可删除
		List<Models> modelsList = modelsService.findModelsByBrandId(brandId);
		if(modelsList != null && modelsList.size() > 0){
			logger.info("品牌id:{},存在型号",brandId);
			return 3;//存在型号
		}
		//查询当前品牌对象
		Brand brand = brandService.getBrandById(brandId);
		if(brand.getLogoPath() != null){
			//删除logo图像
			File imgFile = new File(brand.getLogoPath());
			if(imgFile.exists()){
				imgFile.delete();
			}
		}
		
		boolean f = brandService.deleteBrand(brandId);
		if(f){
			return 1;//删除成功
		}else{
			return 2;//失败
		}
	}
}
