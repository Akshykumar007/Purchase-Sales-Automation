package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.PurSalAutoServer.Entities.StockStatus;

public class StockStatusBean {
Connection conn;
	
	public StockStatusBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}
	
	public StockStatus getstockstatus(String uid) throws SQLException {
		Statement sta = conn.createStatement();
		
		int noofitems = 0;
		int instock = 0;
		int outofstock = 0;
		int noofnulldistributors = 0;
		
		String query = "select count(*) from " + uid + "_purchasestock";
		ResultSet rs = sta.executeQuery(query);
		if(rs.next()) {
			noofitems = rs.getInt("COUNT(*)");
		}
		
		query = "SELECT COUNT(*) FROM `" + uid + "_purchasestock` WHERE currentqty >= minqty";
		rs = sta.executeQuery(query);
		if(rs.next()) {
			instock = rs.getInt("COUNT(*)");
		}
		
		query = "SELECT COUNT(*) FROM `" + uid + "_purchasestock` WHERE currentqty < minqty";
		rs = sta.executeQuery(query);
		if(rs.next()) {
			outofstock = rs.getInt("COUNT(*)");
		}
		
		query = "SELECT COUNT(*) FROM `" + uid + "_purchasestock` WHERE disname = \"\" OR disname IS NULL";
		rs = sta.executeQuery(query);
		if(rs.next()) {
			noofnulldistributors = rs.getInt("COUNT(*)");
		}
		
		StockStatus toreturn = new StockStatus();
		toreturn.setInstock(instock);
		toreturn.setNoofitems(noofitems);
		toreturn.setOutofstock(outofstock);
		toreturn.setNoofnulldistributors(noofnulldistributors);
		
		return toreturn;
	}
}
