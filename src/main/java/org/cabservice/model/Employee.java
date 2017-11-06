package org.cabservice.model;

public class Employee implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4799362175569953392L;
	private String id;
	private String fullName;
	private String designation;
	private String joiningDate;
	private String email;
	private String phone;
	private String address;
	private String errorMessage;
	private String errorCode;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id : "+this.id+" fullName : "+this.fullName+" designation : "+this.designation+" joiningDate : "+this.joiningDate+" email : "+this.email+" phone : "+this.phone+" address : "+this.address;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getErrorMessage() {
		return this.errorMessage;
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
