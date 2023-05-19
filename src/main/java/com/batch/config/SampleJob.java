package com.batch.config;

import com.batch.config.listener.FirstJobListener;
import com.batch.config.listener.FirstStepListener;
import com.batch.config.step.SecondStep;
import com.batch.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class SampleJob
{

	private final Logger logger = LoggerFactory
			.getLogger(SampleJob.class);
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private SecondStep secondStep;

	@Autowired
	private FirstJobListener firstJobListener;

	@Autowired
	private FirstStepListener firstStepListener;

	@Bean
	public Job firstJob() {
		return new JobBuilder(Constant.Job.FIRST_JOB, jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(secondStep())
				.listener(firstJobListener)
				.build();
	}

	private Step secondStep() {
		return new StepBuilder(Constant.Step.SECOND_STEP, jobRepository)
				.tasklet(secondStep, transactionManager)
				.build();
	}

	private Step firstStep() {
		return new StepBuilder(Constant.Step.FIRST_STEP, jobRepository)
				.tasklet(firstTask(), transactionManager)
				.tasklet(secondTask(), transactionManager)
				.tasklet(thirdTask(), transactionManager)
				.listener(firstStepListener)
				.build();
	}

	private Tasklet firstTask() {
		return (contribution, chunkContext) -> {
			logger.info("contribution.getClass() {} ", contribution.getClass());
			System.err.println("this is first tasklet step");
			return RepeatStatus.FINISHED;
		};
	}

	private Tasklet secondTask() {
		return (contribution, chunkContext) -> {
			logger.info("contribution.getClass() {} ", contribution.getClass());
			System.err.println("this is second tasklet step");
			return RepeatStatus.FINISHED;
		};
	}

	private Tasklet thirdTask() {
		return (contribution, chunkContext) -> {
			logger.info("contribution.getClass() {} ", chunkContext.getStepContext());
			System.err.println("this is third tasklet step");
			return RepeatStatus.FINISHED;
		};
	}
}
