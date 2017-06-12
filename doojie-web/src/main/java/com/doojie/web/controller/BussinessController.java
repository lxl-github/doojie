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

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.constant.BussinessConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.MD5Util;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.vo.BussinessVo;
import com.doojie.service.service.BussinessService;
import com.doojie.shiro.util.SessionUtil;

@Controller
@RequestMapping("/system/bussiness")
public class BussinessController {

	@Autowired
	private BussinessService bussinessService;
	
	/**
	 * 所有商家列表 用于管理员查看
	 * @param model
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions("system/bussiness/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,String username,String name){
		Page<Bussiness> page = new Page<Bussiness>();
		
		page.setCurrentPage(currentPage);
		
		List<BussinessVo> bussinessList = bussinessService.getBussinessPageList(page,username,name);
		
		model.addAttribute("list",bussinessList);
		
		model.addAttribute("page",page);
		
		model.addAttribute("username", username);
		
		model.addAttribute("name", name);
		
		return "system/bussiness/list"; 
	}
	
	@RequestMapping("add")
	public String add(Model model){
		return "system/bussiness/add";
	}
	
	@RequestMapping("check")
	public @ResponseBody Boolean check(String username){
		Bussiness bussiness = bussinessService.getBussinessByuserName(username);
		return bussiness == null;
	}
	
	/**
	 * 添加商家 用于管理员
	 * @param model
	 * @param bussiness
	 * @return
	 */
	@RequestMapping("save")
	public String save(Model model,Bussiness bussiness,RedirectAttributes redirectAttributes,String location){
		String[] locationArr = location.split(",");
		bussiness.setLng(locationArr[0]);
		bussiness.setLat(locationArr[1]);
		bussiness.setStatus(BussinessConstant.NO_AUDIT);
		bussiness.setCreateTime(DateUtil.getCurrentTimespan());
		bussiness.setModifyTime(DateUtil.getCurrentTimespan());
		bussiness.setType(BussinessConstant.IS_BUSSINESS);
		bussiness.setPassword(MD5Util.getMD5(bussiness.getPassword()));
		bussiness.setArea(bussiness.getCity().concat("市"));
		int count = bussinessService.saveBussiness(bussiness);
		if(count > 0){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/bussiness/list";
	}
	
	/**
	 * 跳转编辑 用于管理员
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("edit/{bussinessId}")
	public String edit(Model model,@PathVariable Integer bussinessId){
		Bussiness bussiness = bussinessService.getBussinessById(bussinessId);
		
		model.addAttribute("bussiness", bussiness);
		
		return "system/bussiness/edit"; 
	}

	/**
	 * 修改商家信息 
	 * @param model
	 * @param bussiness
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model,Bussiness bussiness,RedirectAttributes redirectAttributes,String location){
		Bussiness bussinessSub = bussinessService.getBussinessById(bussiness.getId());
		bussinessSub.setModifyTime(DateUtil.getCurrentTimespan());
		if(bussiness.getPassword() != null){
			bussinessSub.setPassword(MD5Util.getMD5(bussiness.getPassword()));
		}
		bussinessSub.setAddress(bussiness.getAddress());
		bussinessSub.setBank(bussiness.getBank());
		bussinessSub.setBankAccount(bussiness.getBankAccount());
		bussinessSub.setBankAccountName(bussiness.getBankAccountName());
		bussinessSub.setCity(bussiness.getCity());
		bussinessSub.setProvince(bussiness.getProvince());
		bussinessSub.setCounty(bussiness.getCounty());
		bussinessSub.setName(bussiness.getName());
		bussinessSub.setTel(bussiness.getTel());
		bussinessSub.setRemark(bussiness.getRemark());
		bussinessSub.setLng(location.split(",")[0]);
		bussinessSub.setLat(location.split(",")[1]);
		bussinessSub.setUsername(bussiness.getUsername());
		bussinessSub.setArea(bussiness.getCity().concat("市"));
		bussinessSub.setIsAuthor(bussiness.getIsAuthor());
		int count = bussinessService.updateBussiness(bussinessSub);
		if(count > 0){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/bussiness/list";
	}
	
	/**
	 * 审核商家 用于管理员
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("audit/{bussinessId}")
	public @ResponseBody Boolean audit(Model model,@PathVariable Integer bussinessId){
		Bussiness bussiness = bussinessService.getBussinessById(bussinessId);
		bussiness.setStatus(BussinessConstant.AUDIT);
		bussiness.setModifyTime(DateUtil.getCurrentTimespan());
		int count = bussinessService.updateBussiness(bussiness);
		return count > 0;
	}
	
	/**
	 * 重置密码 用于管理员
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("restPass/{bussinessId}")
	public @ResponseBody Boolean restPass(Model model,@PathVariable Integer bussinessId){
		Bussiness bussiness = bussinessService.getBussinessById(bussinessId);
		bussiness.setPassword(MD5Util.getMD5(BaseConstant.DEFAULT_PASS));
		bussiness.setModifyTime(DateUtil.getCurrentTimespan());
		int count = bussinessService.updateBussiness(bussiness);
		return count > 0;
	}
	
	
	/**
	 * 商家基本信息
	 * @param model
	 * @return
	 */
	@RequestMapping("base/{bussinessId}")
	public String base(Model model,@PathVariable Integer bussinessId){
		Bussiness bussiness = bussinessService.getBussinessById(bussinessId);
		
		model.addAttribute("bussiness", bussiness);
		
		return "system/bussiness/base"; 
	}
	
//	@RequestMapping("login")
//	public String login(Model model){
//		Subject currentUser = SecurityUtils.getSubject();
//		if(currentUser.isAuthenticated()){
//			return "redirect:/main";
//		}else{
//			return "system/login";
//		}
//	}
//	
//	/**
//	 * ajax校验验证码
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("ajaxCheckVerifyCode")
//	public @ResponseBody
//	Boolean ajaxCheckCHVerifyCode(@RequestParam("verifyCode") String verifyCode) {
//		String rs = (String) SessionUtil.getAttribute("validateCode"); // 获取验证码
//		if (rs != null && rs.equalsIgnoreCase(verifyCode)) {
//			return true;
//		}
//		return false;
//	}
//	
//	@RequestMapping("doLogin")
//	public String doLogin(Model model,RedirectAttributes redirectAttributes,@RequestParam("userName") String userName,@RequestParam("userPwd") String userPwd){
//		Bussiness bussiness = bussinessService.getBussinessByuserName(userName);
//		
//		if(bussiness != null){
//			if(bussiness.getPassword().equals(MD5Util.getMD5(userPwd))){
//				DoojieUsernamePasswordToken token = new DoojieUsernamePasswordToken();
//				token.setUsername(bussiness.getUsername());
//				token.setPassword(bussiness.getPassword().toCharArray());
//				Subject currentUser = SecurityUtils.getSubject();
//				currentUser.login(token);
//				
//				BussinessDTO bussinessDTO = new BussinessDTO();
//				BeanUtils.copyProperties(bussiness, bussinessDTO);
//				
//				SessionUtil.setAttribute(BaseConstant.BUSSINESS,bussinessDTO);
//				SessionUtil.setAttribute(BaseConstant.BUSSINESS_ID,bussinessDTO.getId());
//				return "redirect:/main";//登录成功
//			}else {
//				redirectAttributes.addAttribute("flag",2);
//				return "redirect:/bussiness/login";//账户密码错误
//			}
//		}else{
//			redirectAttributes.addAttribute("flag",1);
//			return "redirect:/bussiness/login";//账户不存在
//		}
//	}
	
}
