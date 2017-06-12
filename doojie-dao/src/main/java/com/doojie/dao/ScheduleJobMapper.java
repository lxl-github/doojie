package com.doojie.dao;

import java.util.List;

import com.doojie.domain.quartz.ScheduleJob;

public interface ScheduleJobMapper {

	List<ScheduleJob> getAll();
}
