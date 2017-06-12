package com.doojie.service.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.TradeRecord;
import com.doojie.domain.po.User;
import com.doojie.domain.po.UserProduct;
import com.doojie.domain.vo.SuggestVo;
import com.doojie.domain.vo.TradeRecordVo;
import com.doojie.domain.vo.UserCardVo;
import com.doojie.domain.vo.UserProductVo;
import com.doojie.domain.vo.UserVo;

public interface UserService {

	List<UserVo> getUserPageList(Page<User> page);
	
	User getUserById(Integer userId);
	
	User getUserByMobile(String mobile);
	
	User getUserByOpenId(String openId);
	
	Integer saveUser(User user);
	
	Integer updateUser(User user);
	
	Integer deleteUser(Integer userId);
	
	Integer saveSuggest(String content, Integer userId);
	
	Page<SuggestVo> getSuggestAllPageList(Integer currentPage);
	
	UserProduct getUserProductById(Integer id);
	
	List<UserProductVo> getUserProductVoPageList(Page<UserProductVo> page, Integer userId);
	
	List<UserCardVo> getUserCardVoList(Integer userId, Integer category, Integer isDoor);
	
	List<TradeRecord> getTradeRecordListByUserId(Integer userId);
	
	TradeRecordVo getTradeRecordVoByUserIdAndTradeRecordId(Integer userId, Integer tradeRecordId);
	
	/**
	 * 支付成功后，回调此方法进行商品的用户绑定
	 * <br>
	 * 创建时间：2015年8月28日下午5:06:52
	 * <br>
	 * @author lixiaoliang
	 * <br>
	 * @param productId
	 * @return
	 *
	 */
	String saveUserProduct(Integer productId, Integer userId, String tradeNo, String serialNumber);
	
	/**
	 * 根据交易编号查询是否已有交易，防止微信支付回调时，二次生成交易记录,导致数据错误
	 * @param tradeCode
	 * @return
	 */
	Boolean getIsContains(String tradeCode);
}
