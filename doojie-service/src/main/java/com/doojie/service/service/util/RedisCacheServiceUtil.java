package com.doojie.service.service.util;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * 功能概述： 封装redis 缓存服务器服务接口 <br/>
 * 创建时间：2015年7月3日上午11:38:59 <br/>
 * 修改记录：
 * 
 * @author lixiaoliang
 */
@Service
public class RedisCacheServiceUtil {
	
    @Autowired
    private RedisTemplate<String, Object> template;

    private ValueOperations<String, Object> operations;

    /**
     * 默认过期时间30分钟
     */
    private static final Long EXPIRE_TIME = 30l;
    
    @PostConstruct
    public void init() {
        operations = template.opsForValue();
    }

    public synchronized void set(String key, Object value) {
        operations.set(key, value);
        template.expire(key, EXPIRE_TIME, TimeUnit.MINUTES);
    }
    
    public synchronized void set(String key, Object value,Long expireTime) {
        operations.set(key, value);
        template.expire(key, expireTime, TimeUnit.SECONDS);
    }

    public synchronized Object get(String key) {
        return operations.get(key);
    }
    
    public synchronized void del(String key){
    	template.delete(key);
    }
    
    

}
