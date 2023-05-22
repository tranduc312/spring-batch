package com.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


@Component
public class FirstItemWriter implements ItemWriter<Integer>
{
	private final Logger logger = LoggerFactory.getLogger(FirstItemWriter.class);

	@Override
	public void write(Chunk<? extends Integer> chunk) throws Exception
	{
		logger.info("Inside Item Writer");
		chunk.forEach(System.out::println);
	}
}
