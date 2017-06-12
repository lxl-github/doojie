package com.doojie.quartz.scheduler;

import java.util.List;

import org.quartz.SchedulerException;

import com.doojie.domain.quartz.ScheduleJob;

/**
 * 功能概述：计划任务接口类
 * <br>
 * 创建时间：2015年12月6日下午11:47:46
 * <br>
 * 修改记录：
 * <br>
 * @author 李晓亮
 * <br>
 */
public interface ScheduleJobService {

	
	/**
	 * 启动加载
	 * @throws Exception
	 */
	void init() throws Exception;
	
	/**
	 * 获取所有计划中的任务列表
	 * @return
	 * @throws SchedulerException
	 */
	List<ScheduleJob> getAllJob() throws SchedulerException;
	
	/**
	 * 获取所有正在运行的任务列表
	 * @return
	 * @throws SchedulerException
	 */
	List<ScheduleJob> getRunningJob() throws SchedulerException;
	
	/**
	 * 添加一个任务
	 * @param job
	 * @throws SchedulerException
	 */
	void addJob(ScheduleJob job) throws SchedulerException, ClassNotFoundException, IllegalAccessException, InstantiationException;
	
	/**
	 * 暂停一个任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void pauseJob(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 恢复一个任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void resumeJob(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 删除一个任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void deleteJob(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 立即执行任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException;
	
	/**
	 * 更新job时间表达式
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException;
}
