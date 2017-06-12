package com.doojie.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取ip工具
 * <br>
 * 创建时间：2015年3月17日下午11:15:26
 * <br>
 * 修改记录：
 * <br>
 * @author lixiaoliang
 * <br>
 */
public class IpAdressUtil {
	  public static String getRemortIP(HttpServletRequest request) {  
	      if (request.getHeader("x-forwarded-for") == null) {  
	          return request.getRemoteAddr();  
	      }  
	      return request.getHeader("x-forwarded-for");  
	  }  

	  public static String getIpAddr(HttpServletRequest request) {  
	      String ip = request.getHeader("x-forwarded-for");  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getHeader("Proxy-Client-IP");  
	      }  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getHeader("WL-Proxy-Client-IP");  
	      }  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	          ip = request.getRemoteAddr();  
	      }  
	      return ip;  
	  }  
}
