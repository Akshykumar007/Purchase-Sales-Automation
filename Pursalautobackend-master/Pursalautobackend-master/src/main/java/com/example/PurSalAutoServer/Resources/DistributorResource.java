package com.example.PurSalAutoServer.Resources;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.DistributorBean;
import com.example.PurSalAutoServer.Bean.PurchaseStockBean;
import com.example.PurSalAutoServer.Entities.Distributor;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

@AuthNeeded
@Path("distributor")
public class DistributorResource {
	@GET
	@Path("isempty")
	public Response isdisbributortablecreated(@HeaderParam("uid") String uid) {
		try {
			DistributorBean bean = new DistributorBean();
			if(bean.checktabelexistswithuid(uid)) {
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Yes Distributors Present\"}";
				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
			}
			else {
				String jsonresponse="{\"ResultCode\":0,\"Result\":\"No Distributors added so far\"}";
				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Internal server error cannot process request").build();
		}
	}
	
	@Path("add")
	@POST
	public Response adddistributor(@HeaderParam("uid") String uid,Distributor dis) {
		try {
			DistributorBean bean = new DistributorBean();
			if(!bean.checktabelexistswithuid(uid)) {
				bean.createdistable(uid);
			}
			try {
				bean.adddistributor(uid, dis);
				bean.closeconnection();
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Distributors added\"}";
				return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
			}
			catch (MySQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				String jsonresponse="{\"ResultCode\":0,\"Result\":\"Distributor name already exists try using another name\"}";
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
	
	@Path("remove")
	@DELETE
	public Response removedistributors(@HeaderParam("uid") String uid,Distributor dis) {
		try {
			DistributorBean bean = new DistributorBean();
			PurchaseStockBean stockbean = new PurchaseStockBean();
			try {
				bean.removedistributor(uid, dis.getName());
				stockbean.updatedeleteddistributer(uid, dis.getName());
				bean.closeconnection();
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Distributors " + dis.getName() + " removed\"}";
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
	
	@Path("getall")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getalldistributors(@HeaderParam("uid") String uid) {
		ArrayList<Distributor> dislist = new ArrayList<Distributor>();
		try {
			DistributorBean bean = new DistributorBean();
			if(!bean.checktabelexistswithuid(uid)) {
				return Response.ok().entity(dislist).build();
			}
			dislist = bean.getalldistributor(uid);
			bean.closeconnection();
			return Response.ok().entity(dislist).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Internal server error cannot get distributors").build();
		}
	}
	
	@Path("edit")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editdistributor() {
		return null;
	}
}

/*
  {
		"name": "sample dis"
		"address": "sample distributor address"
		"phno": 456789089
		"email": "sampledistributor@gmailcom"
	}
*/
