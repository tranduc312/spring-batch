package com.batch.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class FirstJobListener implements JobExecutionListener
{
	private final Logger logger = LoggerFactory.getLogger(FirstJobListener.class);
	@Override
	public void beforeJob(JobExecution jobExecution)
	{
		logger.info("Before job: {}", jobExecution.getJobInstance().getJobName());
		logger.info("jobExecution.getJobParameters(): {}", jobExecution.getJobParameters());
		logger.info("jobExecution.getExecutionContext(): {}", jobExecution.getExecutionContext());
		jobExecution.getExecutionContext().put("a", "b");
	}

	@Override
	public void afterJob(JobExecution jobExecution)
	{
		logger.info("After job: {}",  jobExecution.getJobInstance().getJobName());
		logger.info("jobExecution.getJobParameters(): {}", jobExecution.getJobParameters());
		logger.info("jobExecution.getExecutionContext(): {}", jobExecution.getExecutionContext());
	}
}
