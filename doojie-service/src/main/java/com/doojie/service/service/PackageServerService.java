package com.doojie.service.service;

import java.util.List;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.PackageServer;
import com.doojie.domain.vo.PackageServerVo;

public interface PackageServerService {

	List<PackageServerVo> getPackageServerByBussinessIdPageLsit(Page<PackageServer> page, Integer bussinessId, String name, String status);

	PackageServer getPackageServerById(Integer packageId);
	
	Integer savePackageServer(PackageServer packageServer);
	
	Integer updatePackageServer(PackageServer packageServer);
	
	Integer deletePackageServer(Integer packageId);
	
	Boolean savePackage(PackageServer packageServer, Integer[] serverIdArr);
	
	Boolean updatePackage(PackageServer packageServer, Integer[] serverIdArr);
	
	Boolean isExistsPackageRelServerByServerId(Integer serverId);
	
	Boolean isExistsPackageServerByName(String name, Integer bussinessId);
}
