package com.batch.writer;

import com.batch.model.StudentCsv;
import com.batch.model.StudentJson;
import com.batch.model.StudentXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


@Component
public class FirstItemWriter implements ItemWriter<StudentJson>
{
	private final Logger logger = LoggerFactory.getLogger(FirstItemWriter.class);

	@Override
	public void write(Chunk<? extends StudentJson> chunk) throws Exception
	{
		logger.info("Inside Item Writer");
		chunk.forEach(System.out::println);
	}
}
