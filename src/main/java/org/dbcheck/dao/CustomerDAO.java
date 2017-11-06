package org.dbcheck.dao;

import java.util.List;
import java.util.Map;

import org.cabservice.model.Cab;
import org.cabservice.model.Employee;
import org.dbcheck.model.Customer;

public interface CustomerDAO
{
	
	public Employee findByEmployeeId(int empId);
	public List<Employee> getEmployees();
	
	public Employee createEmployee(Employee emp);
	public Employee updateEmployee(Employee emp) throws Exception;
	
	public Employee deleteEmployee(int empId) throws Exception;
	
	//cabs
	public List<Cab> getCabs();
	public Cab findByCabId(int cabId);
	public Cab createNewCab(Cab cab);
	public Cab updateCabSetails(Cab cab) throws Exception;
	public Cab updateCabStatusSetails(int cabId,String Status)throws Exception;
	public Cab deleteCab(int cabId) throws Exception;
	
	//requests
	public Map<String,String> requestCab(Map<String,String> details);
	public Map<String,String> requestId(int reqId);
	public Map<String, String> bookingId(int bookingId);
	
	
}