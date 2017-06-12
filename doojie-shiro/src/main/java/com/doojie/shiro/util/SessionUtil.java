package com.doojie.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.doojie.common.constant.BaseConstant;
import com.doojie.shiro.dto.BussinessDTO;
import com.doojie.shiro.dto.EmployeeDTO;

/**
 * 从Session中获取属性的工具类 使用此工具类，因为我们使用的shiro安全框架，可以使用SecurityUtils与web容器解耦。
 * 
 * @author lxl
 * 
 */
public final class SessionUtil {

	private SessionUtil() {
		
	}

	
	/**
	 * 获取Session对象
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 放置属性进入session
	 * 
	 * @param key
	 * @param value
	 */
	public static void setAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 从session中获取属性
	 * 
	 * @param key
	 * @return
	 */
	public static Object getAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 从session中取出当前用户Id 方法功能说明
	 * 
	 * @return
	 */
	public static Integer getCurrentEmployeeId() {
		return getCurrentEmployee().getId();
	}

	/**
	 * 从Session中去除当前用户登录名 方法功能说明
	 * 
	 * @return
	 */
	public static String getCurrentNumber() {
		return getCurrentEmployee().getNumber();
	}

	/**
	 * 从Session中去除当前用户名称 方法功能说明
	 * 
	 * @return
	 */
	public static String getCurrentUName() {
		return getCurrentEmployee().getName();
	}

	/**
	 * 从session中取出当前用户 方法功能说明
	 * 
	 * @return
	 */
	public static EmployeeDTO getCurrentEmployee() {
		return (EmployeeDTO) getSession().getAttribute(BaseConstant.SYSTEM_EMPLOYEE);
	}


}
