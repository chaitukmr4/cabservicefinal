package org.dbcheck.model;

import java.sql.Timestamp;

public class Customer
{
	int custId;
	String name;
	int age;
	public Customer(){
		
	}
public Customer(int custId,String name,int age){
		this.custId=custId;
		this.name=name;
		this.age=age;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "cust_id "+this.custId+" name "+this.name+" age "+this.age;
	}
	//getter and setter methods
	public int getCustId() {
		return this.custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age = age;
	}

}