package com.doojie.common.cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.doojie.service.service.util.RedisCacheServiceUtil;

//使用@RunWith(SpringJUnit4ClassRunner.class),才能使测试运行于Spring测试环境   
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/applicationContext.xml",
})
@TransactionConfiguration(transactionManager = "springTransactionManager", defaultRollback = false)
public class RedisCacheServiceUtilTest {
	
//	@Autowired
//	RedisCacheServiceUtil redisCacheServiceUtil; 
	
	@Test
	public void redisTest(){
//		redisCacheServiceUtil.set("a","123");
//		System.out.println(redisCacheServiceUtil.get("a"));
	}
}
