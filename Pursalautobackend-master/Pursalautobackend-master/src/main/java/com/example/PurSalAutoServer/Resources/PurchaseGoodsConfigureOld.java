package com.example.PurSalAutoServer.Resources;

import java.sql.SQLException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.PurchaseStockConfigBeanOld;
import com.example.PurSalAutoServer.Entities.PurchaseStock;
import com.example.PurSalAutoServer.Entities.PurchaseStockConfigOld;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.sun.swing.internal.plaf.basic.resources.basic;

@AuthNeeded
@Path("configestock")
public class PurchaseGoodsConfigureOld {
	@Path("putpurstockconfig")
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML})
	public Response configurestockfeilds(@HeaderParam("uid") String uid,HashMap<String, String> map) {
		PurchaseStockConfigOld config = new PurchaseStockConfigOld();
		config.setStockfeilds(map);
		try {
			PurchaseStockConfigBeanOld bean = new PurchaseStockConfigBeanOld();
			try {
				bean.addconfigusinguid(uid,config);
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Products feils for stocks sucessgully created\"}";
				bean.closeconnection();
				return Response.ok().entity(jsonresponse).build();
			}
			catch (MySQLSyntaxErrorException e) {
				System.out.println("Error when add new config to user with user id " + uid);
				String jsonresponse="{\"ResultCode\":0,\"Result\":\"Products feils for stock already created\"}";
				e.printStackTrace();
				bean.closeconnection();
				return Response.ok().entity(jsonresponse).build();
			}
			catch (SQLException e) {
				System.out.println("Error when add new config to user with user id " + uid);
				e.printStackTrace();
				bean.closeconnection();
				return Response.serverError().entity("Cannot configure stock feilds Try again later").build();
			}
		}
		catch (Exception e) {
			System.out.println("Error when add new config to user with user id " + uid);
			e.printStackTrace();
			return Response.serverError().entity("Cannot configure stock feilds Try again later").build();
		}
	}
	
	@GET
	@Path("getpurstockconfig")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getstockconfig(@HeaderParam("uid") String uid) {
		PurchaseStockConfigOld config = new PurchaseStockConfigOld();
		try {
			PurchaseStockConfigBeanOld bean = new PurchaseStockConfigBeanOld();
			try {
				config = bean.getpurchaseconfigusinguid(uid);
				bean.closeconnection();
				return Response.ok(config).build();
			}
			catch (MySQLSyntaxErrorException e) {
				e.printStackTrace();
				return Response.ok(null).build();
			}
			catch (SQLException e) {
				e.printStackTrace();
				return Response.serverError().entity("Cannot configure internal server error").build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Cannot configure internal server error").build();
		}
	}
}

/*
 {
    "stockfeilds": {
        "des": "string",
        "price": "double_2",
        "stockid": "integer",
        "name": "string"
    }
}
*/
