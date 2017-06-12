package com.doojie.common.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;

import com.doojie.common.util.PropertiesUtil;

public class BaseConstant {
	
	/** 用户 **/
	public static final String USER = "user";
	
	public static final String USER_ID = "user_id";
	
	public static final String USER_WEIXIN_OPENID = "user_weixin_openId";
	
	public static final String COOKIE_USER_WEIXIN_OPENID = "cookie_user_weixin_openId";
	
	/** 商家 **/
	public static final String BUSSINESS = "bussiness";
	
	/**商家id**/
	public static final String BUSSINESS_ID = "bussiness_id";
	
	
	public static final String SYSTEM_EMPLOYEE = "employee";
	
	public static final String SYSTEM_EMPLOYEE_ID = "employee_id";
	
	/**
	 * 默认密码
	 */
	public static final String DEFAULT_PASS = "123456";
	
	public static final String AK = PropertiesUtil.getProperties("config.properties").getProperty("baiduMapKey");

	/**
	 * 在职
	 */
	public static final Integer EMPLOYEE_STATUS_ZZ = 1;
	
	/**
	 * 离职
	 */
	public static final Integer EMPLOYEE_STATUS_LZ = 2;
	
	/**
	 * 休假
	 */
	public static final Integer EMPLOYEE_STATUS_XJ = 3;
	
	
	/**
	 * 员工编号起始值
	 */
	public static final Integer EMPLOYEE_COMMON = 10000;
	
	
	/**
	 * 车牌区域简称
	 */
	public static final Map<String, String> PLATE_MAP = new LinkedHashMap<String, String>();
	
	/**
	 * 车牌区域所属字母
	 */
	public static final Map<String,String> LETTER_MAP = new LinkedHashMap<String, String>();
	
	static{
		 PLATE_MAP.put("京", "京");
		 PLATE_MAP.put("津", "津");
		 PLATE_MAP.put("沪", "沪");
		 PLATE_MAP.put("渝", "渝");
		 PLATE_MAP.put("冀", "冀");
		 PLATE_MAP.put("豫", "豫");
		 PLATE_MAP.put("云", "云");
		 PLATE_MAP.put("辽", "辽");
		 PLATE_MAP.put("黑", "黑");
		 PLATE_MAP.put("湘", "湘");
		 PLATE_MAP.put("皖", "皖");
		 PLATE_MAP.put("鲁", "鲁");
		 PLATE_MAP.put("新", "新");
		 PLATE_MAP.put("苏", "苏");
		 PLATE_MAP.put("浙", "浙");
		 PLATE_MAP.put("赣", "赣");
		 PLATE_MAP.put("鄂", "鄂");
		 PLATE_MAP.put("桂", "桂");
		 PLATE_MAP.put("甘", "甘");
		 PLATE_MAP.put("晋", "晋");
		 PLATE_MAP.put("蒙", "蒙");
		 PLATE_MAP.put("陕", "陕");
		 PLATE_MAP.put("吉", "吉");
		 PLATE_MAP.put("闽", "闽");
		 PLATE_MAP.put("贵", "贵");
		 PLATE_MAP.put("粤", "粤");
		 PLATE_MAP.put("青", "青");
		 PLATE_MAP.put("藏", "藏");
		 PLATE_MAP.put("川", "川");
		 PLATE_MAP.put("宁", "宁");
		 PLATE_MAP.put("琼", "琼");

		
		
		 LETTER_MAP.put("A","A"); 
		 LETTER_MAP.put("B", "B");
		 LETTER_MAP.put("C", "C");
		 LETTER_MAP.put("D", "D");
		 LETTER_MAP.put("E", "E");
		 LETTER_MAP.put("F", "F");
		 LETTER_MAP.put("G", "G");
		 LETTER_MAP.put("H", "H");
		 LETTER_MAP.put("I", "I");
		 LETTER_MAP.put("G", "G");
		 LETTER_MAP.put("K", "K");
		 LETTER_MAP.put("L", "L");
		 LETTER_MAP.put("M", "M");
		 LETTER_MAP.put("N", "N");
		 LETTER_MAP.put("O", "O");
		 LETTER_MAP.put("P", "P");
		 LETTER_MAP.put("Q", "Q");
		 LETTER_MAP.put("R", "R");
		 LETTER_MAP.put("S", "S");
		 LETTER_MAP.put("T", "T");
		 LETTER_MAP.put("U", "U");
		 LETTER_MAP.put("V", "V");
		 LETTER_MAP.put("W", "W");
		 LETTER_MAP.put("S", "S");
		 LETTER_MAP.put("Y", "Y");
		 LETTER_MAP.put("Z", "Z");

	}
	
}
