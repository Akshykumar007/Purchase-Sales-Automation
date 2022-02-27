package com.example.PurSalAutoServer.Entities;

public class StockStatus {
	int instock;
	int noofitems;
	int outofstock;
	int noofnulldistributors;
	public int getInstock() {
		return instock;
	}
	public void setInstock(int instock) {
		this.instock = instock;
	}
	public int getNoofitems() {
		return noofitems;
	}
	public void setNoofitems(int noofitems) {
		this.noofitems = noofitems;
	}
	public int getOutofstock() {
		return outofstock;
	}
	public void setOutofstock(int outofstock) {
		this.outofstock = outofstock;
	}
	public int getNoofnulldistributors() {
		return noofnulldistributors;
	}
	public void setNoofnulldistributors(int noofnulldistributors) {
		this.noofnulldistributors = noofnulldistributors;
	}
	
}
