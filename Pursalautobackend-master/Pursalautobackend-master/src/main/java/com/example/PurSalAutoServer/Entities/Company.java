package com.example.PurSalAutoServer.Entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>This is the entity class for company details
 * The equivalent json object for the class if
 * <b>
 * {
 *  "companyaddress": "sample",
 *  "companymailid": "sample",
 *  "companyname": "sample",
 *  "companyphno": "56789",
 *  "id": 0
 * }</b>
 * The operation on company details can be done using the comapany resource.</p> 
 * @author gokul
 *
 */
@XmlRootElement
public class Company {
	
	int id;
	String companyname;
	String companymailid;
	String companyphno;
	String companyaddress;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanymailid() {
		return companymailid;
	}
	public void setCompanymailid(String companymailid) {
		this.companymailid = companymailid;
	}
	public String getCompanyphno() {
		return companyphno;
	}
	public void setCompanyphno(String companyphno) {
		this.companyphno = companyphno;
	}
	public String getCompanyaddress() {
		return companyaddress;
	}
	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	
}
