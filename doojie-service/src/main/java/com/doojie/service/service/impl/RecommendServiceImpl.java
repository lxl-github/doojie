package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.dao.OrderMapper;
import com.doojie.dao.RecommendMapper;
import com.doojie.domain.po.Recommend;
import com.doojie.domain.vo.RecommendVo;
import com.doojie.service.service.RecommendService;

@Service
public class RecommendServiceImpl implements RecommendService {

	
	private static final Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);
	
	@Autowired
	private RecommendMapper recommendMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public Integer saveRecommend(Recommend recommend) {
		int count = 0;
		if(recommend.getId() != null){
			count = recommendMapper.updateByPrimaryKey(recommend);
		}else{
			count = recommendMapper.insert(recommend);
			orderMapper.updateIsRecommendById(recommend.getOrderId());
		}
		return count;
	}

	@Override
	public Integer updateRecommend(Recommend recommend) {
		try{
			int count = recommendMapper.updateByPrimaryKey(recommend);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer deleteRecommend(Integer recommendId) {
		try{
			int count = recommendMapper.deleteByPrimaryKey(recommendId);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}
	
	@Override
	public Recommend getRecommendById(Integer id) {
		return recommendMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<RecommendVo> getRecommendByUserIdPageList(Page<Map<String, Object>> page, Integer userId) {
		Page<RecommendVo> recommendVoPage = new Page<RecommendVo>();
		List<Map<String,Object>> list = recommendMapper.selectRecommendByUserIdPageList(page, userId);
		getRecommendVoCommon(page, recommendVoPage, list);
		return recommendVoPage;
	}


	@Override
	public Page<RecommendVo> getRecommendByBussinessIdPageList(Page<Map<String, Object>> page, Integer bussinessId,Integer isOnlyOne) {
		Page<RecommendVo> recommendVoPage = new Page<RecommendVo>();
		if (isOnlyOne != null) {
			page.setPageSize(1);
		}
		List<Map<String,Object>> list = recommendMapper.selectRecommendByBussinessIdPageList(page, bussinessId);
		getRecommendVoCommon(page, recommendVoPage, list);
		return recommendVoPage;
	}

	@Override
	public Page<RecommendVo> getRecommendAllPageList(Page<Map<String, Object>> page, Integer isTop,String name) {
		Page<RecommendVo> recommendVoPage = new Page<RecommendVo>();
		List<Map<String,Object>> list = recommendMapper.selectRecommendAllPageList(page, isTop,name);
		getRecommendVoCommon(page, recommendVoPage, list);
		return recommendVoPage;
	}

	/**
	 * 获取推荐信息公用方法
	 * @param page
	 * @param recommendVoPage
	 * @param list
	 */
	private void getRecommendVoCommon(Page<Map<String, Object>> page, Page<RecommendVo> recommendVoPage,
			List<Map<String, Object>> list) {
		if(list != null && list.size() > 0){
			List<RecommendVo> recommendVoList = new ArrayList<RecommendVo>();
			for(Map<String,Object> map : list){
				RecommendVo recommendVo = new RecommendVo();
				for(Entry<String,Object> entry : map.entrySet()){
					if("bussinessId".equals(entry.getKey())){
						recommendVo.setBussinessId((Integer)entry.getValue());
					}
					if("userId".equals(entry.getKey())){
						recommendVo.setUserId((Integer)entry.getValue());
					}
					if("bname".equals(entry.getKey())){
						recommendVo.setbName((String)entry.getValue());
					}
					if("recommendIndex".equals(entry.getKey())){
						recommendVo.setRecommendIndex((Integer)entry.getValue());
					}
					if("reasons".equals(entry.getKey())){
						recommendVo.setReasons((String)entry.getValue());
					}
					if("createTime".equals(entry.getKey())){
						recommendVo.setCreateDate(DateUtil.getDatetime((Integer)entry.getValue()));
					}
					if("recommendId".equals(entry.getKey())){
						recommendVo.setId((Integer)entry.getValue());
					}
					if("isTop".equals(entry.getKey())){
						recommendVo.setIsTop((Integer)entry.getValue());
					}
					if("mobile".equals(entry.getKey())){
						String mobile = entry.getValue() == null ? null : (String)entry.getValue();
						String m = mobile.substring(3, 7);
						String mr = mobile.replace(m,"****");
						recommendVo.setMobile(mr);
					}
					if("modifyTime".equals(entry.getKey())){
						recommendVo.setModifyDate(DateUtil.getDatetime((Integer)entry.getValue()));
					}
				}
				recommendVoList.add(recommendVo);
			}
			recommendVoPage.setCurrentPage(page.getCurrentPage());
			recommendVoPage.setPageSize(page.getPageSize());
			recommendVoPage.setTotalPage(page.getTotalPage());
			recommendVoPage.setTotalRecord(page.getTotalRecord());
			recommendVoPage.setResults(recommendVoList);
		}
	}

	@Override
	public Boolean isHasRecommended(Integer userId, Integer bussinessId) {
		int count = recommendMapper.isHasRecommended(userId, bussinessId);
		return count > 0;
	}

	@Override
	public Recommend getRemcommendByUserIdAndOrderId(Integer userId,
			Long orderId) {
		Recommend recommend = recommendMapper.selectRecommendByUserIdAndOrderId(userId, orderId);
		return recommend;
	}

}
