package com.doojie.service.service;

import java.util.List;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.BaseServer;
import com.doojie.domain.po.Server;
import com.doojie.domain.vo.ServerVo;

public interface ServerService {

	List<ServerVo> getServerByBussinessIdPageLsit(Page<Server> page, Integer bussinessId, String name, String status);

	List<Server> getServerByBussinessIdLsit(Integer bussinessId);
	
	Server getServerById(Integer serverId);
	
	Integer saveServer(Server server);
	
	Integer updateServer(Server server);
	
	Integer deleteServer(Integer serverId);
	
	Server getServerByName(String name, Integer bussinessId);
	
	List<BaseServer> getBaseServerByBussinessId(Integer bussinessId);
	
	Integer updateBaseServer(List<BaseServer> baseServerList);
	
	Integer saveBaseServer(List<BaseServer> baseServerList);
}
