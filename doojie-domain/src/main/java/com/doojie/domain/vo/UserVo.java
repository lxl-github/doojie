package com.doojie.domain.vo;

import com.doojie.domain.po.User;

public class UserVo {
	
	private User user;
	
	private String createDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
