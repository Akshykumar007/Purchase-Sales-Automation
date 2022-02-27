package com.example.PurSalAutoServer.Entities;

import java.sql.Date;

public class SyncPurchaseStock {
	String name;
	String des;
	float price;
	int currentqty;
	int minqty;
	int orderqty;
	Date lastsync;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getCurrentqty() {
		return currentqty;
	}
	public void setCurrentqty(int currentqty) {
		this.currentqty = currentqty;
	}
	public int getMinqty() {
		return minqty;
	}
	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}
	public int getOrderqty() {
		return orderqty;
	}
	public void setOrderqty(int orderqty) {
		this.orderqty = orderqty;
	}
}
