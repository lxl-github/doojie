package com.doojie.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.constant.ProductConstant;
import com.doojie.common.constant.RegionConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.RandomUtil;
import com.doojie.domain.po.Models;
import com.doojie.domain.po.Product;
import com.doojie.domain.po.Region;
import com.doojie.domain.vo.ProductVo;
import com.doojie.service.service.ProductService;
import com.doojie.service.service.RegionService;
import com.doojie.shiro.util.SessionUtil;

@Controller
@RequestMapping("/system/product")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private RegionService regionService;

	@RequestMapping("list")
	@RequiresPermissions("system/product/list")
	public String list(Model model, @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
			Integer type, Integer category) {
		Page<Product> page = new Page<Product>();

		page.setCurrentPage(currentPage);

		List<ProductVo> productVoList = productService.getProductPageList(page, type, category);

		model.addAttribute("list", productVoList);

		model.addAttribute("page", page);

		model.addAttribute("type", type);

		model.addAttribute("category", category);

		return "system/product/list";
	}

	@RequestMapping("add")
	public String add(Model model) {
		List<Region> regionList = regionService.getRegionByParentId(RegionConstant.PARAENT_ID);
		model.addAttribute("regionList",regionList);
		// 加载前生成商品编码(当前日期+六位随机数)
		String productCode = DateUtil.getDate().concat(RandomUtil.random(6));
		model.addAttribute("productCode", productCode);
		return "system/product/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String name,String origialName){
		//判断原名是否相同
		if(origialName != null){
			if(name.equals(origialName)){
				return true;
			}
		}
		
		Product product = productService.getProductByName(name);
		return product == null;
	}

	/**
	 * 保存商品
	 * <br>
	 * 创建时间：2015年6月3日下午2:44:57
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param product
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping("save")
	public String save(Product product, RedirectAttributes redirectAttributes) {
		product.setPrice(product.getPrice()*100);
//		product.setDiscountPrice(product.getDiscountPrice()*100);
		product.setCreateTime(DateUtil.getCurrentTimespan());
		product.setBussinessId(0);
		product.setStatus(ProductConstant.NO_DELETE);
		product.setIsShow(ProductConstant.NOT_IS_SHOW);
		product.setModifyTime(DateUtil.getCurrentTimespan());
		boolean f = productService.saveProduct(product);
		if (f) {
			redirectAttributes.addFlashAttribute("flag", "true");
		} else {
			redirectAttributes.addFlashAttribute("flag", "false");
		}
		return "redirect:/system/product/list";
	}

	/**
	 * 跳转编辑页
	 * 
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("edit/{productId}")
	public String edit(Model model, @PathVariable Integer productId) {
		List<Region> regionList = regionService.getRegionByParentId(RegionConstant.PARAENT_ID);
		model.addAttribute("regionList",regionList);
		Product product = productService.getProductById(productId);
		product.setPrice(product.getPrice()/100);
		product.setDiscountPrice(product.getDiscountPrice()/100);
		model.addAttribute("product", product);
		return "system/product/edit";
	}

	/**
	 * 修改商品
	 * 
	 * @param model
	 * @param product
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, Product product, RedirectAttributes redirectAttributes) {
		product.setPrice(product.getPrice()*100);
//		product.setDiscountPrice(product.getDiscountPrice()*100);
		product.setModifyTime(DateUtil.getCurrentTimespan());
		try{
			boolean f = productService.updateProduct(product);
			if (f) {
				redirectAttributes.addFlashAttribute("flag", "true");
			} else {
				redirectAttributes.addFlashAttribute("flag", "false");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("updateProduct exception :{}",e.getLocalizedMessage());
			redirectAttributes.addFlashAttribute("flag", "false");
		}
		return "redirect:/system/product/list";
	}

	/**
	 * 是否删除
	 * <br>
	 * 创建时间：2015年6月3日下午2:44:13
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param productId
	 * @return
	 *
	 */
	@RequestMapping("del/{productId}")
	public @ResponseBody Integer isDeleted(Model model, @PathVariable Integer productId) {
		boolean f = productService.updateProductIsDeleted(ProductConstant.DELETE, productId);
		if (f) {
			return 1;// 删除成功
		} else {
			return 2;// 失败
		}
	}
	
	/**
	 * 是否上架
	 * <br>
	 * 创建时间：2015年6月3日下午2:44:02
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param model
	 * @param productId
	 * @param isShow
	 * @return
	 *
	 */
	@RequestMapping("enable/{productId}/{isShow}")
	public @ResponseBody Integer isShow(Model model, @PathVariable Integer productId,@PathVariable Integer isShow) {
		boolean f = productService.updateProductIsShow(isShow,productId);
		if (f) {
			return 1;// 操作成功
		} else {
			return 2;// 失败
		}
	}

}
