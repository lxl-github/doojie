package com.doojie.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.doojie.common.util.XSSHttpServletRequestWrapper;


/**
 * 功能概述：控制XSS攻击
 * <br/>
 * 创建时间：2015年3月25日下午6:20:09
 * <br/>
 * 修改记录：
 * @author lixiaoliang
 */
public class XSSFilter implements Filter {

	FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XSSHttpServletRequestWrapper(
				(HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

}
