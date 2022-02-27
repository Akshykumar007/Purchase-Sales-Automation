package com.example.PurSalAutoServer.Entities;

public class PurchaseOrder {
	Distributor distributor;
	Company company;
	PurchaseStock stock;
	StockMailStatus stockmailstatus;
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public PurchaseStock getStock() {
		return stock;
	}
	public void setStock(PurchaseStock stock) {
		this.stock = stock;
	}
	public StockMailStatus getStockmailstatus() {
		return stockmailstatus;
	}
	public void setStockmailstatus(StockMailStatus stockmailstatus) {
		this.stockmailstatus = stockmailstatus;
	}
	
}
