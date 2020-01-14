package sk.tsystems.openlab.entity;

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
	private String startDate;
	private String endDate;

	public Job(String position, String employmentType, String startDate, String endDate) {
		this.position = position;
		this.employmentType = employmentType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Job() {

	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}