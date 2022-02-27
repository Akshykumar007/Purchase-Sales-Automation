package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.PurSalAutoServer.Entities.PurchaseStock;
import com.example.PurSalAutoServer.Entities.StockOpConfig;
import com.example.PurSalAutoServer.Entities.SyncPurchaseStock;
import com.example.PurSalAutoServer.Entities.SyncStatus;

import Exceptions.StockNameNotFoundException;

public class PurchaseStockBean {
	Connection conn;
	
	public PurchaseStockBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}
	
	public void createtable(String uid) throws Exception{
		Statement sta = conn.createStatement();
		String query = "CREATE TABLE `pursalauto`.`" + uid + "_purchasestock` ( `id` INT NOT NULL AUTO_INCREMENT , `name` VARCHAR(200) NOT NULL , `description` VARCHAR(500) NOT NULL , `price` DECIMAL(3) NOT NULL , `currentqty` INT NOT NULL , `minqty` INT NOT NULL , `orderqty` INT NOT NULL , `disname` VARCHAR(300) , `lastsync` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
		sta.execute(query);
		DistributorBean bean = new DistributorBean();
		if(!bean.checktabelexistswithuid(uid)) {
			bean.createdistable(uid);
		}
//		query = "ALTER TABLE " + uid + "_purchasestock ADD FOREIGN KEY(disname) REFERENCES " + uid + "_distributors(name)";
//		sta.execute(query);
	}
	
	public boolean checktabelexistswithuid(String uid) throws SQLException{
		java.sql.DatabaseMetaData meta = conn.getMetaData();
		ResultSet rs = meta.getTables(null, null, uid + "_purchasestock",  null);
		if(rs.next()) {
			return true;
		}
		return false;
	}

	public ArrayList<PurchaseStock> getallstocks(String uid) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "select * from " + uid + "_purchasestock";
		ResultSet rs = sta.executeQuery(query);
		ArrayList<PurchaseStock> toreturn = new ArrayList<PurchaseStock>();
		while (rs.next()) {
			PurchaseStock temp = new PurchaseStock();
			temp.setName(rs.getString("name"));
			temp.setDes(rs.getString("description"));
			temp.setPrice(rs.getFloat("price"));
			temp.setCurrentqty(rs.getInt("currentqty"));
			temp.setMinqty(rs.getInt("minqty"));
			temp.setOrderqty(rs.getInt("orderqty"));
			temp.setDisname(rs.getString("disname"));
			temp.setLassync(rs.getTimestamp("lastsync"));
			toreturn.add(temp);
		}
		return toreturn;
	}
	
	public SyncStatus syncstock(ArrayList<PurchaseStock> stocks,String uid) throws Exception{
		int noofnew=0,noofupdate=0,noofitems=0,instock=0,outofstock=0;
		Date syncdate;
		Statement sta = conn.createStatement();
		System.out.println("Executing stock sync");
		for(int i=0;i<stocks.size();i++) {
			String insertquery = "INSERT INTO `"+ uid +"_purchasestock` (";
			String insertkeysquery = "";
			String insertvalquery = "";
			String updatequery = "UPDATE `" + uid + "_purchasestock` SET ";
			String updatekeysvalquery = "";
			String finalquery = "";
			PurchaseStock temp = stocks.get(i);
			if(temp.getName() == null)
			{
				throw new StockNameNotFoundException("stock not found");
			}
			if(temp.getDes() != null) {
				insertkeysquery = insertkeysquery + "`description`,";
				insertvalquery = insertvalquery + "'" + temp.getDes() + "',";
				
				updatekeysvalquery = updatekeysvalquery + "`description` = '" + temp.getDes() + "',";
			}
			if(new Float(temp.getPrice()) != null) {
				insertkeysquery = insertkeysquery + "`price`,";
				insertvalquery = insertvalquery + temp.getPrice() + ",";
				
				updatekeysvalquery = updatekeysvalquery + "`price` = '" + temp.getPrice() + "',";
			}
			if(new Integer(temp.getCurrentqty()) != null) {
				insertkeysquery = insertkeysquery + "`currentqty`,";
				insertvalquery = insertvalquery + temp.getCurrentqty() + ",";
				
				updatekeysvalquery = updatekeysvalquery + "`currentqty` = '" + temp.getCurrentqty() + "',";
			}
			if(new Integer(temp.getMinqty()) != null) {
				insertkeysquery = insertkeysquery + "`minqty`,";
				insertvalquery = insertvalquery + temp.getMinqty() + ",";
				
				updatekeysvalquery = updatekeysvalquery + "`minqty` = '" + temp.getMinqty() + "',";
			}
			if(new Integer(temp.getOrderqty()) != null) {
				insertkeysquery = insertkeysquery + "`orderqty`,";
				insertvalquery = insertvalquery + temp.getOrderqty() + ",";
				
				updatekeysvalquery = updatekeysvalquery + "`orderqty` = '" + temp.getOrderqty() + "',";
			}
			
			insertquery=insertquery + "`name`," + insertkeysquery.substring(0, insertkeysquery.length()-1) + ") VALUES ('" + temp.getName() + "'," + insertvalquery.substring(0,insertvalquery.length()-1) + ")";
			updatequery = updatequery + updatekeysvalquery.substring(0,updatekeysvalquery.length()-1) + ", `lastsync` = CURRENT_TIME()" + " WHERE `" + uid + "_purchasestock`.`name` = '" + temp.getName() + "'";
			System.out.println(temp.getName() + " Item Synced");
			System.out.println(insertquery);
			System.out.println();
			System.out.println(updatequery);
			
			ResultSet rs = sta.executeQuery("select * from " + uid + "_purchasestock where name='" + temp.getName() + "'");
			if(rs.next()) {
				sta.execute(updatequery);
				noofupdate++;
			}
			else {
				sta.execute(insertquery);
				noofnew++;
			}
			
			if(temp.getCurrentqty()<temp.getMinqty()) {
				outofstock++;
			}
			else {
				instock++;
			}
			noofitems++;
//			finalquery = finalquery + updatequery + " IF @@ROWCOUNT=0 " + insertquery +";";
//			System.out.println(finalquery);
//			sta.execute(query);
		}
		//Setting up sync status
		SyncStatus status = new SyncStatus();
		status.setNoofitems(noofitems);
		status.setNoofnew(noofnew);
		status.setNoofupdates(noofupdate);
		status.setOutstock(outofstock);
		status.setInstock(instock);
		
		return status;
	}
	
	public void setconfig(String uid, StockOpConfig config) throws SQLException {
		Statement sta = conn.createStatement();
		String query="UPDATE `" + uid + "_purchasestock` SET `minqty` = '" + config.getMinqty() + "', `orderqty` = '" + config.getOrderqty() + "', `disname` = '" + config.getDisname() + "' WHERE `" + uid + "_purchasestock`.`name` = '" + config.getName() + "'";
		System.out.println(query);
		sta.execute(query);
	}
	
	public void updatedeleteddistributer(String uid,String disname) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "UPDATE `" + uid + "_purchasestock` SET `disname` = '' WHERE `" + uid + "_purchasestock`.`disname` = '" + disname + "'";
		System.out.println(query);
		sta.execute(query);
	}
	
	public PurchaseStock getstock(int uid,int stockid) throws SQLException {
		Statement sta = conn.createStatement();
		String query = "select * from " + uid + "_purchasestock where id='" + stockid + "'";
		ResultSet rs = sta.executeQuery(query);
		PurchaseStock temp = new PurchaseStock();
		
		while (rs.next()) {
			temp.setName(rs.getString("name"));
			temp.setDes(rs.getString("description"));
			temp.setPrice(rs.getFloat("price"));
			temp.setCurrentqty(rs.getInt("currentqty"));
			temp.setMinqty(rs.getInt("minqty"));
			temp.setOrderqty(rs.getInt("orderqty"));
			temp.setDisname(rs.getString("disname"));
			temp.setLassync(rs.getTimestamp("lastsync"));
		}
		return temp;
	}
}

//CREATE TABLE `pursalauto`.`test_purchasestock` ( `id` INT NOT NULL AUTO_INCREMENT , `name` VARCHAR(200) NOT NULL , `description` VARCHAR(500) NOT NULL , `price` DECIMAL(3) NOT NULL , `currentqty` INT NOT NULL , `minqty` INT NOT NULL , `orderqty` INT NOT NULL , `disname` VARCHAR(300) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;