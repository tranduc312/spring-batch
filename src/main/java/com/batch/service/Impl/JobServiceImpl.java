package com.batch.service.Impl;

import com.batch.request.JobParamRequest;
import com.batch.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class JobServiceImpl implements JobService
{

	private final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("firstJob")
	private Job firstJob;

	@Autowired
	@Qualifier("secondJob")
	private Job secondJob;

	@Autowired
	private JobOperator jobOperator;


	@Async
	@Override
	public void startJob(String jobName, List<JobParamRequest> jobParamRequestList) {

		Map<String, JobParameter<?>> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis(), Long.class));
		jobParamRequestList.forEach(jobParam -> {
			params.put(jobParam.getKey(), new JobParameter<>(jobParam.getValue(), String.class));
		});
		JobParameters jobParameters = new JobParameters(params);
		try
		{
			JobExecution jobExecution = null;
			if (StringUtils.pathEquals("firstJob", jobName)) {
				jobExecution = jobLauncher.run(firstJob, jobParameters);
			} else if (StringUtils.pathEquals("secondJob", jobName)) {
				jobExecution = jobLauncher.run(secondJob, jobParameters);
			}
			logger.info("Job Execution ID = {}", jobExecution.getJobId());
		} catch (Exception e) {
			logger.error("Exception while starting job");
		}
	}

	@Override
	public void stop(Long executionId)
	{
		try
		{
			jobOperator.stop(executionId);
		} catch (Exception e) {
			logger.error("Exception while stopped job");
		}
	}
}
