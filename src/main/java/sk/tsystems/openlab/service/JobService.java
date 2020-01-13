package sk.tsystems.openlab.service;

import java.util.List;

import sk.tsystems.openlab.entity.Job;

public interface JobService {

	void addJob(Job job);

	List<Job> getAllJobs();
	
	void clearJobs();
}
