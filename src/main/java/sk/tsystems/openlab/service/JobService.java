package sk.tsystems.openlab.service;

import java.util.List;

import sk.tsystems.openlab.entity.Job;

public interface JobService {
	List<Job> getAllJobs();
	
	void refreshJobs(List<Job> jobs);
}
