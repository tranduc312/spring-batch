package com.batch.config;

import com.batch.model.StudentCsv;
import com.batch.config.listener.FirstJobListener;
import com.batch.config.listener.FirstStepListener;
import com.batch.config.step.SecondStep;
import com.batch.constant.Constant;
import com.batch.model.StudentJson;
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
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;


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
				.<StudentJson, StudentJson>chunk(3, transactionManager)
//				.reader(flatFileItemReader()) // reader csv file
				.reader(jsonItemReader()) // reader json file
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

	public FlatFileItemReader<StudentCsv> flatFileItemReader() {
		FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();

		flatFileItemReader.setResource(new FileSystemResource(
				new File("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\inputFiles\\students.csv")));
		flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer(){{
					setNames("ID", "First Name", "Last Name", "Email");
				}});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<>(){{
					setTargetType(StudentCsv.class);
				}});
			}
		});
		flatFileItemReader.setLinesToSkip(1);

		return flatFileItemReader;
	}

	public JsonItemReader<StudentJson> jsonItemReader() {
		JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<>();
		jsonItemReader.setResource(new FileSystemResource(
				new File("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\inputFiles\\students.json")));
		jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));
		return jsonItemReader;
	}
}
