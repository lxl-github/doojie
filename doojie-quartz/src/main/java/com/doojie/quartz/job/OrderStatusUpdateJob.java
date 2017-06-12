package com.doojie.quartz.job;

import com.doojie.domain.po.Order;
import com.doojie.quartz.task.OrderStatusUpdateTask;
import com.doojie.service.service.OrderService;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>爽约订单或未确认订单job</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lixiaoliang
 * @version 1.0
 * @since 1.0
 */
public class OrderStatusUpdateJob implements Job {

    private final static Logger logger = LoggerFactory.getLogger(OrderStatusUpdateJob.class);


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("taskExecutor")
    private ExecutorService taskExecutor;

    private final static ReentrantLock REENTRANT_LOCK = new ReentrantLock();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {

            Boolean getLock = REENTRANT_LOCK.tryLock(2,TimeUnit.MILLISECONDS);
            if(getLock){

                logger.info("开始执行任务");

                List<Callable<Boolean>> taskList = new CopyOnWriteArrayList<Callable<Boolean>>();
                //查询订单状态为待确认并且当前时间超过完成时间24小时或订单状态为下单成功、审核成功并且当前时间超过预约时间24小时
                List<Order> orderList = new LinkedList<Order>();



                for (Order order : orderList){

                    final Long orderId = order.getId();

                    OrderStatusUpdateTask orderStatusUpdateTask = new OrderStatusUpdateTask(applicationContext,orderId);

                    taskList.add(orderStatusUpdateTask);
                }

                List<Future<Boolean>> futureList = taskExecutor.invokeAll(taskList,40, TimeUnit.MINUTES);

                for (Future<Boolean> future : futureList){
                    logger.info("task result {}");
                }
            }else{
                if(logger.isDebugEnabled()){
                    logger.debug("wait for lock ");
                }
            }
        }catch (Exception e){
            logger.error("定时修改订单状态失败");
        }
    }
}
