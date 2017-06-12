package com.doojie.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.weaver.ast.HasAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
//import com.doojie.dao.ActivityMapper;
//import com.doojie.domain.po.Activity;
//import com.doojie.domain.po.ActivityPo;
import com.doojie.service.service.ActivityService;

/** 
* @类名: ActivityServiceImpl 
*<br/>
* @描述: TODO(活动业务逻辑处理) 
*<br/>
* @作者： lixiaoliang <lixiaoliang@qunarju.com>
*<br/>
* @日期： 2015年2月10日 下午4:12:12 
*<br/>
*  
*/
@Service
public class ActivityServiceImpl implements ActivityService{

	private static final Logger logger = LoggerFactory.getLogger(ActivityServiceImpl.class);

//	@Autowired
//	private ActivityMapper activityMapper;
//
//	@Override
//	public List<ActivityPo> searchActivityPageList(Page<ActivityPo> page) {
//		List<ActivityPo> activityList = activityMapper.getActivityPageList(page);
//		page.setResults(activityList);
//		return activityList;
//	}
//
//	@Override
//	public ActivityPo getActivityById(Integer activityId) {
//		return activityMapper.selectActivityById(activityId);
//	}
//
//	@Override
//	public Map<Integer,String> getActivityPictureByIdAndisShow(List<Integer> activityId, Integer isShow) {
//		List<Map<String,Object>> activityMapList = activityMapper.selectPictureByIdAndisShow(activityId, isShow);
//		
//		Map<Integer,String> resultMap = new HashMap<Integer, String>();
//		
//		for(Map<String,Object> map : activityMapList){
//			Integer aId = null;
//			String picImg = null;
//			for(Entry<String,Object> entry : map.entrySet()){
//				if("activityId".equals(entry.getKey())){
//					aId = Integer.valueOf(String.valueOf(entry.getValue()));
//				}else if("picImg".equals(entry.getKey())){
//					picImg = String.valueOf(entry.getValue());
//				}
//			}
//			resultMap.put(aId, picImg);
//		}
//		
//		return resultMap;
//	}
//
//	@Override
//	public Integer saveActivity(Activity activity) {
//		try{
//			int count = activityMapper.insert(activity);
//			return count;
//		}catch (Exception e) {
//			logger.error(e.toString(),e.fillInStackTrace());
//			return 0;
//		}
//	}
//
//	@Override
//	public Integer updateActivity(Activity activity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer deleteActivity(Integer activityId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	
}
