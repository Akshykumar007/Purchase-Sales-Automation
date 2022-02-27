package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.PurSalAutoServer.Entities.Distributor;
import com.mysql.jdbc.DatabaseMetaData;

public class DistributorBean {
	Connection conn;
	
	public DistributorBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}
	
	public boolean checktabelexistswithuid(String uid) throws SQLException{
		java.sql.DatabaseMetaData meta = conn.getMetaData();
		ResultSet rs = meta.getTables(null, null, uid + "_distributors",  null);
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
	public void createdistable(String uid) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "CREATE TABLE `pursalauto`.`" + uid + "_distributors` ( `name` VARCHAR(300) NOT NULL , `address` VARCHAR(500) NOT NULL , `phno` VARCHAR(10) NOT NULL , `email` VARCHAR(200) NOT NULL , PRIMARY KEY (`name`)) ENGINE = InnoDB;";
		sta.execute(query);
	}
	
	
	public void adddistributor(String uid,Distributor dis) throws SQLException{
		Statement sta = conn.createStatement();
		String query = "INSERT INTO `"+ uid +"_distributors` (`name`, `address`, `phno`, `email`) VALUES ('"+ dis.getName() +"', '" + dis.getAddress() + "', '" + dis.getPhno() + "', '" + dis.getEmail() + "')";
		sta.execute(query);
	}
	
	public void removedistributor(String uid,String disname) throws SQLException{
		Statement sta = conn.createStatement();
		String query = "DELETE FROM `" + uid + "_distributors` WHERE `"+ uid +"_distributors`.`name` = '" + disname + "'";
		sta.execute(query);
	}
	
	public ArrayList<Distributor> getalldistributor(String uid) throws SQLException{
		Statement sta = conn.createStatement();
		String query = "select * from " + uid + "_distributors";
		ResultSet rs = sta.executeQuery(query);
		ArrayList<Distributor> toreturn = new ArrayList<Distributor>();
		while(rs.next()) {
			Distributor temp = new Distributor();
			temp.setName(rs.getString("name"));
			temp.setPhno(rs.getString("phno"));
			temp.setAddress(rs.getString("address"));
			temp.setEmail(rs.getString("email"));
			
			toreturn.add(temp);
		}
		return toreturn;
	}
	
	public void editdistributor(Distributor newdis,String name) {
		
	}
	
	public Distributor getdistributor(String uid,String disname) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "select * from " + uid + "_distributors where name='" + disname + "'";
		ResultSet rs = sta.executeQuery(query);
		Distributor temp = new Distributor();
		while(rs.next()) {
			temp.setName(rs.getString("name"));
			temp.setPhno(rs.getString("phno"));
			temp.setAddress(rs.getString("address"));
			temp.setEmail(rs.getString("email"));
		}
		return temp;
	}
}
