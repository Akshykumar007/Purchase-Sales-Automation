package com.example.PurSalAutoServer.Entities;

public class StockOpConfig {
	String disname;
	int minqty;
	int orderqty;
	String name;
	
	public String getDisname() {
		return disname;
	}
	public void setDisname(String disname) {
		this.disname = disname;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
