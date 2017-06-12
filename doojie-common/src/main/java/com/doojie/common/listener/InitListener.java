package com.doojie.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.doojie.common.constant.OrderConstant;
import com.doojie.common.util.DateUtil;

/**
 * 功能概述：初始化数据
 * <br/>
 * 创建时间：2015年6月30日下午7:32:06
 * <br/>
 * 修改记录：
 * @author lixiaoliang
 */
public class InitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		sce.getServletContext().removeAttribute("appiontmentDate");
		sce.getServletContext().removeAttribute("website_name");
		sce.getServletContext().removeAttribute("website_keywords");
		sce.getServletContext().removeAttribute("website_desc");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		sce.getServletContext().setAttribute("appiontmentDate",DateUtil.dateToWeek());
		sce.getServletContext().setAttribute("website_name", OrderConstant.WEBSITE_NAME);
		sce.getServletContext().setAttribute("website_keywords", OrderConstant.WEBSITE_KEYWORDS);
		sce.getServletContext().setAttribute("website_desc", OrderConstant.WEBSITE_DESC);
	}

}
