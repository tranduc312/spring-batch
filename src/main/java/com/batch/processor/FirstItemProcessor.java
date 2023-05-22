package com.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class FirstItemProcessor implements ItemProcessor<Integer, Long>
{
	private final Logger logger = LoggerFactory.getLogger(FirstItemProcessor.class);
	@Override
	public Long process(Integer item) throws Exception
	{
		logger.info("Inside Item Processor");
		return Long.valueOf(item);
	}
}
