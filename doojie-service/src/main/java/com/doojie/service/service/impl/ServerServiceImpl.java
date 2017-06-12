package com.doojie.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doojie.common.pagination.Page;
import com.doojie.common.util.DateUtil;
import com.doojie.dao.ServerMapper;
import com.doojie.domain.po.BaseServer;
import com.doojie.domain.po.Server;
import com.doojie.domain.vo.ServerVo;
import com.doojie.service.service.ServerService;

@Service
public class ServerServiceImpl implements ServerService {

	
	private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);
	
	@Autowired
	private ServerMapper serverMapper;
	
	@Override
	public List<ServerVo> getServerByBussinessIdPageLsit(Page<Server> page, Integer bussinessId,String name,String status) {
		List<Server> serverList = serverMapper.selectServerByBussinessIdPageList(page, bussinessId,name,status);
		List<ServerVo> serverVoList = null;
		if(serverList.size() > 0){
			serverVoList = new ArrayList<ServerVo>();
			for (Server server : serverList) {
				ServerVo serverVo = new ServerVo();
				serverVo.setServer(server);
				serverVo.setCreateDate(DateUtil.getDatetime(server.getCreateTime()));
				serverVo.setModifyDate(DateUtil.getDatetime(server.getModifyTime()));
				serverVoList.add(serverVo);
			}
			page.setResults(serverList);
		}
		page.setResults(serverList);
		return serverVoList;
	}

	@Override
	public Server getServerById(Integer serverId) {
		return serverMapper.selectByPrimaryKey(serverId);
	}

	@Override
	public Integer saveServer(Server server) {
		try{
			int count = serverMapper.insert(server);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer updateServer(Server server) {
		try{
			int count = serverMapper.updateByPrimaryKeySelective(server);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public Integer deleteServer(Integer serverId) {
		try{
			int count = serverMapper.deleteByPrimaryKey(serverId);
			return count;
		}catch (Exception e) {
			logger.error(e.toString(),e.fillInStackTrace());
			return 0;
		}
	}

	@Override
	public List<Server> getServerByBussinessIdLsit(Integer bussinessId) {
		return serverMapper.selectServerByBussinessIdList(bussinessId);
	}

	@Override
	public Server getServerByName(String name,Integer bussinessId) {
		return serverMapper.selectServerByname(name,bussinessId);
	}

	@Override
	public List<BaseServer> getBaseServerByBussinessId(Integer bussinessId) {
		return serverMapper.selectBaseServerByBussinessId(bussinessId);
	}

	@Override
	public Integer updateBaseServer(List<BaseServer> baseServerList) {
		return serverMapper.updateBaseServer(baseServerList);
	}

	@Override
	public Integer saveBaseServer(List<BaseServer> baseServerList) {
		return serverMapper.insertBaseServer(baseServerList);
	}

}
