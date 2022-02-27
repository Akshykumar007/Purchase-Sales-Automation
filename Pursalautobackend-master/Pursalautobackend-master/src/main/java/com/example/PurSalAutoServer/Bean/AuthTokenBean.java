package com.example.PurSalAutoServer.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.PurSalAutoServer.Entities.AuthToken;
import com.example.PurSalAutoServer.Entities.User;

public class AuthTokenBean {
	
	Connection conn;
	public AuthTokenBean() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pursalauto", "root", "");
	}
	
	public void putnewtoken(AuthToken t) throws SQLException {
		deletetoken(t);
		
		String query = "INSERT INTO `authtoken` (`uid`, `encoded64key`, `issueon`, `expiryon`) VALUES (?, ?, current_timestamp(),?)";
		PreparedStatement sta = conn.prepareStatement(query);
		sta.setInt(1, t.getId());
		sta.setString(2, t.getEncoded64key());
		sta.setDate(3, t.getExpieyon());
		
		sta.executeUpdate();
	}
	
	private void deletetoken(AuthToken t) throws SQLException {
		String query = "DELETE FROM `authtoken` WHERE `authtoken`.`uid` =" + t.getId();
		Statement sta = conn.createStatement();
		
		sta.execute(query);
	}

	public String getkeybyid(int id) throws SQLException{
		String key= "";
		
		String query = "SELECT * FROM `authtoken` WHERE `uid` = " + id;
		
		Statement sta = conn.createStatement();
		
		ResultSet res = sta.executeQuery(query);
		while(res.next()) {
			key = res.getString("encoded64key");
		}
		
		return key;
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
