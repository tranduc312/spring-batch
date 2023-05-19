package com.batch.config.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class FirstStepListener implements StepExecutionListener
{
	@Override
	public void beforeStep(StepExecution stepExecution)
	{
		System.out.println("Before step: " + stepExecution.getStepName());
		System.out.println("jobExecution.getJobParameters(): " + stepExecution.getJobParameters());
		System.out.println("jobExecution.getExecutionContext(): " + stepExecution.getExecutionContext());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution)
	{
		System.out.println("After step: " + stepExecution.getStepName());
		System.out.println("jobExecution.getJobParameters(): " + stepExecution.getJobParameters());
		System.out.println("jobExecution.getExecutionContext(): " + stepExecution.getExecutionContext());
		return ExitStatus.COMPLETED;
	}
}
