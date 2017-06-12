package com.doojie.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.Bussiness;

public interface BussinessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bussiness record);

    int insertSelective(Bussiness record);

    Bussiness selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bussiness record);

    int updateByPrimaryKey(Bussiness record);
    
    /**
     * 根据商家登录名查询商家信息
     * <br>
     * 创建时间：2015年3月11日下午4:37:04
     * <br>
     * @author lixiaoliang
     * <br>
     * @param userName
     * @return
     *
     */
    Bussiness selectBussinessByuserName(String userName);
    
    /**
     * 获取所有商家信息
     * <br>
     * 创建时间：2015年3月11日下午4:47:30
     * <br>
     * @author lixiaoliang
     * <br>
     * @param page
     * @return
     *
     */
    List<Bussiness> selectBussinessAllPageList(Page<Bussiness> page,@Param("username")String username,@Param("name") String name);
    
    /**
     * 根据区域查询商家 用于前台
     * @param province
     * @param city
     * @param county
     * @return
     */
    List<Bussiness> selectBussinessAllList(@Param("province") String province,@Param("city") String city,@Param("county")Integer county,@Param("area") String area);
    
    List<Bussiness> selectBussinessAllsPageList(Page<Bussiness> page,@Param("province") String province,@Param("city") String city,@Param("county")Integer county,@Param("area") String area);
    
    /**
     * 查询所有被推荐的商家，根据推荐数倒序 
     * @return
     */
    List<Map<String,Object>> selectRecommendBussinessAllList(@Param("area") String area);
}