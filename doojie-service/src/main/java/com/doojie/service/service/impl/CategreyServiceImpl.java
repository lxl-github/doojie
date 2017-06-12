package com.doojie.service.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
//import com.doojie.dao.CategreyMapper;
//import com.doojie.domain.po.Categrey;
import com.doojie.service.service.CategreyService;
@Service
public class CategreyServiceImpl implements CategreyService{

//	@Autowired
//	private CategreyMapper categreyMapper; 
//	
//	@Override
//	public List<Categrey> getCategreyPageList(Page<Categrey> page) {
//		List<Categrey> categreyList = categreyMapper.getCategreyPageList(page);
//		page.setResults(categreyList);
//		return categreyList;
//	}
//
//	@Override
//	public List<Categrey> getCategreyAllList() {
//		List<Categrey> categreyList = categreyMapper.getCategreyAllList();
//		return categreyList;
//	}
//
//	@Override
//	public Map<Integer, String> getCategreyAllMap() {
//		Map<Integer,String> map = new HashMap<Integer, String>();
//		List<Categrey> categreyList = categreyMapper.getCategreyAllList();
//		if(categreyList != null && categreyList.size() > 0){
//			for (Categrey categrey : categreyList) {
//				map.put(categrey.getId(),categrey.getName());
//			}
//		}
//		return map;
//	}

}
