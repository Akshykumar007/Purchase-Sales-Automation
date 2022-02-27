package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.PurSalAutoServer.Entities.POAck;
import com.example.PurSalAutoServer.Entities.StockMailStatus;


public class StockMailStatusBean {
	Connection conn;
	
	public StockMailStatusBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}
	
	public void createtable(String uid) throws SQLException 
	{
		Statement sta = conn.createStatement();
		String query = "CREATE TABLE `pursalauto`.`" + uid + "_stockmailstatus` ( `stockid` INT NOT NULL , `stockname` VARCHAR(200) NOT NULL , `mailts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , `distributor` VARCHAR(200) NOT NULL , `pono` INT(10) NOT NULL AUTO_INCREMENT , `orderqty` INT(5) NOT NULL , UNIQUE (`pono`) , `ack` INT NULL DEFAULT NULL , PRIMARY KEY (`stockid`)) ENGINE = InnoDB;";
		sta.execute(query);
	}
	
	public boolean checkiftableexists(String uid) throws SQLException {
		java.sql.DatabaseMetaData meta = conn.getMetaData();
		ResultSet rs = meta.getTables(null, null, uid + "_stockmailstatus",  null);
		if(rs.next()) {
			return true;
		}
		return false;
	}
	
	public ArrayList<StockMailStatus> getallstockmailstatus(String uid) throws SQLException {
		
		ArrayList<StockMailStatus> toreturn = new ArrayList<StockMailStatus>();
		
		Statement sta = conn.createStatement();
		String query = "select * from " + uid + "_stockmailstatus";
		ResultSet rs = sta.executeQuery(query);
		while(rs.next()) {
			StockMailStatus temp = new StockMailStatus();
			temp.setId(rs.getInt("stockid"));
			temp.setName(rs.getString("stockname"));
			temp.setDistributor(rs.getString("distributor"));
			temp.setMailts(rs.getTimestamp("mailts"));
			temp.setPo(rs.getInt("pono"));
			temp.setOrderqty(rs.getInt("orderqty"));
			temp.setAck(rs.getInt("ack"));
			
			toreturn.add(temp);
		}
		return toreturn;
	}
	
	public StockMailStatus getstockmailstatus(String uid,String itemname) throws SQLException {
		StockMailStatus toreturn = new StockMailStatus();
		
		Statement sta = conn.createStatement();
		String query = "select * from " + uid + "_stockmailstatus where stockname = '" + itemname + "'";
		ResultSet rs = sta.executeQuery(query);
		if(rs.next()) {
			toreturn.setId(rs.getInt("stockid"));
			toreturn.setName(rs.getString("stockname"));
			toreturn.setDistributor(rs.getString("distributor"));
			toreturn.setMailts(rs.getTimestamp("mailts"));
			toreturn.setPo(rs.getInt("pono"));
			toreturn.setOrderqty(rs.getInt("orderqty"));
			toreturn.setAck(rs.getInt("ack"));
			
			return toreturn;
		}
		return null;
	}
	
	public void ackpo(POAck poack) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "UPDATE `" + poack.getUid() + "_stockmailstatus` SET `ack` = '" + poack.getAckqty() + "' WHERE `" + poack.getUid() + "_stockmailstatus`.`stockid` = " + poack.getStockid();
		sta.execute(query);
	}
}
