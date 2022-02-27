package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.PurSalAutoServer.Entities.User;
import com.mysql.jdbc.Driver;

public class UserDetailsBean {
	
	Connection conn;
	public UserDetailsBean() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void addnewuser(User u) throws SQLException {
		//add new User to the database
		Statement sta = conn.createStatement();
		String query = "INSERT INTO `userdetails` (`uid`, `uname`, `uemail`, `upasswrd`) VALUES (NULL, '" + u.getName() + "', '" + u.getEmail() + "', '" + u.getPassword() + "')";
		sta.execute(query);
		System.out.println("New User Created");
		sta.close();
	}
	
	//return the user with id for the given user details
	public User getuserwithdetails(User u) throws SQLException{
		User toreturn = new User();
		Statement sta = conn.createStatement();
		String query = "SELECT * FROM `userdetails` WHERE `uname` LIKE '" + u.getName() + "' AND `uemail` LIKE '" + u.getEmail() + "' AND `upasswrd` LIKE '" + u.getPassword() + "'"; 
		ResultSet res = sta.executeQuery(query);
		while (res.next()) {
			toreturn.setId(res.getInt("uid"));
			toreturn.setName(res.getString("uname"));
			toreturn.setEmail(res.getString("uemail"));
		}
		return toreturn; 
	}
	
	public User getuserwithuid(String uid) throws SQLException{
		User toreturn = new User();
		Statement sta = conn.createStatement();
		String query = "SELECT * FROM `userdetails` WHERE `uid` = '" + uid + "'" ; 
		ResultSet res = sta.executeQuery(query);
		while (res.next()) {
			toreturn.setId(res.getInt("uid"));
			toreturn.setName(res.getString("uname"));
			toreturn.setEmail(res.getString("uemail"));
		}
		return toreturn; 
	}
	
	public boolean checkusernameexists(String uname) throws SQLException{
		//check if the user name exists
		Statement sta = conn.createStatement();
		String query = "SELECT * FROM `userdetails` WHERE `uname` = '" + uname + "'";
		ResultSet res = sta.executeQuery(query);
		while (res.next()) {
			return true;
		}
		return false;
	}
	
	public boolean checkusermailexists(String mail) throws SQLException{
		//check if the email exists
		Statement sta = conn.createStatement();
		String query = "SELECT * FROM `userdetails` WHERE `uemail` = '" + mail + "'";
		ResultSet res = sta.executeQuery(query);
		while (res.next()) {
			return true;
		}
		return false;
	}
	
	public boolean verifyuser(User u) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "SELECT * FROM `userdetails` WHERE `uname` LIKE '" + u.getName() + "' AND `uemail` LIKE '" + u.getEmail() + "' AND `upasswrd` LIKE '" + u.getPassword() + "'"; 
		ResultSet res = sta.executeQuery(query);
		while (res.next()) {
			return true;
		}
		return false;
	}
	
	public void closeconnection() {
		try {
			conn.close();
			System.out.println("SqlConnection closed for new user signup");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
