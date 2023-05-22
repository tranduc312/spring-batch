package com.batch.config.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;


@Service
public class SecondStep implements Tasklet
{

	private final Logger logger = LoggerFactory.getLogger(SecondStep.class);

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
	{
		logger.info("Four step");
		return RepeatStatus.FINISHED;
	}
}
