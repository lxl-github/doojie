package com.doojie.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.dao.BrandMapper;
import com.doojie.domain.po.Brand;
import com.doojie.service.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	
	private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
	
	@Autowired
	private BrandMapper brandMapper;
	
	@Override
	public boolean saveBrand(Brand brand) {
		try {
			int count = brandMapper.insert(brand);
			return count > 0;
		} catch (Exception e) {
			logger.debug("saveBrand exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean updateBrand(Brand brand) {
		try {
			int count = brandMapper.updateByPrimaryKeySelective(brand);
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("updateBrand exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean deleteBrand(Integer id) {
		try {
			int count = brandMapper.deleteByPrimaryKey(id);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateBrand exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public Brand getBrandById(Integer id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public Brand getBrandByName(String name) {
		return brandMapper.selectBrandByBrandName(name);
	}

	@Override
	public List<Brand> findBrands() {
		return brandMapper.selectBrandList();
	}

}
