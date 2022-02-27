package com.example.PurSalAutoServer.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.SyncStatusBean;
import com.example.PurSalAutoServer.Entities.SyncStatus;

@AuthNeeded
@Path("syncstatus")
public class SyncStatusResource {
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getsyncstatus(@HeaderParam("uid") String uid) {
		try {
			SyncStatusBean bean = new SyncStatusBean();
			SyncStatus status = bean.getsyncstatus(uid);
			bean.closeconnection();
			return Response.ok().entity(status).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot get sync details").build();
		}
	}
}
