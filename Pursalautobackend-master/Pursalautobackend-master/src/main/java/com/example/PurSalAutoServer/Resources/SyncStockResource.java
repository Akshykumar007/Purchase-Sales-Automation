package com.example.PurSalAutoServer.Resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.PurchaseStockBean;
import com.example.PurSalAutoServer.Bean.StockMailStatusBean;
import com.example.PurSalAutoServer.Bean.SyncStatusBean;
import com.example.PurSalAutoServer.Entities.PurchaseStock;
import com.example.PurSalAutoServer.Entities.SyncPurchaseStock;
import com.example.PurSalAutoServer.Entities.SyncStatus;

import Exceptions.StockNameNotFoundException;

//Sync stock of a shop
//if the tables are created sync to it otherwise create tables for mail status and stock sync
@AuthNeeded
@Path("syncstock")
public class SyncStockResource {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response syncstock(@HeaderParam("uid") String uid,ArrayList<PurchaseStock> stocks) {
		try {
			PurchaseStockBean bean = new PurchaseStockBean();
			SyncStatusBean statusbean = new SyncStatusBean();
			StockMailStatusBean mailstatusbean = new StockMailStatusBean();
			try {
				if(!bean.checktabelexistswithuid(uid)) {
					bean.createtable(uid);
					mailstatusbean.createtable(uid);
				}
				SyncStatus status = bean.syncstock(stocks, uid);
				statusbean.updatesyncstatus(status,uid);
				bean.closeconnection();
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Stock Sync Sucessfull\"}";
				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).entity(status).build();
			}
			catch (StockNameNotFoundException e) {
				bean.closeconnection();
				String jsonresponse="{\"ResultCode\":0,\"Result\":\"No name specified for item\"}";
				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
			}
			catch (SQLException e) {
				e.printStackTrace();
				return Response.serverError().entity("Unable to sync server error").build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Unable to sync server error").build();
		}
	}
}
/*
 * [
    {
        "currentqty": 20,
        "des": "hammam",
        "disname": "",
        "id": 0,
        "minqty": 10,
        "name": "Soap",
        "orderqty": 30,
        "price": 18.0
    },
    {
        "currentqty": 30,
        "des": "colgate",
        "disname": "",
        "id": 0,
        "minqty": 10,
        "name": "Brush",
        "orderqty": 20,
        "price": 20.5
    }
]
 */
