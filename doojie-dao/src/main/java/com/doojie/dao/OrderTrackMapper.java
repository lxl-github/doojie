package com.doojie.dao;

import java.util.List;

import com.doojie.domain.po.OrderTrack;

public interface OrderTrackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderTrack record);

    int insertSelective(OrderTrack record);

    OrderTrack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderTrack record);

    int updateByPrimaryKey(OrderTrack record);
    
    int insertBatch(List<OrderTrack> list);
}