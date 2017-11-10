package org.cabservice.model;

public class Request {
	private String sourceLocation;
	private String dateTimeOfJourney;
	private String employeeId;
	public String getSourceLocation() {
		return sourceLocation;
	}
	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}
	public String getDateTimeOfJourney() {
		return dateTimeOfJourney;
	}
	public void setDateTimeOfJourney(String dateTimeOfJourney) {
		this.dateTimeOfJourney = dateTimeOfJourney;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
