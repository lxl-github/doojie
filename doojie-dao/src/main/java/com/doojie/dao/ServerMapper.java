package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.BaseServer;
import com.doojie.domain.po.Server;

public interface ServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Server record);

    int insertSelective(Server record);

    Server selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Server record);

    int updateByPrimaryKey(Server record);
    
    List<Server> selectServerAllPageList(Page<Server> page);
    
    /**
     * 根据商家Id查询服务分页列表
     * <br>
     * 创建时间：2015年3月11日下午4:53:50
     * <br>
     * @author lixiaoliang
     * <br>
     * @param page
     * @param bussinessId
     * @return
     *
     */
    List<Server> selectServerByBussinessIdPageList(Page<Server> page,@Param("bussinessId") Integer bussinessId,@Param("name") String name,@Param("status") String status);
    
    /**
     * 根据商家Id查询启用服务列表 用于套餐添加
     * <br>
     * 创建时间：2015年3月12日上午10:17:06
     * <br>
     * @author lixiaoliang
     * <br>
     * @param page
     * @param bussinessId
     * @return
     *
     */
    List<Server> selectServerByBussinessIdList(Integer bussinessId);
    
    /**
     * 根据服务名称查询服务
     * <br>
     * 创建时间：2015年3月13日上午11:37:53
     * <br>
     * @author lixiaoliang
     * <br>
     * @param name
     * @return
     *
     */
    Server selectServerByname(@Param("name")String name,@Param("bussinessId")Integer bussinessId);
    
    /**
     * 根据商家Id查询基础洗车服务
     * <br>
     * 创建时间：2015年3月13日下午4:12:37
     * <br>
     * @author lixiaoliang
     * <br>
     * @param bussinessId
     * @return
     *
     */
    List<BaseServer> selectBaseServerByBussinessId(Integer bussinessId);
    
    /**
     * 批量插入基础洗车服务
     * <br>
     * 创建时间：2015年3月13日下午4:42:22
     * <br>
     * @author lixiaoliang
     * <br>
     * @param baseServerList
     * @return
     *
     */
    int insertBaseServer(List<BaseServer> baseServerList);
    
    /**
     * 批量修改基础洗车服务
     * <br>
     * 创建时间：2015年3月13日下午4:42:37
     * <br>
     * @author lixiaoliang
     * <br>
     * @param baseServerList
     * @return
     *
     */
    int updateBaseServer(List<BaseServer> baseServerList);
}