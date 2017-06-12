package com.doojie.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.doojie.common.constant.BaseConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.MD5Util;
import com.doojie.domain.po.Employee;
import com.doojie.domain.po.EmployeeWorkStatus;
import com.doojie.domain.po.Region;
import com.doojie.domain.vo.EmployeeVo;
import com.doojie.domain.vo.EmployeeWorkRecordVo;
import com.doojie.service.service.EmployeeService;
import com.doojie.service.service.EmployeeWorkRecordService;
import com.doojie.service.service.EmployeeWorkStatusService;
import com.doojie.service.service.RegionService;

@Controller
@RequestMapping("system/employee")
public class EmployeeController {

	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeWorkStatusService employeeWorkStatusService;
	
	@Autowired
	private EmployeeWorkRecordService employeeWorkRecordService;
	
	@Autowired
	private RegionService regionService;
	
	@RequestMapping("list")
	@RequiresPermissions("system/employee/list")
	public String list(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,String number){
		if("".equals(number)){
			number = null;
		}
		
		Page<Employee> page = new Page<Employee>();
		
		page.setCurrentPage(currentPage);
		
		List<EmployeeVo> employeeVoList = employeeService.getEmployeePageList(page,number);
		
		model.addAttribute("list", employeeVoList);
		
		model.addAttribute("page", page);
		
		model.addAttribute("number", number);
		
		return "system/employee/list";
	}
	
	@RequestMapping("add")
	public String add(){
		return "system/employee/add";
	}
	
	@RequestMapping("save")
	public String save(Employee employee,RedirectAttributes redirectAttributes,String entryDate){
		employee.setCreateTime(DateUtil.getCurrentTimespan());
		employee.setModifyTime(DateUtil.getCurrentTimespan());
		employee.setEntryTime(DateUtil.getTimespan2(entryDate));
		boolean f = employeeService.saveEmployee(employee);
		if(f){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/employee/list";
	}
	
	@RequestMapping("edit/{employeeId}")
	public String edit(Model model,@PathVariable Integer employeeId){
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		model.addAttribute("employee", employee);
		
		model.addAttribute("entryDate",DateUtil.getDatetime1(employee.getEntryTime()));
		
		return "system/employee/edit"; 
	}
	
	@RequestMapping("update")
	public String update(Model model,Employee employee,RedirectAttributes redirectAttributes,String entryDate){
		employee.setModifyTime(DateUtil.getCurrentTimespan());
		employee.setEntryTime(DateUtil.getTimespan2(entryDate));
		boolean f = employeeService.updateEmployee(employee);
		if(f){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/employee/list";
	}
	

	
	/**
	 * 开通账户
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("openAccount/{employeeId}/{option}")
	public @ResponseBody Integer openAccount(Model model,@PathVariable Integer employeeId,@PathVariable Integer option){
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		if(option == 1){
			if(employee.getStatus() == BaseConstant.EMPLOYEE_STATUS_LZ){
				return 3; //离职状态不可开通账户
			}
			employee.setPassword(MD5Util.getMD5(BaseConstant.DEFAULT_PASS));
		}else{
			EmployeeWorkStatus employeeWorkStatus = employeeWorkStatusService.getEmployeeWorkStatusByEmployeeId(employeeId);
			if(employeeWorkStatus != null){
				if(employeeWorkStatus.getIsEnabled() == 1){
					return 4;////判断工作状态是否为启用，启用则不能进行禁用账户，必须停用工作状态
				}else{
					employee.setPassword("-1");
				}
			}else{
				employee.setPassword("-1");
			}
		}
		boolean f = employeeService.updateEmployee(employee);
		if(f){
			return 1;
		}else{
			return 2;
		}
	}
	
	
	/**
	 * 跳转设置员工工作状态
	 * <br>
	 * 创建时间：2015年6月12日下午5:49:05
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param employeeId
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("toSetWorkStatus/{employeeId}")
	public String toSetWorkStatus(@PathVariable Integer employeeId,Model model){
		Employee employee = employeeService.getEmployeeById(employeeId);
		EmployeeWorkStatus employeeWorkStatus = employeeWorkStatusService.getEmployeeWorkStatusByEmployeeId(employeeId);
		List<Region> regionList = regionService.getRegionListByLevel(3);
		model.addAttribute("regionList", regionList);
		model.addAttribute("employee",employee);
		model.addAttribute("employeeWorkStatus",employeeWorkStatus);
		return "system/employee/workStatus";
	}
	
	/**
	 * 开始设置工作状态
	 * <br>
	 * 创建时间：2015年6月12日下午5:50:50
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param employeeWorkStatus
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequestMapping("setWorkStatus")
	public String setWorkStatus(EmployeeWorkStatus employeeWorkStatus,RedirectAttributes redirectAttributes){
		if(employeeWorkStatus.getIsEnabled() == null){
			employeeWorkStatus.setIsEnabled(0);
		}
		boolean f = true;
		if(employeeWorkStatus.getId() == null){
			f = employeeWorkStatusService.saveEmployeeWorkStatus(employeeWorkStatus);
		}else{
			f = employeeWorkStatusService.updateEmployeeWorkStatus(employeeWorkStatus);
		}
		
		if(f){
			redirectAttributes.addFlashAttribute("flag", "true");
		}else{
			redirectAttributes.addFlashAttribute("flag","false");
		}
		return "redirect:/system/employee/toSetWorkStatus/"+employeeWorkStatus.getEmployeeId();
	}
	
	
	@RequestMapping("workRecordList")
	@RequiresPermissions("system/employee/workRecordList")
	public String workRecordList(Model model,@RequestParam(value="currentPage",defaultValue="1") Integer currentPage,String number){
		if("".equals(number)){
			number = null;
		}
		
		Page<EmployeeWorkRecordVo> page = new Page<EmployeeWorkRecordVo>();
		
		page.setCurrentPage(currentPage);
		
		List<EmployeeWorkRecordVo> employeeWorkRecordVoList = employeeWorkRecordService.getEmployeeWorkRecordVoPageList(page, number);
		
		model.addAttribute("list", employeeWorkRecordVoList);
		
		model.addAttribute("page", page);
		
		model.addAttribute("number", number);
		
		return "system/employee/workRecordList";
	}
}
