package com.doojie.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.doojie.common.pagination.Page;
import com.doojie.domain.po.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 获取全部用户
     * <br>
     * 创建时间：2015年3月11日下午4:58:00
     * <br>
     * @author lixiaoliang
     * <br>
     * @param page
     * @return
     *
     */
    List<User> selectUserAllPageList(Page<User> page);
    
    /**
     * 根据手机号获取用户
     * <br>
     * 创建时间：2015年3月11日下午4:58:08
     * <br>
     * @author lixiaoliang
     * <br>
     * @param mobile
     * @return
     *
     */
    User selectUserByMobile(String mobile);
    
    /**
     * 根据openId查询用户
     * @param openId
     * @return
     */
    User selectUserByOpenId(String openId);
    
    /**
     * 保存意见反馈
     * <br>
     * 创建时间：2015年3月24日上午9:50:44
     * <br>
     * @author lixiaoliang
     * <br>
     * @param content
     * @param createTime
     * @return
     *
     */
    Integer insertSuggest(@Param("id") Integer id,@Param("content") String content,@Param("createTime")Integer createTime,@Param("userId")Integer userId);
    
    List<Map<String, Object>> selectSuggestPageList(Page<Map<String,Object>> page);
}