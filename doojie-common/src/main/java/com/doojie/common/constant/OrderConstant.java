package com.doojie.common.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.doojie.common.util.PropertiesUtil;

public class OrderConstant {

	static Properties properties = PropertiesUtil.getProperties("commonLanguage.properties");
	
	/**
	 * 下单成功
	 */
	public static final Integer SUCCESS_ORDER_STATUS = 1;
	
	/**
	 * 取消订单
	 */
	public static final Integer CANEAL_ORDER_STATUS = 2;
	
	/**
	 * 已派单
	 */
	public static final Integer DISTRIBUTION_ORDER_STATUS = 3;
	
	
	/**
	 * 处理中
	 */
	public static final Integer HANDLE_CURRENT_ORDER_STATUS = 4;
	
	/**
	 * 处理完成
	 */
	public static final Integer HANDLE_COMPLETE_ORDER_STATUS = 5;
	
	/**
	 * 无效订单
	 */
	public static final Integer INVALID_ORDER_STATUS = 6;
	
	
	/**
	 * 消费完成
	 */
	public static final Integer CONSUM_COMPLETE_ORDER_STATUS = 7;
	
	
	public static final String SELF_ORDER_STATUS_1 = properties.getProperty("self_order_status1");
	
	public static final String SELF_ORDER_STATUS_2 = properties.getProperty("self_order_status2");
	
	public static final String SELF_ORDER_STATUS_3 = properties.getProperty("self_order_status3");
	
	public static final String SELF_ORDER_STATUS_4 = properties.getProperty("self_order_status4");
	
	public static final String SELF_ORDER_STATUS_5 = properties.getProperty("self_order_status5");
	
	public static final String SELF_ORDER_STATUS_6 = properties.getProperty("self_order_status6");
	
	public static final String SELF_ORDER_STATUS_7 = properties.getProperty("self_order_status7");
	
	
	
	
	public static final String BUSSINESS_ORDER_STATUS_1 = properties.getProperty("bussiness_order_status1");
	
	public static final String BUSSINESS_ORDER_STATUS_1_1 = properties.getProperty("bussiness_order_status1_1");
	
	public static final String BUSSINESS_ORDER_STATUS_2 = properties.getProperty("bussiness_order_status2");
	
	public static final String BUSSINESS_ORDER_STATUS_4 = properties.getProperty("bussiness_order_status4");
	
	public static final String BUSSINESS_ORDER_STATUS_5 = properties.getProperty("bussiness_order_status5");
	
	public static final String BUSSINESS_ORDER_STATUS_6 = properties.getProperty("bussiness_order_status6");
	
	public static final String BUSSINESS_ORDER_STATUS_7 = properties.getProperty("bussiness_order_status7");
	
	
	/************************ 站点设置*******************************/
	
	public static final String WEBSITE_NAME = properties.getProperty("website_name");
	
	public static final String WEBSITE_KEYWORDS = properties.getProperty("website_keywords");
	
	public static final String WEBSITE_DESC = properties.getProperty("website_desc");
	
	
	/*********************** 订单所属 ***********************/
	
	/**
	 * 商家
	 */
	public static final Integer ORDER_OWN_BUSSINESS = 2;
	
	/**
	 * 自营
	 */
	public static final Integer ORDER_OWN_SELF = 1;
	
	
	/**预约下单**/
	public static final Integer ORDER_APPOI = 1;
	
	/**验码下单**/
	public static final Integer ORDER_CHECKCODE = 2;
	
	
	public static final Map<Integer,String> SELF_CONTENT_MAP = new HashMap<Integer, String>();
	
	public static final Map<Integer,String> BUSSINESS_CONTENT_MAP = new HashMap<Integer, String>();
	
	static{
		SELF_CONTENT_MAP.put(SUCCESS_ORDER_STATUS, SELF_ORDER_STATUS_1);
		SELF_CONTENT_MAP.put(CANEAL_ORDER_STATUS, SELF_ORDER_STATUS_2);
		SELF_CONTENT_MAP.put(DISTRIBUTION_ORDER_STATUS, SELF_ORDER_STATUS_3);
		SELF_CONTENT_MAP.put(HANDLE_CURRENT_ORDER_STATUS, SELF_ORDER_STATUS_4);
		SELF_CONTENT_MAP.put(HANDLE_COMPLETE_ORDER_STATUS, SELF_ORDER_STATUS_5);
		SELF_CONTENT_MAP.put(INVALID_ORDER_STATUS, SELF_ORDER_STATUS_6);
		SELF_CONTENT_MAP.put(CONSUM_COMPLETE_ORDER_STATUS, SELF_ORDER_STATUS_7);
		
		
		BUSSINESS_CONTENT_MAP.put(SUCCESS_ORDER_STATUS, BUSSINESS_ORDER_STATUS_1);
		BUSSINESS_CONTENT_MAP.put(CANEAL_ORDER_STATUS, BUSSINESS_ORDER_STATUS_2);
		BUSSINESS_CONTENT_MAP.put(HANDLE_CURRENT_ORDER_STATUS, BUSSINESS_ORDER_STATUS_4);
		BUSSINESS_CONTENT_MAP.put(HANDLE_COMPLETE_ORDER_STATUS, BUSSINESS_ORDER_STATUS_5);
		BUSSINESS_CONTENT_MAP.put(INVALID_ORDER_STATUS, BUSSINESS_ORDER_STATUS_6);
		BUSSINESS_CONTENT_MAP.put(CONSUM_COMPLETE_ORDER_STATUS, BUSSINESS_ORDER_STATUS_7);
	}
}
