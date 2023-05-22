package com.batch.config;

import com.batch.config.listener.FirstJobListener;
import com.batch.config.listener.FirstStepListener;
import com.batch.config.step.SecondStep;
import com.batch.constant.Constant;
import com.batch.processor.FirstItemProcessor;
import com.batch.reader.FirstItemReader;
import com.batch.writer.FirstItemWriter;
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
import org.springframework.core.task.SimpleAsyncTaskExecutor;
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

	@Autowired
	private FirstItemReader firstItemReader;

	@Autowired
	private FirstItemProcessor firstItemProcessor;

	@Autowired
	private FirstItemWriter firstItemWriter;


	@Bean(name = "firstJob")
	public Job firstJob() {
		return new JobBuilder(Constant.Job.FIRST_JOB, jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(firstStep())
				.next(secondStep())
				.listener(firstJobListener)
				.build();
	}

	@Bean(name = "secondJob")
	public Job secondJob() {
		return new JobBuilder("Second Job", jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(firstChunkStep())
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
				.listener(firstStepListener)
				.build();
	}

	private Step firstChunkStep() {
		return new StepBuilder("first Chunk Step", jobRepository)
				.<Integer, Integer>chunk(3, transactionManager)
				.reader(firstItemReader)
//				.processor(firstItemProcessor)
				.writer(firstItemWriter)
				.listener(firstStepListener)
				.build();
	}

	private Tasklet firstTask() {
		return (contribution, chunkContext) -> {
			logger.info("contribution.getClass() {} ", contribution.getClass());
			logger.info("this is first tasklet step");
			return RepeatStatus.FINISHED;
		};
	}

	private Tasklet secondTask() {
		return (contribution, chunkContext) -> {
			logger.info("contribution.getClass() {} ", contribution.getClass());
			logger.info("this is second tasklet step");
			return RepeatStatus.FINISHED;
		};
	}

	private Tasklet thirdTask() {
		return (contribution, chunkContext) -> {
			logger.info("contribution.getClass() {} ", chunkContext.getStepContext());
			logger.info("this is third tasklet step");
			return RepeatStatus.FINISHED;
		};
	}
}
