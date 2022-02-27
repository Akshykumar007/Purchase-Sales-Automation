package com.example.PurSalAutoServer.Entities;

import java.math.BigInteger;
import java.util.Date;

public class StockMailStatus {
	int id;
	String name;
	Date mailts;
	String distributor;
	int po;
	int orderqty;
	int ack;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getMailts() {
		return mailts;
	}
	public void setMailts(Date mailts) {
		this.mailts = mailts;
	}
	public String getDistributor() {
		return distributor;
	}
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	public int getPo() {
		return po;
	}
	public void setPo(int po) {
		this.po = po;
	}
	public int getOrderqty() {
		return orderqty;
	}
	public void setOrderqty(int orderqty) {
		this.orderqty = orderqty;
	}
	public int getAck() {
		return ack;
	}
	public void setAck(int ack) {
		this.ack = ack;
	}
	
}
