package org.cabservice.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cabservice.model.Cab;
import org.cabservice.model.Employee;
import org.dbcheck.dao.CustomerDAO;
import org.dbcheck.model.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class EmployeeService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	CustomerDAO customerDAO = null;
	{
		ApplicationContext context =
	    		new ClassPathXmlApplicationContext("Spring-Module.xml");
	    	
		customerDAO   = (CustomerDAO) context.getBean("customerDAO1");
	}
	
	
	@RequestMapping(value = "/employees", method = RequestMethod.GET,headers="Accept=application/json")
	public ResponseEntity<Object> getEmployees()
	{
		
		List<Employee> listOfEmployees = null;
		//listOfEmployees = new ArrayList<Employee>();// TO COMMENT ADDED FOR TESTING
		logger.info("Getting List of Employees."+customerDAO);
		System.out.println(customerDAO);
		
		listOfEmployees = customerDAO.getEmployees();
		//listOfEmployees.add(this.createEmployee(this.getEmployeeById(1)));// TO COMMENT ADDED FOR TESTING
		return new ResponseEntity<Object>(listOfEmployees,HttpStatus.OK);
	}
	/*
	 * 
	 * public ResponseEntity getEmployees(){
		//HashMap<String,String> hm=new HashMap<String,String>();
		ArrayList<String> hm=new ArrayList<String>();
		hm.add(0, "CHaitu");
		hm.add(1, "CHaitu");
		return new ResponseEntity(hm,HttpStatus.OK);
	}
	*/

	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> getEmployeeById(@PathVariable int employeeId)
	{
		logger.info("Get Employee by ID.");
		
		Employee employee =customerDAO.findByEmployeeId(employeeId);
		
		return new ResponseEntity<Object>(employee,HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> createEmployee(@RequestBody Employee emp){
		logger.info("Start createEmployee.");
		Employee employee =customerDAO.createEmployee(emp);
		return new ResponseEntity<Object>(employee,HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/employee", method = RequestMethod.PUT,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> updateEmployee(@RequestBody Employee emp) {
		logger.info("Update Employee.");
		try {
			emp=customerDAO.updateEmployee(emp);
			return new ResponseEntity<Object>(emp,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(emp,HttpStatus.METHOD_FAILURE);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/employee/{employeeId} ", method = RequestMethod.DELETE,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> deleteEmployee(@PathVariable("employeeId") int empId) {
		logger.info("Start deleteEmployee.");
		try{
			Employee emp=null;
			emp=customerDAO.deleteEmployee(empId);
		return new ResponseEntity<Object>(emp,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error wile deleting employee",HttpStatus.METHOD_FAILURE);
		}
		
	}
	
	
	//Cabs
	
	@RequestMapping(value = "/cabs", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> getCabs()
	{
		logger.info("Get all cabs.");
		//customerDAO1.getClass();
		List<Cab> listOfCabs=customerDAO.getCabs();
		//listOfCountries=createCountryList();
		return new ResponseEntity<Object>(listOfCabs,HttpStatus.OK);
	}

	@RequestMapping(value = "/cabs/{cabId}", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> getCabById(@PathVariable("cabId") int cabId)
	{
		logger.info("Get cab by id.");
		Cab cab = customerDAO.findByCabId(cabId);
		//listOfCountries=createCountryList();
		
		//return this.changeCabStausUnAvailable(cabId); // testing
				return new  ResponseEntity<Object>(cab,HttpStatus.OK);
		
		
		
	}
	
	@RequestMapping(value = "/cabs", method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> addCab(@RequestBody Cab cab){
		logger.info("add Cab.");
		//emp.setCreatedDate(new Date());
		//empData.put(emp.getId(), emp);
		customerDAO.createNewCab(cab);
		return new ResponseEntity<Object>("Created SUcessfully",HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/cabs", method = RequestMethod.PUT,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> updateCab(@RequestBody Cab cab) {
		logger.info("Update Cab.");
		//emp.setCreatedDate(new Date());
		//empData.put(emp.getId(), emp);
		
		try {
			customerDAO.updateCabSetails(cab);
			return new ResponseEntity<Object>(cab,HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>(cab,HttpStatus.METHOD_FAILURE);
		}
		
	}
	
	@RequestMapping(value = "/cabs/{cabId}/available ", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> changeCabStausAvailable(@PathVariable("cabId") int cabId) {
		logger.info("Start changeCabStausAvailable.");
		//Employee emp = empData.get(empId);
		//empData.remove(empId);
		Cab cab=null;
		try {
			cab = customerDAO.updateCabStatusSetails(cabId,"AVAILABLE");
			return new ResponseEntity<Object>(cab,HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return new ResponseEntity<Object>(cab,HttpStatus.METHOD_FAILURE);
		}
		
	}
	
	@RequestMapping(value = "/cabs/{cabId}/unavialble ", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> changeCabStausUnAvailable(@PathVariable("cabId") int cabId) {
		logger.info("Start changeCabStausUnAvailable.");
		//Employee emp = empData.get(empId);
		//empData.remove(empId);
		Cab cab=null;
		try {
			cab = customerDAO.updateCabStatusSetails(cabId,"UNAVAILABL");
			return new ResponseEntity<Object>(cab,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(cab,HttpStatus.METHOD_FAILURE);
		}
		
	}

	
	@RequestMapping(value = "/cabs/{cabId} ", method = RequestMethod.DELETE,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> deleteCab(@PathVariable("cabId") int cabId) {
		logger.info("Start deleteCab.");
		try{
			Cab cab=null;
			cab=customerDAO.deleteCab(cabId);
		return new ResponseEntity<Object>(cab,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error wile deleting cab",HttpStatus.METHOD_FAILURE);
		}
		
	}
	
	
	
	@RequestMapping(value = "/request", method = RequestMethod.POST,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> requestCab(@PathVariable("sourceLocation") String sourceLocation,@PathVariable("dateTimeOfJourney") String dateTimeOfJourney,@PathVariable("employeeId") String employeeId) {
		logger.info("Start requestCab.");
		try{
			Map<String,String> hm= new HashMap<String,String>();
			hm.put("sourceLocation", sourceLocation);
			hm.put("dateTimeOfJourney", dateTimeOfJourney);
			hm.put("employeeId", employeeId);
			hm=customerDAO.requestCab(hm);
			//cab=customerDAO.deleteCab(cabId);
		return new ResponseEntity<Object>(hm,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error wile deleting cab",HttpStatus.METHOD_FAILURE);
		}
		
	}
	//
	@RequestMapping(value = "/request/{requestId}", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> requestId(@PathVariable("requestId") int reqId) {
		logger.info("Start requestId.");
		try{
			Map<String,String> hm=customerDAO.requestId(reqId);
			return new ResponseEntity<Object>(hm,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error wile deleting cab",HttpStatus.METHOD_FAILURE);
		}
		
	}
	@RequestMapping(value = "/booking/{bookingId}", method = RequestMethod.GET,headers="Accept=application/json")
	public @ResponseBody ResponseEntity<Object> bookingId(@PathVariable("bookingId") int bookingId) {
		logger.info("Start requestId.");
		try{
			Map<String,String> hm=customerDAO.bookingId(bookingId);
			return new ResponseEntity<Object>(hm,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Error wile deleting cab",HttpStatus.METHOD_FAILURE);
		}
		
	}

}
