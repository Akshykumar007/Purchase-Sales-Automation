package com.example.PurSalAutoServer.Entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class sampleresource {
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
