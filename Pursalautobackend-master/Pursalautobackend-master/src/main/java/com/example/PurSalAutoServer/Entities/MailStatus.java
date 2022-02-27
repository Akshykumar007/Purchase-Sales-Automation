package com.example.PurSalAutoServer.Entities;

import java.util.Date;

public class MailStatus {
	int outofstock;
	int noofmaildelivered;
	int noofnulldistributors;
	Date mailts;
	public int getOutofstock() {
		return outofstock;
	}
	public void setOutofstock(int outofstock) {
		this.outofstock = outofstock;
	}
	public int getNoofmaildelivered() {
		return noofmaildelivered;
	}
	public void setNoofmaildelivered(int noofmaildelivered) {
		this.noofmaildelivered = noofmaildelivered;
	}
	public int getNoofnulldistributors() {
		return noofnulldistributors;
	}
	public void setNoofnulldistributors(int noofnulldistributors) {
		this.noofnulldistributors = noofnulldistributors;
	}
	public Date getMailts() {
		return mailts;
	}
	public void setMailts(Date mailts) {
		this.mailts = mailts;
	}
}
