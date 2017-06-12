package com.doojie.service.service;

import java.util.List;

import com.doojie.domain.po.Region;

public interface RegionService {

	boolean saveRegion(Region region);
	
	boolean updateRegion(Region region);
	
	boolean deleteRegion(Integer id);
	
	/**
	 * 启用禁用区域
	 * <br>
	 * 创建时间：2015年6月2日上午11:16:33
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param isEnabled
	 * @param id
	 * @return
	 *
	 */
	boolean updateRegionIsEnabled(Integer isEnabled, Integer id);
	
	/**
	 * 删除区域
	 * <br>
	 * 创建时间：2015年6月2日上午11:16:50
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param isDeleted
	 * @param id
	 * @return
	 *
	 */
	boolean updateRegionIsDeleted(Integer isDeleted, List<Region> list);
	
	/**
	 * 根据id查询区域对象
	 * <br>
	 * 创建时间：2015年6月1日下午5:02:45
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param id
	 * @return
	 *
	 */
	Region getRegionById(Integer id);
	
	/**
	 * 根据区域名称查询区域对象
	 * <br>
	 * 创建时间：2015年6月1日下午6:07:50
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param name
	 * @return
	 *
	 */
	Region getRegionByName(String name, Integer parentId);
	
	/**
	 * 根据区域名称模糊查询区域对象
	 * <br>
	 * 创建时间：2015年6月1日下午6:07:50
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param name
	 * @return
	 *
	 */
	Region getRegionByLikeName(String name, Integer parentId);
	
	/**
	 * 根据code查询区域对象
	 * <br>
	 * 创建时间：2015年6月3日下午5:06:45
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param code
	 * @return
	 *
	 */
	Region getRegionByCode(String code);
	
	/**
	 * 采用递归方式查找子级
	 * <br>
	 * 创建时间：2015年6月4日上午11:04:45
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param parentId
	 * @param regionList
	 *
	 */
	void getRegionByParentId(Integer parentId, List<Region> regionList);
	
	
	/**
	 * 根据父级查询子级列表
	 * <br>
	 * 创建时间：2015年6月4日上午11:06:23
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param parentId
	 * @return
	 *
	 */
	List<Region> getRegionByParentId(Integer parentId);
	
	/**
	 * 根据基本查询区域列表
	 * <br>
	 * 创建时间：2015年6月12日下午3:07:10
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param level
	 * @return
	 *
	 */
	List<Region> getRegionListByLevel(Integer level);
 	
	/**
	 * 根据父级id查看是否存在子级区域
	 * <br>
	 * 创建时间：2015年6月2日下午3:59:07
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param parentId
	 * @return
	 *
	 */
	boolean isExistsEnabledRegionByParentId(Integer parentId);
}
