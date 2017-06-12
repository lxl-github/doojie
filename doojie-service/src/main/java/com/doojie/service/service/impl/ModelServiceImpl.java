package com.doojie.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.dao.ModelsMapper;
import com.doojie.domain.po.Models;
import com.doojie.service.service.ModelsService;

@Service
public class ModelServiceImpl implements ModelsService {

	
	private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);
	
	@Autowired
	private ModelsMapper modelsMapper;
	
	@Override
	public boolean saveModel(Models models) {
		try {
			int count = modelsMapper.insert(models);
			return count > 0;
		} catch (Exception e) {
			logger.debug("saveModel exception : {}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean updateModel(Models models) {
		try {
			int count = modelsMapper.updateByPrimaryKeySelective(models);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateModel exception : {}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean deleteModel(Integer id) {
		try {
			int count = modelsMapper.deleteByPrimaryKey(id);
			return count > 0;
		} catch (Exception e) {
			logger.debug("deleteModel exception : {}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public Models getModelsById(Integer id) {
		return modelsMapper.selectByPrimaryKey(id);
	}

	@Override
	public Models getModelsByName(String name,Integer brandId) {
		return modelsMapper.selectModelsByName(name,brandId);
	}

	@Override
	public List<Models> findModelsByBrandId(Integer brandId) {
		return modelsMapper.selectModelsList(brandId);
	}

}
