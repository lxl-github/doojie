package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.domain.po.TradeRecord;
import com.doojie.domain.vo.TradeRecordVo;

public interface TradeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeRecord record);

    int insertSelective(TradeRecord record);

    TradeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeRecord record);

    int updateByPrimaryKey(TradeRecord record);
    
    List<TradeRecord> selectByUserId(Integer userId);
    
    TradeRecord selectByTradeCode(String tradeCode);
    
    TradeRecordVo selectTradeRecordVoByUserIdAndTradeRecordId(@Param("userId") Integer userId,@Param("tradeRecordId") Integer tradeRecordId);
    
    Integer selectTradeRecoredNextId();
}