package com.batch.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class FirstStepListener implements StepExecutionListener
{
	private final Logger logger = LoggerFactory.getLogger(FirstStepListener.class);
	@Override
	public void beforeStep(StepExecution stepExecution)
	{
		logger.info("Before step: {}", stepExecution.getStepName());
		logger.info("stepExecution.getJobParameters(): {}", stepExecution.getJobParameters());
		logger.info("stepExecution.getExecutionContext(): {}", stepExecution.getExecutionContext());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution)
	{
		logger.info("After step: {}", stepExecution.getStepName());
		logger.info("stepExecution.getJobParameters(): {}", stepExecution.getJobParameters());
		logger.info("stepExecution.getExecutionContext(): {}", stepExecution.getExecutionContext());
		return ExitStatus.COMPLETED;
	}
}
