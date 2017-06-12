package com.doojie.service.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.util.Comparators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.DistanceUtil;
import com.doojie.dao.BussinessMapper;
import com.doojie.domain.po.Bussiness;
import com.doojie.domain.vo.BussinessVo;
import com.doojie.domain.vo.RecommendVo;
import com.doojie.service.service.BussinessService;
@Service
public class BussinessServiceImpl implements BussinessService {
	
	private static final Logger logger = LoggerFactory.getLogger(BussinessServiceImpl.class);

	@Autowired
	private BussinessMapper bussinessMapper;
	
	
	@Override
	public List<BussinessVo> getBussinessPageList(Page<Bussiness> page,String username,String name) {
		List<Bussiness> bussinesseList = bussinessMapper.selectBussinessAllPageList(page,username,name);
		List<BussinessVo> bussinessVoList = null;
		if(bussinesseList.size() > 0){
			bussinessVoList = new ArrayList<BussinessVo>();
			for (Bussiness bussiness : bussinesseList) {
				BussinessVo bussinessVo = new BussinessVo();
				bussinessVo.setBussiness(bussiness);
				bussinessVo.setCreateDate(DateUtil.getDatetime(bussiness.getCreateTime()));
				bussinessVo.setModifyDate(DateUtil.getDatetime(bussiness.getModifyTime()));
				bussinessVoList.add(bussinessVo);
			}
			page.setResults(bussinesseList);
		}
		
		return bussinessVoList;
	}

	@Override
	public Bussiness getBussinessById(Integer bussinessId) {
		return bussinessMapper.selectByPrimaryKey(bussinessId);
	}

	@Override
	public Bussiness getBussinessByuserName(String userName) {
		return bussinessMapper.selectBussinessByuserName(userName);
	}

