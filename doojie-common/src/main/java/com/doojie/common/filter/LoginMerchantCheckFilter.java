package com.doojie.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.doojie.common.constant.BaseConstant;
import com.doojie.domain.po.Bussiness;

public class LoginMerchantCheckFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		Bussiness bs = (Bussiness) session.getAttribute(BaseConstant.BUSSINESS);
		if(bs != null) {
			chain.doFilter(req, resp);
		} else {
			PrintWriter out = response.getWriter();
			out.write("-100");
			out.close();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
