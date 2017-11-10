package org.dbcheck.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;






import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.cabservice.model.Cab;
import org.cabservice.model.Employee;
import org.dbcheck.dao.CustomerDAO;
import org.dbcheck.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class JdbcCabServiceDAO implements CustomerDAO
{
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Employee createEmployee(Employee emp) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO EMPLOYEES "
		+"(FULL_NAME,DESIGNATION,JOINING_DATE,EMAIL,PHONE,ADDRESS) VALUES  "
		+"(?,  ?, ?, ?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getFullName());
			ps.setString(2, emp.getDesignation());
			ps.setString(3, emp.getJoiningDate());
			ps.setString(4, emp.getEmail());
			ps.setString(5, emp.getPhone());
			ps.setString(6, emp.getAddress());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		
		return null;
	}
	
	public void insert(Customer customer){

		String sql = "INSERT INTO CUSTOMER " +
				"(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customer.getCustId());
			ps.setString(2, customer.getName());
			ps.setInt(3, customer.getAge());
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public Employee findByEmployeeId(int empId){

		String sql = "SELECT * FROM EMPLOYEES WHERE EMP_ID = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			Employee employee = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				employee = new Employee();
				
				employee.setId(rs.getString("EMP_ID"));
				employee.setFullName(rs.getString("FULL_NAME"));
				employee.setDesignation(rs.getString("DESIGNATION"));
				employee.setJoiningDate(rs.getString("JOINING_DATE"));
				employee.setEmail(rs.getString("EMAIL"));
				employee.setPhone(rs.getString("PHONE"));
				employee.setAddress(rs.getString("ADDRESS"));
			}
			rs.close();
			ps.close();
			return employee;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public List<Employee> getEmployees(){
		
		String sql = "SELECT * FROM EMPLOYEES";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setInt(1, custId);
			List<Employee> listOfEmployees = new ArrayList<Employee>();
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				Employee employee =  new Employee();
				employee.setId(rs.getString("EMP_ID"));
				employee.setFullName(rs.getString("FULL_NAME"));
				employee.setDesignation(rs.getString("DESIGNATION"));
				employee.setJoiningDate(rs.getString("JOINING_DATE"));
				employee.setEmail(rs.getString("EMAIL"));
				employee.setPhone(rs.getString("PHONE"));
				employee.setAddress(rs.getString("ADDRESS"));

				listOfEmployees.add(employee);
			}
			rs.close();
			ps.close();
			return listOfEmployees;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}

	}

	public Employee updateEmployee(Employee emp) throws Exception {
		
		Employee exisitingEmp = this.findByEmployeeId(Integer.parseInt(emp.getId()));
		String sql = "UPDATE EMPLOYEES SET FULL_NAME='"+emp.getFullName()+"',DESIGNATION='"+emp.getDesignation()+"',JOINING_DATE=SYSDATE(),EMAIL='"+emp.getEmail()+"',PHONE='"+emp.getPhone()+"',ADDRESS='"+emp.getAddress()+"' WHERE EMP_ID = ?";
		
		boolean updateFlag=false;
		Connection conn = null;

		try {
			if(exisitingEmp != null){
			System.out.println("In DAO update employee");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getId());
			
			int updt = ps.executeUpdate();
			if (updt>0) {
					emp.setErrorCode("EMP_UPDATED");
					emp.setErrorMessage("The update is SUCESSFULL");
				
			}else{
				emp.setErrorCode("EMP_UPDATE_FAILED");
				emp.setErrorMessage("Problem updating record");
			}
			
			
			ps.close();
			return emp;
			}else{
				System.out.println("In DAO update employee");
				emp.setErrorCode("DATA_NOT_FOUND");
				emp.setErrorMessage("The entered Id don't have any data, please enter a valid data");
		
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}

		
		return emp;	
		
	}

	public Employee deleteEmployee(int empId) throws Exception {
		
		
		Employee employee  = this.findByEmployeeId(empId);
		
		 String sql = "DELETE  FROM EMPLOYEES WHERE EMP_ID = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			
			int res = ps.executeUpdate();
			if(res > 1){
				employee.setErrorCode("DELETE_SUCESSFULL");
				employee.setErrorMessage("Sucessfully deleted the sepecified record");
			}else{
				employee= new Employee();
				employee.setErrorCode("DELETE_UNSUCESSFULL");
				employee.setErrorMessage("please enter a valid ID");
			}
			ps.close();
			return employee;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
		
		
		
	}

	public List<Cab> getCabs() {
		String sql = "SELECT * FROM CABS";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setInt(1, custId);
			List<Cab> listOfCabs = new ArrayList<Cab>();
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				
				Cab cab= new Cab();
				cab.setCabId(rs.getString("CAB_ID"));
				cab.setCabStatus(rs.getString("CAB_STATUS"));
				cab.setComments(rs.getString("COMMENTS"));
				cab.setDriverId(rs.getString("DRIVER_ID"));
				cab.setRegistrationNumber(rs.getString("REG_NUM"));
				cab.setVacancy(rs.getString("VACANCY"));
				
				listOfCabs.add(cab);
			}
			rs.close();
			ps.close();
			return listOfCabs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}


	}

	public Cab findByCabId(int cabId) {
		String sql = "SELECT * FROM CABS WHERE CAB_ID = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cabId);
			Cab cab = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cab = new Cab();
				
				cab.setCabId(rs.getString("CAB_ID"));
				cab.setCabStatus(rs.getString("CAB_STATUS"));
				cab.setComments(rs.getString("COMMENTS"));
				cab.setDriverId(rs.getString("DRIVER_ID"));
				cab.setRegistrationNumber(rs.getString("REG_NUM"));
				cab.setVacancy(rs.getString("VACANCY"));
			}
			rs.close();
			ps.close();
			return cab;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public Cab createNewCab(Cab cab) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO CABS "
				+"(REG_NUM,DRIVER_ID,CAB_STATUS,COMMENTS) VALUES  "
				+"(?,  ?, ?, ?)";
				Connection conn = null;

				try {
					conn = dataSource.getConnection();
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, cab.getRegistrationNumber());
					ps.setString(2, cab.getDriverId());
					ps.setString(3, cab.getCabStatus());
					ps.setString(4, cab.getComments());
					
					ps.executeUpdate();
					ps.close();

				} catch (SQLException e) {
					throw new RuntimeException(e);

				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e) {}
					}
				}
				
				
		return null;
	}

	public Cab updateCabSetails(Cab cab) throws Exception {
		
		Cab exisitingCab = this.findByCabId(Integer.parseInt(cab.getCabId()));
		String sql = "UPDATE CABS SET REG_NUM='"+cab.getRegistrationNumber()+"',DRIVER_ID='"+cab.getDriverId()+"',CAB_STATUS='"+cab.getCabStatus()+"',VACANCY='"+cab.getVacancy()+"',COMMENTS='"+cab.getComments()+"'  WHERE CAB_ID = ?";
		
		boolean updateFlag=false;
		Connection conn = null;

		try {
			if(exisitingCab != null){
			System.out.println("In DAO update employee");
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cab.getCabId());
			
			int updt = ps.executeUpdate();
			if (updt>0) {
					cab.setErrorCode("EMP_UPDATED");
					cab.setErrorMessage("The update is SUCESSFULL");
				
			}else{
				cab.setErrorCode("EMP_UPDATE_FAILED");
				cab.setErrorMessage("Problem updating record");
			}
			
			
			ps.close();
			return cab;
			}else{
				System.out.println("In DAO update employee");
				cab.setErrorCode("DATA_NOT_FOUND");
				cab.setErrorMessage("The entered Id don't have any data, please enter a valid data");
		
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}

		
		return cab;	
	}

	public Cab deleteCab(int cabId) throws Exception {
		// TODO Auto-generated method stub
		

		Cab cab  = this.findByCabId(cabId);
		
		 String sql = "DELETE  FROM CABS WHERE CAB_ID = ?";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cabId);
			if(cab != null){
			int res = ps.executeUpdate();
				if(res > 1){
					cab.setErrorCode("DELETE_SUCESSFULL");
					cab.setErrorMessage("Sucessfully deleted the sepecified record");
				}else{
					cab.setErrorCode("DELETE_UNSUCESSFULL");
					cab.setErrorMessage("technical issue");
				}
			}
			else{
				cab= new Cab();
				cab.setErrorCode("ID_NOT_AVAILABLE");
				cab.setErrorMessage("please enter a valid ID");
			}
			ps.close();
			return cab;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
		
	}

	public Map<String, String> requestCab(Map<String, String> details) {
		
		String sourceLocation=(String)details.get("sourceLocation");
		String dateTimeOfJourney = (String)details.get("dateTimeOfJourney");
		String employeeId=(String)details.get("employeeId");
		List<String> sourceLocations=new ArrayList<String>();
		sourceLocations.add("India");//Kindly add the respective source locations
		String query = "select ('"+dateTimeOfJourney+"' > SYSDATE() and (date_format('"+dateTimeOfJourney+"', '%H:%i')>'22:00' or  date_format('"+dateTimeOfJourney+"', '%H:%i')<'01:00') or ((WEEKDAY('"+dateTimeOfJourney+"') mod 7) < 4 )) as FLAG";
			Employee emp=this.findByEmployeeId(Integer.parseInt(employeeId));
			Connection conn = null;
			System.out.println(""+query);
			if(emp != null){
				try{
					Map<String,String> hm= new HashMap<String,String>();
					conn = dataSource.getConnection();
					PreparedStatement ps = conn.prepareStatement(query);
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						if(rs.getInt("FLAG") < 1){
							
							hm.put("ERROR_CODE", "INVALID_TRIP_TIME");
							return hm;
						}
						else if(!sourceLocations.contains(sourceLocation)){
							hm.put("ERROR_CODE", "SOURCE_INVALID");
							return hm;
						}
						else if(this.checkReqBef12Hrs(dateTimeOfJourney)){
							
							hm.put("ERROR_CODE", "REQUEST_NOT_POSSIBLE");
							return hm;
						}
						else if(this.checkCabAvailability()){
							hm.put("ERROR_CODE", "CAB_NOT_AVAILABLE");
							return hm;
						}
						else{
							
							String reqId=this.insertRequest(details);
							hm.put("requestId", reqId);
							this.bookCab(details,reqId);
							//this.processBooking(vehicleNum, details, reqId);//to process the request
							return hm;
						}
					}
					
					throw new SQLException();
				}catch (SQLException e) {
					throw new RuntimeException(e);
				} finally {
					if (conn != null) {
						try {
						conn.close();
						} catch (SQLException e) {}
					}
				}
			}
			
			
			
		
		
		
		
		return null;
	}

	public String insertRequest(Map<String,String> details){
		
		String sql = "Insert into REQUEST_BOOKING(SOURCE_LOCATION,EMP_ID,BOOKING_ID,REQ_STATUS,COMMENTS,DATE_TIME_JOURNEY) values(?,?,1,?,?,?)";
		Connection conn = null;
		String requestId="";
		System.out.println("Hello world");
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, details.get("sourceLocation"));//
			ps.setInt(2,Integer.parseInt(details.get("employeeId")) );
			ps.setString(3, "IN PROCESS");
			ps.setString(4, "Request in process");
			ps.setString(5, details.get("dateTimeOfJourney"));
			ps.executeUpdate();
			String reqId="SELECT LAST_INSERT_ID()";
			ps = conn.prepareStatement(reqId);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				requestId=rs.getString(1);
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return requestId;
		
	}
	
	
	public boolean checkReqBef12Hrs(String timeStamp){
		
		boolean flag= false;
		
		String sql = "SELECT abs((UNIX_TIMESTAMP(?) - UNIX_TIMESTAMP(SYSDATE()))/60/60) between 12 and 48 as output";
		Connection conn = null;
		
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, timeStamp);//
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				if(rs.getInt(1)>0)
					flag=false;
				else
					flag=true;
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return flag;
	}
	
	public boolean bookCab(Map<String,String> details,String requestId){
		
		String sql = "SELECT * FROM CABS WHERE REG_NUM IN (SELECT VEH_DET FROM BOOKINGS WHERE SOURCE_LOCATION='"+details.get("sourceLocation")+"' AND DATE_TIME_JOURNEY = '"+details.get("dateTimeOfJourney")+"') AND VACANCY >= 1 AND CAB_STATUS='AVAILABLE'";
		Connection conn = null;
		boolean bookinCabsAvialble= false;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setString(1, timeStamp);//
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bookinCabsAvialble=true;
				this.processBooking(rs.getString("REG_NUM"),details,requestId);
			}else{
				bookinCabsAvialble=true;
				//if no cabs were booked to that source loc then getting a new Cab
				String sqlQuery= "select * FROM CABS WHERE CAB_ID NOT IN(SELECT CAB_ID FROM CABS WHERE REG_NUM IN (SELECT VEH_DET FROM BOOKINGS WHERE SOURCE_LOCATION='"+details.get("sourceLocation")+"' AND DATE_TIME_JOURNEY = '"+details.get("dateTimeOfJourney")+"') AND VACANCY >= 1 AND CAB_STATUS='AVAILABLE') AND VACANCY >= 1 AND CAB_STATUS='AVAILABLE'";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setString(1, timeStamp);//
				rs=ps.executeQuery();
				if(rs.next()){
					this.processBooking(rs.getString("REG_NUM"),details,requestId);
				}
			}
				
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	
		
		return bookinCabsAvialble;
	}
	public void processBooking(String vehicleNum,Map<String,String> details,String reqId){
		//based on vehicle no update vacancy 
		//add booking in bookings table
		//update bookingId for request
		
		
		String sql = "SELECT * FROM CABS WHERE REG_NUM = '"+vehicleNum+"'";
		Connection conn = null;
		
		int driverId= 0;
		String passengrDet=((Employee)this.findByEmployeeId(Integer.parseInt(details.get("employeeId")))).toString();
		String driverDet="";
		String bookingId= "";
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setString(1, timeStamp);//
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				driverId=rs.getInt("DRIVER_ID");
			}
			driverDet=((Employee)this.findByEmployeeId(driverId)).toString();
			
			//Update the vehicle vacancy
			sql="UPDATE CABS SET VACANCY = VACANCY-1 WHERE REG_NUM = '"+vehicleNum+"'";
			ps = conn.prepareStatement(sql);
			if(ps.executeUpdate()>0){
				//add bookings in table
				sql="INSERT INTO BOOKINGS(SOURCE_LOCATION,DATE_TIME_JOURNEY,BOOK_STATUS,PASS_DET,VEH_DET,DRIVER_DET) VALUES(?,?,'CONFIRMED',?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, details.get("sourceLocation"));
				ps.setString(2, details.get("dateTimeOfJourney"));
				ps.setString(3, passengrDet);
				ps.setString(4, vehicleNum);
				ps.setString(5, driverDet);
				ps.executeUpdate();
			}
			//to get booking Id
			String bookingIdQuery="SELECT LAST_INSERT_ID()";
			ps = conn.prepareStatement(bookingIdQuery);
			rs=ps.executeQuery();
			if(rs.next())
				bookingId=rs.getString(1);
			//update bookingId for request
			String updateBookingID="update REQUEST_BOOKING SET BOOKING_ID = "+bookingId+" , REQ_STATUS='GENERATED' WHERE REQ_ID= "+reqId+" ";
			ps = conn.prepareStatement(updateBookingID);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	

	}
	
	public boolean checkCabAvailability(){
		boolean flag=true;
		
		String sql = "SELECT * FROM CABS WHERE CAB_STATUS= 'AVAILABLE' and VACANCY >= 1";
		Connection conn = null;
		
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
				flag=false;
			ps.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return flag;

	}

	public Map<String, String> requestId(int reqId) {
		String sql = "SELECT * FROM REQUEST_BOOKING WHERE REQ_ID = ?";
		Map<String,String> hm= new HashMap<String,String>();
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, reqId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				hm.put("requestId", rs.getString("REQ_ID"));
				hm.put("requestStatus", rs.getString("REQ_STATUS"));
				hm.put("comments", rs.getString("COMMENTS"));
				hm.put("bookingId", rs.getString("BOOKING_ID"));
				hm.put("sourceLocation", rs.getString("SOURCE_LOCATION"));
				hm.put("dateTimeOfJourney", rs.getString("DATE_TIME_JOURNEY"));
				hm.put("requestCreationDate", "today");
				hm.put("requestGenerator", ((Employee)this.findByEmployeeId(rs.getInt("EMP_ID"))).toString());
				
			}
			rs.close();
			ps.close();
			return hm;
		} catch (SQLException e) {
			System.out.println("Excedption"+e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public Map<String, String> bookingId(int bookingId) {
		String sql = "SELECT * from BOOKINGS WHERE BOOKING_ID = ?";
		Map<String,String> hm= new HashMap<String,String>();
		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bookingId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				hm.put("bookingId", rs.getString("BOOKING_ID"));
				hm.put("sourceLocation", rs.getString("SOURCE_LOCATION"));
				hm.put("dateTimeOfjhourney", rs.getString("DATE_TIME_JOURNEY"));
				hm.put("bookingStatus", rs.getString("BOOK_STATUS"));
				hm.put("passengerDetails", rs.getString("PASS_DET"));
				hm.put("vehicleDetails", rs.getString("VEH_DET"));
				hm.put("driverDetails", rs.getString("DRIVER_DET"));
				
			}
			rs.close();
			ps.close();
			return hm;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public Cab updateCabStatusSetails(int cabId, String Status) throws Exception {
		// TODO Auto-generated method stub
		
		 ;
		 
		 Cab cab = this.findByCabId(cabId);
		 
			boolean updateFlag=false;
			Connection conn = null;

			try {
				if(cab != null){
				System.out.println("In DAO update employee");
				conn = dataSource.getConnection();
				String sql = "UPDATE CABS SET CAB_STATUS = '"+Status+"' WHERE CAB_ID = ?";
				PreparedStatement ps;
				if("UNAVAILABL".equalsIgnoreCase(Status)){
					String sqlQuery="SELECT COUNT(*) FROM BOOKINGS WHERE VEH_DET = ?";
					conn = dataSource.getConnection();
					ps = conn.prepareStatement(sqlQuery);
					ps.setString(1, cab.getRegistrationNumber());
					ResultSet rs=ps.executeQuery();
					if(rs.next()){
						int count=rs.getInt(1);
						sqlQuery="SELECT * FROM CABS WHERE CAB_STATUS='AVAILABLE' AND VACANCY >="+count+" ";
						//conn = dataSource.getConnection();
						ps = conn.prepareStatement(sqlQuery);
						rs=ps.executeQuery();
						if(rs.next()){
							updateFlag=true;
							String updateVehNum=rs.getString("REG_NUM");
							sqlQuery="UPDATE BOOKINGS SET VEH_DET = '"+updateVehNum+"' WHERE VEH_DET = '"+cab.getRegistrationNumber()+"'";
							ps = conn.prepareStatement(sqlQuery);
							ps.executeUpdate();
						}
					}else{
						updateFlag=true;
					}
				}
				ps = conn.prepareStatement(sql);
				ps.setString(1, cab.getCabId());
				if(("UNAVAILABL".equalsIgnoreCase(Status) && updateFlag) || "AVAILABLE".equalsIgnoreCase(Status)){
				int updt = ps.executeUpdate();
				if (updt>0) {
					cab.setErrorCode("CAB_UPDATED");
					cab.setErrorMessage("The update is SUCESSFULL");
					
				}else{
					cab.setErrorCode("CAB_UPDATE_FAILED");
					cab.setErrorMessage("Problem updating record");
				}
				
				}else{
					System.out.println("In DAO update employee");
					cab.setErrorCode("CABS_NOT_AVAILABLE");
					cab.setErrorMessage("cannot set as Unavailable");
		
				}
				ps.close();
				return cab;
				}else{
					System.out.println("In DAO update employee");
					cab.setErrorCode("DATA_NOT_FOUND");
					cab.setErrorMessage("The entered Id don't have any data, please enter a valid data");
			
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				if (conn != null) {
					try {
					conn.close();
					} catch (SQLException e) {}
				}
			}

			
			return cab;	

		
	}
}