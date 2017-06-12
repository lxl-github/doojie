package com.doojie.service.service;

import java.util.List;
import java.util.Map;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.vo.BussinessVo;

public interface BussinessService {

	List<BussinessVo> getBussinessPageList(Page<Bussiness> page, String username, String name);
	
	Bussiness getBussinessById(Integer bussinessId);
	
	Bussiness getBussinessByuserName(String userName);
	
	Integer saveBussiness(Bussiness bussiness);
	
	Integer updateBussiness(Bussiness bussiness);
	
	Integer deleteBussiness(Integer bussinessId);
	
	/**
	 * 根据区域和类型查询商家
	 * @param province
	 * @param city
	 * @param county
	 * @param type 0 距离   1点赞数
	 * @return
	 */
	List<BussinessVo> getBussinessList(Double lng, Double lat, String province, String city, Integer county, Integer type, String area);
	
	Page<BussinessVo> getBussinessPageList(Page<Bussiness> page, Double lng, Double lat, String province, String city, Integer county, Integer type, String area);
	
	/**
     * 查询所有被推荐的商家，根据推荐数倒序 
     * @return
     */
	Page<BussinessVo> getRecommendBussinessAllList(Integer currentPage,Double lng, Double lat,String area);
}
