package org.cabservice.model;

public class Cab implements java.io.Serializable{
	private String cabId;
	private String registrationNumber;
	private String driverId;
	private String cabStatus;
	private String comments;
	private String vacancy;
	
	private String errorMessage;
	private String errorCode;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " cabId : "+this.cabId+" registrationNumber : "+this.registrationNumber+" driverId : "+this.driverId+" cabStatus : "+this.cabStatus+" comments : "+this.comments+" vacancy : "+this.vacancy;
	}
	
	public String getCabId() {
		return cabId;
	}
	public void setCabId(String cabId) {
		this.cabId = cabId;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	public String getCabStatus() {
		return cabStatus;
	}
	public void setCabStatus(String cabStatus) {
		this.cabStatus = cabStatus;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getVacancy() {
		return vacancy;
	}
	public void setVacancy(String vacancy) {
		this.vacancy = vacancy;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
