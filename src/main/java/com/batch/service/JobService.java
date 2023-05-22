package com.batch.service;

import com.batch.request.JobParamRequest;

import java.util.List;


public interface JobService
{
	void startJob(String jobName, List<JobParamRequest> jobParamRequestList);
}
