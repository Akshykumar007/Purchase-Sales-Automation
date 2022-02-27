package com.example.PurSalAutoServer.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.CompanyBean;
import com.example.PurSalAutoServer.Bean.UserDetailsBean;
import com.example.PurSalAutoServer.Entities.Company;
import com.example.PurSalAutoServer.Entities.User;

@AuthNeeded
@Path("user")
public class UserDetailsResource {

	@GET
	@Path("get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getuser(@HeaderParam("uid") String uid) {
		User c = null;
		try {
			UserDetailsBean bean = new UserDetailsBean();
			c = bean.getuserwithuid(uid);
			if(c==null) {
				return Response.ok("No user Details Found",MediaType.APPLICATION_JSON).build();
			}
			bean.closeconnection();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot retrive company details :(").build();
		}
		return Response.ok(c, MediaType.APPLICATION_JSON).build();
	}
}
