package com.doojie.domain.vo;

import com.doojie.domain.po.Server;

public class ServerVo {

	private Server server;
	
	private String createDate;
	
	private String modifyDate;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
