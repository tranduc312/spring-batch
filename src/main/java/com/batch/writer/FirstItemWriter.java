package com.batch.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


@Component
public class FirstItemWriter implements ItemWriter<Long>
{
	@Override
	public void write(Chunk<? extends Long> chunk) throws Exception
	{
		System.out.println("Inside Item Writer");
		chunk.forEach(System.out::println);
	}
}
