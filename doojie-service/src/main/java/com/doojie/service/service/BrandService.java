package com.doojie.service.service;

import java.util.List;

import com.doojie.domain.po.Brand;

public interface BrandService {

	/**
	 * 保存品牌
	 * <br>
	 * 创建时间：2015年6月1日下午4:46:26
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param brand
	 * @return
	 *
	 */
	boolean saveBrand(Brand brand);
	
	/**
	 * 修改品牌
	 * <br>
	 * 创建时间：2015年6月1日下午4:46:41
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param brand
	 * @return
	 *
	 */
	boolean updateBrand(Brand brand);
	
	/**
	 * 根据id删除品牌
	 * <br>
	 * 创建时间：2015年6月1日下午4:48:17
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param id
	 * @return
	 *
	 */
	boolean deleteBrand(Integer id);
	
	/**
	 * 根据id查询品牌对象
	 * <br>
	 * 创建时间：2015年6月1日下午4:46:53
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param id
	 * @return
	 *
	 */
	Brand getBrandById(Integer id);
	
	/**
	 * 根据品牌名称查询品牌对象
	 * <br>
	 * 创建时间：2015年6月1日下午5:46:42
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param name
	 * @return
	 *
	 */
	Brand getBrandByName(String name);
	
	/**
	 * 获取所有品牌
	 * <br>
	 * 创建时间：2015年6月1日下午4:47:09
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @return
	 *
	 */
	List<Brand> findBrands();
	
}
