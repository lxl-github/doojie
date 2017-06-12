package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.dao.PackageServerMapper;
import com.doojie.domain.bo.PackageRelServer;
import com.doojie.domain.po.PackageServer;
import com.doojie.domain.vo.PackageServerVo;
import com.doojie.service.service.PackageServerService;

@Service
public class PackageServerServiceImpl implements PackageServerService {

	
	private static final Logger logger = LoggerFactory.getLogger(PackageServerServiceImpl.class);
	
	@Autowired
	private PackageServerMapper packageServerMapper;
	
	@Override
	public List<PackageServerVo> getPackageServerByBussinessIdPageLsit(Page<PackageServer> page, Integer bussinessId,String name,String status) {
		List<PackageServer> packageServerList = packageServerMapper.selectPackageServerByBussinessIdPageList(page, bussinessId,name,status);
		List<PackageServerVo> packageServerVoVoList = null;
		if(packageServerList.size() > 0){
			packageServerVoVoList = new ArrayList<PackageServerVo>();
			for (PackageServer packageServer : packageServerList) {
				PackageServerVo packageServerVo = new PackageServerVo();
				packageServerVo.setPackageServer(packageServer);
				packageServerVo.setCreateDate(DateUtil.getDatetime(packageServer.getCreateTime()));
				packageServerVo.setModifyDate(DateUtil.getDatetime(packageServer.getModifyTime()));
				packageServerVoVoList.add(packageServerVo);
			}
			page.setResults(packageServerList);
		}
		return packageServerVoVoList;
	}

	@Override
	public PackageServer getPackageServerById(Integer packageId) {
		return packageServerMapper.selectByPrimaryKey(packageId);
	}

	@Override
	public Integer savePackageServer(PackageServer packageServer) {
		try{
			int count = packageServerMapper.insert(packageServer);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer updatePackageServer(PackageServer packageServer) {
		try{
			int count = packageServerMapper.updateByPrimaryKeySelective(packageServer);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer deletePackageServer(Integer packageId) {
		try{
			int count = packageServerMapper.deleteByPrimaryKey(packageId);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Boolean savePackage(PackageServer packageServer, Integer[] serverIdLsit) {
		try{
			int counts = packageServerMapper.insertSelective(packageServer);
			if(counts > 0){
				List<PackageRelServer> packageRelServerList = new ArrayList<PackageRelServer>();
				for (int i = 0; i < serverIdLsit.length; i++) {
					PackageRelServer packageRelServer = new PackageRelServer();
					packageRelServer.setPackageId(packageServer.getId());
					packageRelServer.setServerId(serverIdLsit[i]);
					packageRelServerList.add(packageRelServer);
				}
				int count = packageServerMapper.insertPackageRelServer(packageRelServerList);
				return count > 0;
			}else{
				return false;
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e.fillInStackTrace());
			return false;
		}
	}

	@Override
	public Boolean updatePackage(PackageServer packageServer, Integer[] serverIdLsit) {
		try{
			int counts = packageServerMapper.updateByPrimaryKey(packageServer);
			if(counts > 0){
				List<PackageRelServer> packageRelServerList = new ArrayList<PackageRelServer>();
				for (int i = 0; i < serverIdLsit.length; i++) {
					PackageRelServer packageRelServer = new PackageRelServer();
					packageRelServer.setPackageId(packageServer.getId());
					packageRelServer.setServerId(serverIdLsit[i]);
					packageRelServerList.add(packageRelServer);
				}
				int count = packageServerMapper.insertPackageRelServer(packageRelServerList);
				return count > 0;
			}else{
				return false;
			}
		}catch (Exception e) {
			logger.error(e.getMessage(),e.fillInStackTrace());
			return false;
		}
	}

	@Override
	public Boolean isExistsPackageRelServerByServerId(Integer serverId) {
		int count = packageServerMapper.selectPackageRelServerByServerId(serverId);
		return count > 0;
	}

	@Override
	public Boolean isExistsPackageServerByName(String name, Integer bussinessId) {
		PackageServer packageServer = packageServerMapper.selectPackageServerByname(name, bussinessId);
		return packageServer == null;
	}

}
