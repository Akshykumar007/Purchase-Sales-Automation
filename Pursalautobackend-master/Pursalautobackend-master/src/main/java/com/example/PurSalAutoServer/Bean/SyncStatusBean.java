package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.example.PurSalAutoServer.Entities.SyncStatus;

public class SyncStatusBean {
Connection conn;
	
	public SyncStatusBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}

	public void updatesyncstatus(SyncStatus status,String uid) throws SQLException {
		Statement sta = conn.createStatement();
		String insertquery = "INSERT INTO `syncstatus` (`TS`, `noofitems`, `noofupdate`, `noofnew`, `instock`, `outofstock`, `uid`) VALUES (current_timestamp(), '" + status.getNoofitems() + "', '" + status.getNoofupdates() + "', '" + status.getNoofnew() + "', '" + status.getInstock() + "', '" + status.getOutstock() + "', '" + uid + "')";
		String updatequery = "UPDATE `syncstatus` SET `TS` = current_timestamp(), `noofitems` = '" + status.getNoofitems() + "', `noofupdate` = '" + status.getNoofupdates() + "', `noofnew` = '" + status.getNoofnew() + "', `outofstock` = '" + status.getOutstock() + "',`instock` = '" + status.getInstock() + "' WHERE `syncstatus`.`uid` = " + uid;
		ResultSet rs = sta.executeQuery("select * from syncstatus where uid=" + uid);
		if(rs.next()) {
			sta.execute(updatequery);
		}
		else {
			sta.execute(insertquery);
		}
	}

	public SyncStatus getsyncstatus(String uid) throws SQLException {
		SyncStatus toreturn = new SyncStatus();
		Statement sta = conn.createStatement();
		String query = "select * from syncstatus where uid="+uid; 
		ResultSet rs = sta.executeQuery(query);
		
		if(rs.next())
		{
			toreturn.setNoofitems(rs.getInt("noofitems"));
			toreturn.setNoofnew(rs.getInt("noofnew"));
			toreturn.setNoofupdates(rs.getInt("noofupdate"));
			toreturn.setInstock(rs.getInt("instock"));
			toreturn.setOutstock(rs.getInt("outofstock"));
			Date date = rs.getTimestamp("TS");
			toreturn.setTs(date);
			toreturn.setTimestamp(date.getDate() + "/" + date.getMonth() + "/" + date.getYear() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
			return toreturn;
		}
		else {
			toreturn.setNoofitems(0);
			toreturn.setNoofnew(0);
			toreturn.setNoofupdates(0);
			toreturn.setInstock(0);
			toreturn.setOutstock(0);
			toreturn.setTimestamp("No syncs");
			return toreturn;
		}
	}
}
