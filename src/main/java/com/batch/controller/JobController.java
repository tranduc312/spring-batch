package com.batch.controller;

import com.batch.request.JobParamRequest;
import com.batch.service.Impl.JobServiceImpl;
import com.batch.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/job")
public class JobController
{

	@Autowired
	private JobService jobService;

	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName
			, @RequestBody List<JobParamRequest> jobParamRequestList)
	{
		jobService.startJob(jobName, jobParamRequestList);
		return "Job Start...";
	}
}
