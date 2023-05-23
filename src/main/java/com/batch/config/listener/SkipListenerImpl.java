package com.batch.config.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.File;
import java.io.FileWriter;


public class SkipListenerImpl implements SkipListener<Throwable, String>
{
	@Override
	public void onSkipInRead(Throwable throwable)
	{
		if (throwable instanceof FlatFileParseException)
		{
			createFile("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\Chunk Job\\First Chunk Step\\reader\\SkipInRead.txt",
					((FlatFileParseException) throwable).getInput());
		}
	}

	@Override
	public void onSkipInWrite(String item, Throwable throwable)
	{
		if (throwable instanceof Exception) {
			createFile("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\Chunk Job\\First Chunk Step\\writer\\SkipInWriter.txt",
					throwable.getMessage());
		}
	}

	@Override
	public void onSkipInProcess(Throwable throwable, Throwable t)
	{
		if (throwable instanceof Exception) {
			createFile("D:\\Study\\Udemy\\Spring-Batch\\Source\\spring-batch\\spring-batch\\Chunk Job\\First Chunk Step\\processor\\SkipInProcess.txt",
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
