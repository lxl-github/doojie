package com.doojie.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.doojie.common.constant.BaseConstant;
import com.doojie.domain.po.User;

public class LoginUserCheckFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		User em = (User) session.getAttribute(BaseConstant.USER);
		if(em != null) {
			chain.doFilter(req, resp);
		} else {
			if(req.getRequestURI().contains("user")){
				String openId = "";
				Cookie[] cookieArray = req.getCookies();
				if(null != cookieArray){
					for(Cookie c :cookieArray){
						if(BaseConstant.COOKIE_USER_WEIXIN_OPENID.equals(c.getName())){
							openId = c.getValue();
							break;
						}
					}
				}
				
				if(null != openId || !"".equals(openId)){
					String reqUrl = req.getServletPath();
					request.getRequestDispatcher("/webApp/reLogin?openId=" + openId + "&reqUrl=" + reqUrl).forward(request,response);
				}else{
					request.getRequestDispatcher("/webApp/login").forward(request,response);
				}
			}else{
				PrintWriter out = response.getWriter();
				out.write("-100");
				out.close();
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
