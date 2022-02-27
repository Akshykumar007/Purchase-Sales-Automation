package com.example.PurSalAutoServer.Entities;

import java.util.HashMap;

/*
 * id , name default feilds with types as integer and string
 * custom feilds the feilds are allowed double(decimal points),string and integer(non decimal numbers)
 * for double the value for the keys is double_nooffractionaldigits
 * for integer it is integer
 * for string it is string
 */
public class PurchaseStockConfigOld {
	HashMap<String, String> stockfeilds;

	
	public PurchaseStockConfigOld() {
	}

	public HashMap<String, String> getStockfeilds() {
		return stockfeilds;
	}

	public void setStockfeilds(HashMap<String, String> stockfeilds) {
		stockfeilds.put("productid", "integer");
		stockfeilds.put("productname", "string");
		this.stockfeilds = stockfeilds;
	}
	
	public void setStockfeildsfromhashmap(HashMap<String, String> stockfeilds) {
		this.stockfeilds = stockfeilds;
	}
}
