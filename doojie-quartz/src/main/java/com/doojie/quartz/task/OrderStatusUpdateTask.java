package com.doojie.quartz.task;

import com.doojie.service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 功能概述：定时修改订单状态任务，用于未确认或爽约订单
 * <br>
 * 创建时间：2015年12月6日下午10:28:39
 * <br>
 * 修改记录：
 * <br>
 * @author 李晓亮
 * <br>
 */

public class OrderStatusUpdateTask implements Callable<Boolean>{

	
	private static final Logger logger = LoggerFactory
			.getLogger(OrderStatusUpdateTask.class);

    private ApplicationContext applicationContext;

    private Long orderId;

    public OrderStatusUpdateTask(ApplicationContext context,Long orderId){
        applicationContext = context;
        orderId = orderId;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            OrderService orderService = (OrderService) applicationContext.getBean("orderService");


        }catch (Exception e){
            logger.error("");
        }

        return null;
    }
}