	@Override
	public Integer saveBussiness(Bussiness bussiness) {
		try{
			int count = bussinessMapper.insert(bussiness);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer updateBussiness(Bussiness bussiness) {
		try{
			int count = bussinessMapper.updateByPrimaryKeySelective(bussiness);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer deleteBussiness(Integer bussinessId) {
		try{
			int count = bussinessMapper.deleteByPrimaryKey(bussinessId);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public List<BussinessVo> getBussinessList(Double lng,Double lat,String province, String city, Integer county, Integer type,String area) {
		List<Bussiness> bussinessList = bussinessMapper.selectBussinessAllList(province, city, county,area);
		
		List<BussinessVo> bussinessVoList = null ;
		
		if(bussinessList != null && bussinessList.size() > 0){
			bussinessVoList = new ArrayList<BussinessVo>();
			for(Bussiness bussiness : bussinessList){
				BussinessVo bussinessVo = new BussinessVo();
				bussinessVo.setBussiness(bussiness);
				double distance = DistanceUtil.getDistance(lng,lat,new Double(bussiness.getLng()),new Double(bussiness.getLat()));
				DecimalFormat df = new DecimalFormat("#.00");
				bussinessVo.setDistance(new Double(df.format(distance)));
				bussinessVoList.add(bussinessVo);
			}
			//根据距离排序
			if(type == 0){
				Collections.sort(bussinessVoList, new Comparator<BussinessVo>() {
		            public int compare(BussinessVo arg0, BussinessVo arg1) {
		            	double dis1 = arg0.getDistance();
		        		double dis2 = arg1.getDistance();
		        		if(dis1 > dis2){
		        			return 1;
		        		}else if(dis1 < dis2){
		        			return -1;
		        		}else{
		        			return 0;
		        		}
		            }
		        });
			}
			
			return bussinessVoList;
		}
		
		return null;
	}

	@Override
	public Page<BussinessVo> getBussinessPageList(Page<Bussiness> page, Double lng, Double lat, String province,
			String city, Integer county, Integer type, String area) {
		Page<BussinessVo> bussinessVoPage = new Page<BussinessVo>();
		//获取所有数据
		List<BussinessVo> bussinessVos = getBussinessList(lng,lat,province,city,county,type,area);
		//获取分页数
		bussinessMapper.selectBussinessAllsPageList(page,province, city, county,area);
		bussinessVoPage.setCurrentPage(page.getCurrentPage());
		bussinessVoPage.setPageSize(page.getPageSize());
		bussinessVoPage.setTotalPage(page.getTotalPage());
		bussinessVoPage.setTotalRecord(page.getTotalRecord());
		bussinessVoPage.setResults(bussinessVos);
		
//		List<BussinessVo> bussinessVoList = null ;
//		
//		if(bussinessList != null && bussinessList.size() > 0){
//			bussinessVoList = new ArrayList<BussinessVo>();
//			for(Bussiness bussiness : bussinessList){
//				BussinessVo bussinessVo = new BussinessVo();
//				bussinessVo.setBussiness(bussiness);
//				double distance = DistanceUtil.getDistance(lng,lat,new Double(bussiness.getLng()),new Double(bussiness.getLat()));
//				DecimalFormat df = new DecimalFormat("#.00");
//				bussinessVo.setDistance(new Double(df.format(distance)));
//				bussinessVoList.add(bussinessVo);
//			}
//			//根据距离排序
//			if(type == 0){
//				Collections.sort(bussinessVoList, new Comparator<BussinessVo>() {
//		            public int compare(BussinessVo arg0, BussinessVo arg1) {
//		            	double dis1 = arg0.getDistance();
//		        		double dis2 = arg1.getDistance();
//		        		if(dis1 > dis2){
//		        			return 1;
//		        		}else if(dis1 < dis2){
//		        			return -1;
//		        		}else{
//		        			return 0;
//		        		}
//		            }
//		        });
//			}
//			bussinessVoPage.setResults(bussinessVoList);
//			return bussinessVoPage;
//		}
		return bussinessVoPage;
	}

	@Override
	public Page<BussinessVo> getRecommendBussinessAllList(Integer currentPage,Double lng, Double lat,String area) {
		Page<BussinessVo> bussinessVoPage = new Page<BussinessVo>();
		List<Map<String,Object>> list = bussinessMapper.selectRecommendBussinessAllList(area);
		getBussinessVoCommon(bussinessVoPage,list,currentPage,lng,lat);
		return bussinessVoPage;
	}

	/**
	 * 获取推荐商家信息公用方法
	 * @param page
	 * @param bussinessVoPage
	 * @param list
	 */
	private void getBussinessVoCommon(Page<BussinessVo> bussinessVoPage,
			List<Map<String, Object>> list,Integer currentPage,Double lng, Double lat) {
		if(list != null && list.size() > 0){
			List<BussinessVo> bussinessVoList = new ArrayList<BussinessVo>();
			for(Map<String,Object> map : list){
				BussinessVo bussinessVo = new BussinessVo();
				Bussiness bussiness = new Bussiness();
				for(Entry<String,Object> entry : map.entrySet()){
					if("id".equals(entry.getKey())){
						bussiness.setId((Integer)entry.getValue());
					}
					if("tel".equals(entry.getKey())){
						bussiness.setTel((String)entry.getValue());
					}
					if("name".equals(entry.getKey())){
						bussiness.setName((String)entry.getValue());
					}
					if("address".equals(entry.getKey())){
						bussiness.setAddress((String)entry.getValue());
					}
					if("lat".equals(entry.getKey())){
						bussiness.setLat((String)entry.getValue());
					}
					if("lng".equals(entry.getKey())){
						bussiness.setLng((String)entry.getValue());
					}
					if("city".equals(entry.getKey())){
						bussiness.setCity((String)entry.getValue());
					}
					if("createTime".equals(entry.getKey())){
						bussinessVo.setCreateDate(DateUtil.getDatetime((Integer)entry.getValue()));
					}
					if("area".equals(entry.getKey())){
						bussiness.setArea((String)entry.getValue());
					}
					if("recommendCount".equals(entry.getKey())){
						bussinessVo.setRecommendCount(Integer.valueOf(String.valueOf(entry.getValue())));
					}
					if("isAuthor".equals(entry.getKey())){
						bussiness.setIsAuthor(Integer.valueOf(String.valueOf(entry.getValue())));
					}
				}
				double distance = DistanceUtil.getDistance(lng,lat,new Double(bussiness.getLng()),new Double(bussiness.getLat()));
				DecimalFormat df = new DecimalFormat("#.00");
				bussinessVo.setDistance(new Double(df.format(distance)));
				bussinessVo.setBussiness(bussiness);
				bussinessVoList.add(bussinessVo);
			}
			bussinessVoPage.setCurrentPage(currentPage);
			bussinessVoPage.setTotalRecord(list.size());
			bussinessVoPage.setResults(bussinessVoList);
		}
	}
}
