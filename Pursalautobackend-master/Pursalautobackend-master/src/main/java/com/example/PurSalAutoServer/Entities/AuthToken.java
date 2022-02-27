package com.example.PurSalAutoServer.Entities;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthToken {
	int uid;
	String encoded64key;
	Date issueon;
	Date expieyon;
	public AuthToken() {
		
	}
	public int getId() {
		return uid;
	}
	public void setId(int id) {
		this.uid = id;
	}
	public String getEncoded64key() {
		return encoded64key;
	}
	public void setEncoded64key(String encoded64key) {
		this.encoded64key = encoded64key;
	}
	public Date getIssueon() {
		return issueon;
	}
	public void setIssueon(Date issueon) {
		this.issueon = issueon;
	}
	public Date getExpieyon() {
		return expieyon;
	}
	public void setExpieyon(Date expieyon) {
		this.expieyon = expieyon;
	}
	
	
}
