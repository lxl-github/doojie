package com.doojie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.EmployeeWorkRecord;
import com.doojie.domain.vo.EmployeeWorkRecordVo;

public interface EmployeeWorkRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmployeeWorkRecord record);

    int insertSelective(EmployeeWorkRecord record);

    EmployeeWorkRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmployeeWorkRecord record);

    int updateByPrimaryKey(EmployeeWorkRecord record);
    
    List<EmployeeWorkRecordVo> selectEmployeeWorkRecordVoPageList(Page<EmployeeWorkRecordVo> page,@Param("number")String number);
    
    EmployeeWorkRecordVo selectEmployeeWorkRecordVoByOrderId(Long orderId);
}