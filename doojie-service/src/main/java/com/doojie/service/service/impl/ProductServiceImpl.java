package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.dao.ProductMapper;
import com.doojie.dao.RegionMapper;
import com.doojie.domain.po.PackageServer;
import com.doojie.domain.po.Product;
import com.doojie.domain.po.Region;
import com.doojie.domain.vo.PackageServerVo;
import com.doojie.domain.vo.ProductVo;
import com.doojie.service.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private RegionMapper regionMapper;
	
	
	@Override
	public boolean saveProduct(Product product) {
		try {
			int count = productMapper.insert(product);
			return count > 0;
		} catch (Exception e) {
			logger.debug("saveProduct exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		int count = productMapper.updateByPrimaryKeySelective(product);
		return count > 0;
	}

	@Override
	public Product getProductById(Integer id) {
		return productMapper.selectByPrimaryKey(id);
	}

	@Override
	public Product getProductByName(String name) {
		return productMapper.selectProductByName(name);
	}

	@Override
	public List<ProductVo> getProductPageList(Page<Product> page, Integer type, Integer category) {
		List<Product> productList = productMapper.selectProductPageList(page, type, category);
		List<ProductVo> productVoList = null;
		if(productList.size() > 0){
			productVoList = new ArrayList<ProductVo>();
			for (Product product : productList) {
				ProductVo productVo = new ProductVo();
				productVo.setProduct(product);
				productVo.setCreateDate(DateUtil.getDatetime(product.getCreateTime()));
				productVo.setModifyDate(DateUtil.getDatetime(product.getModifyTime()));
				Region region = regionMapper.selectByPrimaryKey(product.getRegionId());
				productVo.setRegionName(region.getName());
				productVoList.add(productVo);
			}
			page.setResults(productList);
		}
		return productVoList;
	}

	@Override
	public boolean updateProductIsShow(Integer isShow, Integer id) {
		try {
			int count = productMapper.updateProductIsShow(isShow, id);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateProductIsShow exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public boolean updateProductIsDeleted(Integer isDeleted, Integer id) {
		try {
			int count = productMapper.updateProductIsDeleted(isDeleted, id);
			return count > 0;
		} catch (Exception e) {
			logger.debug("updateProductIsDeleted exception :{}",e.getLocalizedMessage());
		}
		return false;
	}

	@Override
	public List<Product> getProductList(Integer type, Integer category,Integer regionId) {
		return productMapper.selectProductList(type, category,regionId);
	}

}
