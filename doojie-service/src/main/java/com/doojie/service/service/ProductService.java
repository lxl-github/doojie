package com.doojie.service.service;

import java.util.List;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Product;
import com.doojie.domain.vo.ProductVo;

public interface ProductService {

	boolean saveProduct(Product product);
	
	boolean updateProduct(Product product);
	
	Product getProductById(Integer id);
	
	/**
	 * 是否前端显示
	 * <br>
	 * 创建时间：2015年6月2日下午2:27:50
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param isShow
	 * @param id
	 * @return
	 *
	 */
	boolean updateProductIsShow(Integer isShow, Integer id);
	
	/**
	 * 是否删除
	 * <br>
	 * 创建时间：2015年6月2日下午2:28:14
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param isDeleted
	 * @param id
	 * @return
	 *
	 */
	boolean updateProductIsDeleted(Integer isDeleted, Integer id);
	
	/**
	 * 根据商品名称查询是否已经存在
	 * <br>
	 * 创建时间：2015年6月1日下午5:34:32
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param name
	 * @return
	 *
	 */
	Product getProductByName(String name);
	
	/**
	 * 根据商品类型和类别查询商品列表
	 * <br>
	 * 创建时间：2015年6月1日下午5:43:05
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param page
	 * @param type 类型
	 * @param category 类别
	 * @return
	 *
	 */
	List<ProductVo> getProductPageList(Page<Product> page, Integer type, Integer category);
	
	/**
	 * 根据商品类型和类别查询商品列表 用于购买券或卡时使用
	 * <br>
	 * 创建时间：2015年6月2日下午2:34:39
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param type
	 * @param category
	 * @return
	 *
	 */
	List<Product> getProductList(Integer type, Integer category, Integer regionId);
}
