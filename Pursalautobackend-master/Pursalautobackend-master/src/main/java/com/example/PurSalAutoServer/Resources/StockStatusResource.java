package com.example.PurSalAutoServer.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.PurchaseStockBean;
import com.example.PurSalAutoServer.Bean.StockStatusBean;
import com.example.PurSalAutoServer.Bean.SyncStatusBean;
import com.example.PurSalAutoServer.Entities.StockStatus;
import com.example.PurSalAutoServer.Entities.SyncStatus;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

@AuthNeeded
@Path("stockstatus")
public class StockStatusResource {
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getstockstatus(@HeaderParam("uid") String uid) {
		try {
			StockStatusBean bean = new StockStatusBean();
			StockStatus status = bean.getstockstatus(uid);
			bean.closeconnection();
			return Response.ok().entity(status).build();
		}
		catch (MySQLSyntaxErrorException e) {
			
			e.printStackTrace();
			StockStatus status = new StockStatus();
			status.setInstock(0);
			status.setNoofitems(0);
			status.setNoofnulldistributors(0);
			status.setOutofstock(0);
			return Response.ok().entity(status).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot get sync details").build();
		}
	}
}
