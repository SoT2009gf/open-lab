package sk.tsystems.openlab.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import sk.tsystems.openlab.entity.Job;

@Component
@Transactional
public class JobServiceJPA implements JobService {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Job> getAllJobs() {
		try {
			return (List<Job>)entityManager.createQuery("select j from Job j").getResultList(); 
		} catch(NoResultException ex) {
			return null;	
		}		
	}
	
	public void refreshJobs(List<Job> jobs) {
		entityManager.createQuery("delete from Job j").executeUpdate();
		for(Job job : jobs)
			entityManager.persist(job);
	}
}
