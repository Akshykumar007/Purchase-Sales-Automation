package com.example.PurSalAutoServer.Resources;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.DistributorBean;
import com.example.PurSalAutoServer.Bean.PurchaseStockBean;
import com.example.PurSalAutoServer.Bean.StockMailStatusBean;
import com.example.PurSalAutoServer.Entities.POAck;
import com.example.PurSalAutoServer.Entities.StockMailStatus;

@Path("stockmailstatus")
public class StockMailStatusResource {
	@AuthNeeded
	@GET
	@Path("getall")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<StockMailStatus> getallstockmailstatus(@HeaderParam("uid") String uid){
		try {
			StockMailStatusBean bean = new StockMailStatusBean();
			ArrayList<StockMailStatus> toreturn = new ArrayList<StockMailStatus>();
			if(bean.checkiftableexists(uid)) {
				toreturn = bean.getallstockmailstatus(uid);
				return toreturn;
			}
			else {
				return toreturn;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@POST
	@Path("putack")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response ackpo(POAck poack) {
		try {
			StockMailStatusBean bean = new StockMailStatusBean();
			try {
				bean.ackpo(poack);
				bean.closeconnection();
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Sucessfull!...\"}";
				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
			}
			catch (SQLException e) {
				e.printStackTrace();
				return Response.serverError().entity("Internal server error cannot add distributors").build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Internal server error cannot add distributors").build();
		}
	}
	
}
