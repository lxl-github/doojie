package com.doojie.quartz.job;

import com.doojie.domain.quartz.ScheduleJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 功能概述：自动分配工作job
 * <br>
 * 创建时间：2015年8月22日下午5:06:23
 * <br>
 * 修改记录：
 * <br>
 * @author 李晓亮
 * <br>
 */
public class AutoSchedulingWorkJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("任务成功运行>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
		
	}
	
	

}
