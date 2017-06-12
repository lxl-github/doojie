package com.doojie.service;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.doojie.common.util.JsonUtil;
import com.doojie.domain.vo.OrderDetailVo;
import com.doojie.service.service.OrderService;
import com.doojie.service.service.util.RedisCacheServiceUtil;

//使用@RunWith(SpringJUnit4ClassRunner.class),才能使测试运行于Spring测试环境   
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/applicationContext.xml",
})
@TransactionConfiguration(transactionManager = "springTransactionManager", defaultRollback = false)
public class OrderServiceTest {
	
	
	@Autowired
	OrderService orderService;
	
	@Test
	public void getOrderDetailVoByOrderIdTest(){
//		OrderDetailVo orderDetailVo = orderService.getOrderDetailVoByOrderId(1000000l);
//		try {
//			System.out.println(JsonUtil.toJson(orderDetailVo));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
