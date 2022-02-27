package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.example.PurSalAutoServer.Entities.PurchaseStockConfigOld;

public class PurchaseStockConfigBeanOld {
	Connection conn;
	
	public PurchaseStockConfigBeanOld() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}
	
	public void addconfigusinguid(String uid,PurchaseStockConfigOld config) throws SQLException{
		Statement sta = conn.createStatement();
		String query = "CREATE TABLE `pursalauto`.`" + uid + "_purchasestock` (";
		
		for(String i : config.getStockfeilds().keySet()) {
			query = query + "`" + i + "`";
			if(config.getStockfeilds().get(i).contains("string"))
			{
				query = query + " VARCHAR(300) NOT NULL ,";
			}
			else if(config.getStockfeilds().get(i).contains("integer")) {
				query = query + " INT(10) NOT NULL ,";
			}
			else if(config.getStockfeilds().get(i).contains("double")) {
				String val = config.getStockfeilds().get(i);
				System.out.println(val.substring(7));
				query = query + " DOUBLE(10," + val.substring(7) + ") NOT NULL ,";
			}
		}
		//String query = "CREATE TABLE `pursalauto`.`uid_purchasestockschema` ( `stockid` INT(10) NOT NULL , `stockname` VARCHAR(300) NOT NULL , `price` DOUBLE(10,2) NOT NULL , PRIMARY KEY (`stockid`), UNIQUE (`stockname`)) ENGINE = InnoDB";
		query = query + "`currentqty` INT(5) `minqty` INT(5) `disname` VARCHAR(300) PRIMARY KEY (`productid`), UNIQUE (`productname`)) ENGINE = InnoDB";
		System.out.println(query);
		sta.execute(query);
		System.out.println("new configration created for user " + uid + " using query " + query);
		query = "CREATE TABLE `pursalauto`.`" + uid + "_purchasestockschema` ( `feildname` VARCHAR(200) NOT NULL , `type` VARCHAR(200) NOT NULL , PRIMARY KEY (`feildname`)) ENGINE = InnoDB";
		sta.execute(query);
		for(String i:config.getStockfeilds().keySet()) {
			query = "INSERT INTO `" + uid + "_purchasestockschema` (`feildname`, `type`) VALUES ('" + i + "', '" + config.getStockfeilds().get(i)+ "');";
			sta.execute(query);
		}
		sta.close();
	}
	
	public PurchaseStockConfigOld getpurchaseconfigusinguid(String uid) throws SQLException{
		PurchaseStockConfigOld config = new PurchaseStockConfigOld();
		String query = "select * from " + uid + "_purchasestockschema";
		Statement sta = conn.createStatement();
		ResultSet rs = sta.executeQuery(query);
		if(!rs.next()) {
			config.setStockfeildsfromhashmap(null);
		}
		else {
			HashMap<String,String> map = new HashMap<String, String>();
			rs.beforeFirst();
			while(rs.next()) {
				map.put(rs.getString("feildname"), rs.getString("type"));
			}
			config.setStockfeildsfromhashmap(map);
		}
		return config;
	}
}
