package com.example.PurSalAutoServer.Entities;

import java.sql.Date;
import java.sql.Timestamp;

public class SyncStatus {
	int noofitems;
	int noofnew;
	int noofupdates;
	int instock;
	int outstock;
	java.util.Date ts;
	String timestamp;
	public int getNoofitems() {
		return noofitems;
	}
	public void setNoofitems(int noofitems) {
		this.noofitems = noofitems;
	}
	public int getNoofnew() {
		return noofnew;
	}
	public void setNoofnew(int noofnew) {
		this.noofnew = noofnew;
	}
	public int getNoofupdates() {
		return noofupdates;
	}
	public void setNoofupdates(int noofupdates) {
		this.noofupdates = noofupdates;
	}
	public int getInstock() {
		return instock;
	}
	public void setInstock(int instock) {
		this.instock = instock;
	}
	public int getOutstock() {
		return outstock;
	}
	public void setOutstock(int outstock) {
		this.outstock = outstock;
	}
	public java.util.Date getTs() {
		return ts;
	}
	public void setTs(java.util.Date ts) {
		this.ts = ts;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
