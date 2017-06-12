package com.doojie.shiro.dto;

import java.io.Serializable;


public class ShiroUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4950147808250463141L;
	private String loginName;

	public ShiroUser(String loginName) {
		super();
		this.loginName = loginName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public ShiroUser() {
		super();
	}

	@Override
	public String toString() {
		return loginName;
	}

}
