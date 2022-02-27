package com.example.PurSalAutoServer.Resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.PurchaseStockBean;
import com.example.PurSalAutoServer.Entities.PurchaseStock;
import com.example.PurSalAutoServer.Entities.StockOpConfig;

@AuthNeeded
@Path("stock")
public class StockResource {
//	@Path("initiate")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response initiatetable(@HeaderParam("uid") String uid) {
//		try {
//			PurchaseStockBean bean = new PurchaseStockBean();
//			if(bean.checktabelexistswithuid(uid)) {
//				bean.createtable(uid);
//				bean.closeconnection();
//				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Already stocks initiated\"}";
//				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
//			}
//			else {
//				bean.createtable(uid);
//				bean.closeconnection();
//				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Stock initiation Complete\"}";
//				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
//			}
//		}
//		catch (Exception e) {
//			return Response.serverError().entity("Internal Server error Cannot initiate").build();
//		}
//	}
//	
	@Path("getall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<PurchaseStock> getallstocks(@HeaderParam("uid") String uid){
		try {
			PurchaseStockBean bean = new PurchaseStockBean();
			ArrayList<PurchaseStock> toreturn = new ArrayList<PurchaseStock>();
			if(bean.checktabelexistswithuid(uid)) {
				toreturn = bean.getallstocks(uid);
				return toreturn;
			}
			else {
				return toreturn;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Path("stcon")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setdistributor(@HeaderParam("uid") String uid, StockOpConfig config) {
		try {
			PurchaseStockBean bean = new PurchaseStockBean();
			bean.setconfig(uid, config);
			String jsonresponse="{\"ResultCode\":1,\"Result\":\"Stock sucessfully configred\"}";
			return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
		}
		catch( Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Unable to set distributor Server error!..").build();
		}
	}
	
}
