package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.PurSalAutoServer.Entities.Company;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

/**
 * <p>for database operation related in caompany details
 * The sample json object for the company details operation is
 * <b>
 * {
 *  "companyaddress": "sample",
 *  "companymailid": "sample",
 *  "companyname": "sample",
 *  "companyphno": "56789",
 *  "id": 0
 * }</b></p> 
 * @author gokul
 *
 */
public class CompanyBean {
	Connection conn;
	
	public CompanyBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	/**
	 * used to close the sql connection on the bean object
	 * @throws SQLException
	 */
	public void closecommection() throws SQLException {
		conn.close();
	}

	/**
	 * <p>used to get the company details from the database
	 * @param uid for which the company details hava to be added
	 * @return company object with the queried user company details
	 * @throws SQLException
	 */
	public Company getcompanydetailswithuid(String uid) throws SQLException {
		Company c = null;
		String query = "SELECT * FROM `companydetails` WHERE `uid` = " + uid;
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery(query);
		while(rs.next()) {
			c = new Company();
			c.setCompanyname(rs.getString("companyname"));
			c.setCompanyphno(rs.getString("companyphno"));
			c.setCompanymailid(rs.getString("companymailid"));
			c.setCompanyaddress(rs.getString("companyaddress"));
			return c;
		}
		return c;
	}

	/**
	 * <p>used to add new company details into the database for a particular uid
	 * <b>duplicates in company name is not allowed if found throws MySQLIntegrityConstraintViolationException</b></p>
	 * @param uid for which the company details hava to be added
	 * @param company object for which details to be added
	 * @throws SQLException
	 */
	public void putcompanydetailswithuid(String uid, Company c) throws SQLException {
		String query = "INSERT INTO `companydetails` (`companyid`, `companyname`, `companyphno`, `companymailid`, `companyaddress`, `uid`) VALUES (NULL, '" 
														+ c.getCompanyname() + "', '" 
														+ c.getCompanyphno() + "', '" 
														+ c.getCompanymailid() + "', '"
														+ c.getCompanyaddress() +"', '" 
														+ uid + "')";
		Statement sta = conn.createStatement();
		sta.execute(query);
	}

	/**
	 * <p>used to edit company details in the database for a particular uid
	 * <b>duplicates in company name is not allowed if found throws MySQLIntegrityConstraintViolationException</b></p>
	 * @param uid for which the company details hava to be edited
	 * @param company object with the edited details
	 * @throws SQLException
	 */
	public void editcompanywithuserid(String uid, Company c) throws SQLException {
		String query = "UPDATE `companydetails` SET `companyname` = '" + c.getCompanyname() + 
													"', `companyphno` = '" + c.getCompanyphno() + 
													"', `companymailid` = '" + c.getCompanymailid() + 
													"', `companyaddress` = '" + c.getCompanyaddress() +
													"' WHERE `companydetails`.`uid` = " + uid;
		
		Statement sta = conn.createStatement();
		sta.execute(query);
	}
}
