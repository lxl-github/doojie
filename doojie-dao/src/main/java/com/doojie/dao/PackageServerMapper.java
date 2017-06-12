package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.bo.PackageRelServer;
import com.doojie.domain.po.PackageServer;

public interface PackageServerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PackageServer record);

    int insertSelective(PackageServer record);

    PackageServer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PackageServer record);

    int updateByPrimaryKey(PackageServer record);
    
    List<PackageServer> selectPackageServerAllPageList(Page<PackageServer> page);
    
    /**
     * 根据商家Id查询套餐列表
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
    List<PackageServer> selectPackageServerByBussinessIdPageList(Page<PackageServer> page,@Param("bussinessId") Integer bussinessId,@Param("name")String name,@Param("status")String status);
    
    /**
     * 保存套餐与服务关系
     * <br>
     * 创建时间：2015年3月12日上午10:44:55
     * <br>
     * @author lixiaoliang
     * <br>
     * @param packageId
     * @param serverId
     * @return
     *
     */
    int insertPackageRelServer(List<PackageRelServer> packageRelServerList);
    
    /**
     * 根据套餐id删除与服务关系
     * <br>
     * 创建时间：2015年3月12日上午11:42:47
     * <br>
     * @author lixiaoliang
     * <br>
     * @param packageId
     * @return
     *
     */
    int deletePackageRelServerByPackageId(Integer packageId);
    
    /**
     * 根据服务Id查询套餐服务关系表中是否存在
     * <br>
     * 创建时间：2015年3月13日上午10:05:46
     * <br>
     * @author lixiaoliang
     * <br>
     * @param serverId
     * @return
     *
     */
    int selectPackageRelServerByServerId(Integer serverId);
    
    PackageServer selectPackageServerByname(@Param("name")String name,@Param("bussinessId")Integer bussinessId);
}