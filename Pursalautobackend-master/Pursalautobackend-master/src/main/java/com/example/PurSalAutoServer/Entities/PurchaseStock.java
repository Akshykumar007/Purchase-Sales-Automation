package com.example.PurSalAutoServer.Entities;

import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PurchaseStock {
	
	int id;
	String name;
	String des;
	float price;
	int currentqty;
	int minqty;
	int orderqty;
	String disname;
	Date lassync;
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
	public String getDisname() {
		return disname;
	}
	public void setDisname(String disname) {
		this.disname = disname;
	}
	public Date getLassync() {
		return lassync;
	}
	public void setLassync(Date lassync) {
		this.lassync = lassync;
	}
	
}
