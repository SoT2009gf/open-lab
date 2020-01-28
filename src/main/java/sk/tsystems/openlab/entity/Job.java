package sk.tsystems.openlab.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Job {

	@Id
	@GeneratedValue
	private int ident;
	private String position;
	private String employmentType;
	private String applicationDeadline;
	@Column(length = 10000)
	private String accountabilities;
	private String salary;
	@Column(length = 8000)
	private String requirements;
	
	public Job(String position, String employmentType, String applicationDeadline, String accountabilities, String salary, String requirements) {
		this.position = position;
		this.employmentType = employmentType;
		this.applicationDeadline = applicationDeadline;
		this.accountabilities = accountabilities;
		this.salary = salary;
		this.requirements = requirements;
	}

	public Job() {

	}

	public int getIdent() {
		return ident;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(String endDate) {
		this.applicationDeadline = endDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getAccountabilities() {
		return accountabilities;
	}

	public void setAccountabilities(String accountabilities) {
		this.accountabilities = accountabilities;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

}