package com.doojie.service.service;

import java.util.List;

import com.doojie.domain.po.Models;

public interface ModelsService {

	/**
	 * 保存型号
	 * <br>
	 * 创建时间：2015年6月1日下午4:50:09
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param models
	 * @return
	 *
	 */
	boolean saveModel(Models models);
	
	/**
	 * 修改型号
	 * <br>
	 * 创建时间：2015年6月1日下午4:50:19
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param models
	 * @return
	 *
	 */
	boolean updateModel(Models models);
	
	/**
	 * 删除型号
	 * <br>
	 * 创建时间：2015年6月1日下午4:50:38
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param id
	 * @return
	 *
	 */
	boolean deleteModel(Integer id);
	
	/**
	 * 根据id查询型号
	 * <br>
	 * 创建时间：2015年6月1日下午4:50:51
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param id
	 * @return
	 *
	 */
	Models getModelsById(Integer id);
	
	/**
	 * 根据型号名称查询型号对象
	 * <br>
	 * 创建时间：2015年6月1日下午5:47:28
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param name
	 * @return
	 *
	 */
	Models getModelsByName(String name, Integer brandId);
	
	/**
	 * 方法功能说明
	 * <br>
	 * 创建时间：2015年6月1日下午4:51:32
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param brandId
	 * @return
	 *
	 */
	List<Models> findModelsByBrandId(Integer brandId);
	
}
