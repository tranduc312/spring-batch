package com.batch.config.listener;


import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;


@Component
public class SkipListener
{

	@OnSkipInRead
	public void skipInRead(Throwable throwable)
	{
		if (throwable instanceof FlatFileParseException)
		{
			createFile("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\Chunk Job\\First Chunk Step\\reader\\SkipInRead.txt",
					((FlatFileParseException) throwable).getInput());
		}
	}

	@OnSkipInProcess
	public void skipInProcess(Throwable throwable) {
		if (throwable instanceof Exception) {
			createFile("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\Chunk Job\\First Chunk Step\\processor\\SkipInProcess.txt",
					throwable.getMessage());
		}
	}

	@OnSkipInWrite
	public void skipInWrite(Throwable throwable) {
		if (throwable instanceof Exception) {
			createFile("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\Chunk Job\\First Chunk Step\\writer\\SkipInWriter.txt",
					throwable.getMessage());
		}
	}

	public void createFile(String filePath, String data)
	{
		try(FileWriter fileWriter = new FileWriter(new File(filePath), true)) {
			fileWriter.write(data + "\n");
		} catch (Exception e) {

		}
	}
}
