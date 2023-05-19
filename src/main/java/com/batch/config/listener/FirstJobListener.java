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
		System.out.println("Before job: " + jobExecution.getJobInstance().getJobName());
		System.out.println("jobExecution.getJobParameters(): " + jobExecution.getJobParameters());
		System.out.println("jobExecution.getExecutionContext(): " + jobExecution.getExecutionContext());
		jobExecution.getExecutionContext().put("a", "b");
	}

	@Override
	public void afterJob(JobExecution jobExecution)
	{
		System.out.println("After job: " + jobExecution.getJobInstance().getJobName());
		System.out.println("jobExecution.getJobParameters(): " + jobExecution.getJobParameters());
		System.out.println("jobExecution.getExecutionContext(): " + jobExecution.getExecutionContext());
	}
}
