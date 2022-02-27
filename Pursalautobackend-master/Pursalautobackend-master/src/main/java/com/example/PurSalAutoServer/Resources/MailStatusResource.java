package com.example.PurSalAutoServer.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.MailStatusBean;
import com.example.PurSalAutoServer.Entities.MailStatus;

@AuthNeeded
@Path("mailstatus")
public class MailStatusResource {
	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getmailstatus(@HeaderParam("uid") String uid) {
		try {
			
			MailStatusBean bean = new MailStatusBean();
			MailStatus status = bean.getmailstatus(uid);
			bean.closeconnection();
			return Response.ok().entity(status).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot get sync details").build();
		}
	}
}
