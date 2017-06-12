package com.doojie.service.service;

import java.util.Map;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Recommend;
import com.doojie.domain.vo.RecommendVo;


public interface RecommendService {

	Recommend getRecommendById(Integer id);
	
	Integer saveRecommend(Recommend recommend);
	
	Integer updateRecommend(Recommend recommend);
	
	Integer deleteRecommend(Integer recommendId);
	
	Page<RecommendVo> getRecommendByUserIdPageList(Page<Map<String, Object>> page, Integer userId);
	
	Page<RecommendVo> getRecommendByBussinessIdPageList(Page<Map<String, Object>> page, Integer bussinessId, Integer isOnlyOne);
	
	Page<RecommendVo> getRecommendAllPageList(Page<Map<String, Object>> page, Integer isTop, String name);
	
	Boolean isHasRecommended(Integer userId,Integer bussinessId);
	
	Recommend getRemcommendByUserIdAndOrderId(Integer userId, Long orderId);
}
