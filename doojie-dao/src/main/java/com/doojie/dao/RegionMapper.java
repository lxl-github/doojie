package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.domain.po.Region;

public interface RegionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
    
    Region selectRegionByName(@Param("name")String name,@Param("parentId")Integer parentId);
    
    Region selectRegionByLikeName(@Param("name")String name,@Param("parentId")Integer parentId);
    
    Region selectRegionByCode(@Param("code")String code);
    
    List<Region> selectRegionListByParentId(Integer parentId);
    
    List<Region> selectRegionListByLevel(Integer level);
    
    int selectEnabledRegionListByParentId(Integer parentId);
    
    int updateRegionByEnabledAndId(@Param("isEnabled")Integer isEnabled,@Param("id")Integer id);
    
    int updateRegionByDeleted(@Param("isDeleted")Integer isDeleted,@Param("list")List<Region> list);
}