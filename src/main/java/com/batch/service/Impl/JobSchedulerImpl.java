package com.batch.service.Impl;

import com.batch.service.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class JobSchedulerImpl implements JobScheduler
{

	private final Logger logger = LoggerFactory.getLogger(JobSchedulerImpl.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("secondJob")
	private Job secondJob;

	@Override
	@Scheduled(cron = "${cron.expression.job.second.task}")
	public void secondJobStart()
	{
		Map<String, JobParameter<?>> params = new HashMap<>();
		params.put("currentTime", new JobParameter(System.currentTimeMillis(), Long.class));
		JobParameters jobParameters = new JobParameters(params);
		try
		{
			JobExecution jobExecution = jobLauncher.run(secondJob, jobParameters);
			logger.info("Job Execution ID = {}", jobExecution.getJobId());
		} catch (Exception e) {
			logger.error("Exception while starting job");
		}
	}
}
