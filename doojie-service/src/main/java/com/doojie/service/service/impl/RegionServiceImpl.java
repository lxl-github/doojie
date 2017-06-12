package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.dao.RegionMapper;
import com.doojie.domain.po.Region;
import com.doojie.service.service.RegionService;
@Service
public class RegionServiceImpl implements RegionService {

	
	private static final Logger logger = LoggerFactory.getLogger(RegionServiceImpl.class);
	
	@Autowired
	private RegionMapper regionMapper;
	
	@Override
	public boolean saveRegion(Region region) {
		try {
			int count = regionMapper.insert(region);
			return count > 0;
		} catch (Exception e) {
			logger.debug("saveRegion exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean updateRegion(Region region) {
		try {
			int count = regionMapper.updateByPrimaryKeySelective(region);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateRegion exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean deleteRegion(Integer id) {
		try {
			int count = regionMapper.deleteByPrimaryKey(id);
			return count > 0;
		} catch (Exception e) {
			logger.debug("deleteRegion exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public Region getRegionById(Integer id) {
		return regionMapper.selectByPrimaryKey(id);
	}

	@Override
	public Region getRegionByName(String name,Integer parentId) {
		return regionMapper.selectRegionByName(name,parentId);
	}
	
	@Override
	public Region getRegionByLikeName(String name,Integer parentId) {
		return regionMapper.selectRegionByLikeName(name,parentId);
	}

	@Override
	public void getRegionByParentId(Integer parentId,List<Region> regionsList) {
		List<Region> regionList = regionMapper.selectRegionListByParentId(parentId);
		if(regionList != null && regionList.size() > 0){
			regionsList.addAll(regionList);
			for(Region region : regionList){
				getRegionByParentId(region.getId(),regionsList);
			}
		}
	}
	
	@Override
	public List<Region> getRegionByParentId(Integer parentId) {
		return regionMapper.selectRegionListByParentId(parentId);
	}

	@Override
	public boolean updateRegionIsEnabled(Integer isEnabled, Integer id) {
		try {
			int count = regionMapper.updateRegionByEnabledAndId(isEnabled, id);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateRegionIsEnabled exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean updateRegionIsDeleted(Integer isDeleted,List<Region> list) {
		try {
			int count = regionMapper.updateRegionByDeleted(isDeleted, list);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateRegionIsDeleted exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean isExistsEnabledRegionByParentId(Integer parentId) {
		int count = regionMapper.selectEnabledRegionListByParentId(parentId);
		return count > 0;
	}

	@Override
	public Region getRegionByCode(String code) {
		return regionMapper.selectRegionByCode(code);
	}

	@Override
	public List<Region> getRegionListByLevel(Integer level) {
		return regionMapper.selectRegionListByLevel(level);
	}

}
