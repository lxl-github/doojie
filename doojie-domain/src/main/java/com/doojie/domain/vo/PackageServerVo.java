package com.doojie.domain.vo;

import com.doojie.domain.po.PackageServer;

public class PackageServerVo {
	
	private PackageServer packageServer;

	private String createDate;
	
	private String modifyDate;

	public PackageServer getPackageServer() {
		return packageServer;
	}

	public void setPackageServer(PackageServer packageServer) {
		this.packageServer = packageServer;
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
