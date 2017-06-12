package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.constant.UserConstant;
import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.common.util.RandomUtil;
import com.doojie.dao.ProductMapper;
import com.doojie.dao.TradeRecordMapper;
import com.doojie.dao.UserMapper;
import com.doojie.dao.UserProductMapper;
import com.doojie.domain.po.Product;
import com.doojie.domain.po.TradeRecord;
import com.doojie.domain.po.User;
import com.doojie.domain.po.UserProduct;
import com.doojie.domain.vo.SuggestVo;
import com.doojie.domain.vo.TradeRecordVo;
import com.doojie.domain.vo.UserCardVo;
import com.doojie.domain.vo.UserProductVo;
import com.doojie.domain.vo.UserVo;
import com.doojie.service.service.UserService;
@Service
public class UserServiceImpl implements UserService {

	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserProductMapper userProductMapper;
	
	@Autowired
	private TradeRecordMapper tradeRecordMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public List<UserVo> getUserPageList(Page<User> page) {
		List<User> userList = userMapper.selectUserAllPageList(page);
		List<UserVo> userVoList = null;
		if(userList.size() > 0){
			userVoList = new ArrayList<UserVo>();
			for (User user : userList) {
				UserVo userVo = new UserVo();
				userVo.setUser(user);
				userVo.setCreateDate(DateUtil.getDatetime(user.getCreateTime()));
				userVoList.add(userVo);
			}
			page.setResults(userList);
		}
		
		return userVoList;
	}

	@Override
	public User getUserById(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public User getUserByMobile(String mobile) {
		return userMapper.selectUserByMobile(mobile);
	}

	@Override
	public Integer saveUser(User user) {
		try{
			int count = userMapper.insert(user);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer updateUser(User user) {
		try{
			int count = userMapper.updateByPrimaryKeySelective(user);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer deleteUser(Integer userId) {
		try{
			int count = userMapper.deleteByPrimaryKey(userId);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer saveSuggest(String content,Integer userId) {
		try{
			Integer createTime = DateUtil.getCurrentTimespan();
			int count = userMapper.insertSuggest(null, content, createTime, userId);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Page<SuggestVo> getSuggestAllPageList(Integer currentPage) {
		Page<SuggestVo> suggestVoPage = new Page<SuggestVo>();
		
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		page.setCurrentPage(currentPage);
		
		List<Map<String,Object>> mapList = userMapper.selectSuggestPageList(page);
		if(mapList != null && mapList.size() > 0){
			List<SuggestVo> suggestVoList = new ArrayList<SuggestVo>();
			for (Map<String, Object> map : mapList) {
				SuggestVo suggestVo = new SuggestVo();
				for (Entry<String,Object> entry :map.entrySet()) {
					if("mobile".equals(entry.getKey())){
						suggestVo.setCreateUser((String)entry.getValue());
					}
					if("content".equals(entry.getKey())){
						suggestVo.setContent((String)entry.getValue());
					}
					if("createTime".equals(entry.getKey())){
						suggestVo.setCreateDate(DateUtil.getDatetime((Integer)entry.getValue()));
					}
				}
				suggestVoList.add(suggestVo);
			}
			suggestVoPage.setCurrentPage(currentPage);
			suggestVoPage.setResults(suggestVoList);
			suggestVoPage.setTotalPage(page.getTotalPage());
			suggestVoPage.setTotalRecord(page.getTotalRecord());
		}
		
		return suggestVoPage;
	}

	@Override
	public List<UserProductVo> getUserProductVoPageList(Page<UserProductVo> page,Integer userId) {
		
		List<UserProductVo> userProductVoList = userProductMapper.getUserProductVoPageList(page, userId);
		
		return userProductVoList;
	}

	@Override
	public List<UserCardVo> getUserCardVoList(Integer userId,Integer category,Integer isDoor) {
		return userProductMapper.getUserCardVoList(userId,category,isDoor);
	}

	@Override
	public List<TradeRecord> getTradeRecordListByUserId(Integer userId) {
		return tradeRecordMapper.selectByUserId(userId);
	}

	@Override
	public TradeRecordVo getTradeRecordVoByUserIdAndTradeRecordId(
			Integer userId, Integer tradeRecordId) {
		return tradeRecordMapper.selectTradeRecordVoByUserIdAndTradeRecordId(userId, tradeRecordId);
	}

	@Override
	public UserProduct getUserProductById(Integer id) {
		return userProductMapper.selectByPrimaryKey(id);
	}

	@Override
	public String saveUserProduct(Integer productId, Integer userId,String tradeNo,String serialNumber) {
		//获取商品信息
		Product product = productMapper.selectByPrimaryKey(productId);
		//获取用户信息
		User user = userMapper.selectByPrimaryKey(userId);

		//保存用户商品
		UserProduct userProduct = new UserProduct();
		Integer userProductId = userProductMapper.selectUserProdcutNextId();
		//8为日期+六位随机数+主键id
		userProduct.setConsumeCode(DateUtil.getDate().concat(RandomUtil.random(6)).concat(userProductId.toString()));
		userProduct.setConsumeNumber(product.getNumber());
		userProduct.setConsumeNumberAlready(0);
		userProduct.setCreateTime(DateUtil.getCurrentTimespan());
		userProduct.setExpiredTime(DateUtil.getMonthAfterTime(product.getMonthNumber()));
		userProduct.setProductId(product.getId());
		userProduct.setUserId(user.getId());
		userProduct.setStatus(UserConstant.CARD_STATUS_NOT_USED);
		userProductMapper.insertSelective(userProduct);
		
		//保存交易记录
		TradeRecord tradeRecord = new TradeRecord();
		tradeRecord.setCreateTime(DateUtil.getCurrentTimespan());
		tradeRecord.setMoney(Double.valueOf(product.getDiscountPrice())/100);
		tradeRecord.setPayMode(1);
		tradeRecord.setSerialNumber(serialNumber);
		//8为日期+六位随机数+主键id
		tradeRecord.setTradeCode(tradeNo);
		tradeRecord.setUserId(userId);
		tradeRecord.setUserProductId(userProductId);
		tradeRecord.setPayTime(DateUtil.getCurrentTimespan());
		tradeRecordMapper.insertSelective(tradeRecord);
		return "success";
	}

	@Override
	public User getUserByOpenId(String openId) {
		return userMapper.selectUserByOpenId(openId);
	}

	@Override
	public Boolean getIsContains(String tradeCode) {
		TradeRecord tradeRecord = tradeRecordMapper.selectByTradeCode(tradeCode);
		return null == tradeRecord;
	}


}
