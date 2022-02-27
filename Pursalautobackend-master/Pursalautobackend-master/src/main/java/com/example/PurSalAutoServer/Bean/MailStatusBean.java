package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.example.PurSalAutoServer.Entities.MailStatus;

public class MailStatusBean {
	Connection conn;
	
	public MailStatusBean() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void closeconnection() throws SQLException {
		conn.close();
	}

	public MailStatus getmailstatus(String uid) throws SQLException {
		MailStatus toreturn = new MailStatus();
		
		Statement sta = conn.createStatement();
		String query = "select * from mailstatus where uid=" + uid;
		ResultSet rs = sta.executeQuery(query);
		if(rs.next()) {
			toreturn.setOutofstock(rs.getInt("outofstock"));
			toreturn.setNoofmaildelivered(rs.getInt("noofmaildelivered"));
			toreturn.setNoofnulldistributors(rs.getInt("noofnulldistributors"));
			Date date = rs.getTimestamp("mailts");
			toreturn.setMailts(date);
			return toreturn;
		}
		else {
			toreturn.setNoofmaildelivered(0);
			toreturn.setNoofnulldistributors(0);
			toreturn.setOutofstock(0);
			return toreturn;
		}
	}
}
