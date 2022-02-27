package com.example.PurSalAutoServer.Resources;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.UserDetailsBean;
import com.example.PurSalAutoServer.Entities.User;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

@Path("signup")
public class SignUp {
	
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response signup(User u) {
		
		UserDetailsBean bean = null;
		try {
			bean = new UserDetailsBean();
		}
		catch (Exception e) {
			System.out.println("Error when adding new user");
			e.printStackTrace();
			bean.closeconnection();
			return Response.serverError().entity("Server Error Unable to register user!").build();
		}
		
		try {
			bean.addnewuser(u);
			bean.closeconnection();
			System.out.println("New User signuped");
			String jsonresponse="{\"ResultCode\":1,\"Result\":\"Sigh up Sucess\"}";
			return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
		}
		
		catch(MySQLIntegrityConstraintViolationException e) {
			//caught when duplicate mail or name entered
			System.out.println("Error when adding new user name or mail already exists");
			e.printStackTrace();
			
			try {
				if(bean.checkusernameexists(u.getName()))
				{
					bean.closeconnection();
					System.out.println("User Name already Exists");
					String jsonresponse="{\"ResultCode\":0,\"Result\":\"User Name Already Exists Try using another Name\"}";
					return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
				}
				if(bean.checkusermailexists(u.getEmail())) {
					bean.closeconnection();
					String jsonresponse="{\"ResultCode\":0,\"Result\":\"User Mail Id Already Exists Try using another Mail Id\"}";
					return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
				}
				bean.closeconnection();
				return Response.serverError().entity("Server Error Unable to register user!").build();
			}
			
			catch (SQLException exp) {
				System.out.println("Exception when checking for user unique name");
				exp.printStackTrace();
				return Response.serverError().entity("Server Error Unable to register user!").build();
			}
			
		}
		
		catch (SQLException e) {
			System.out.println("Error when adding new user");
			e.printStackTrace();
			bean.closeconnection();
			return Response.serverError().entity("Server Error Unable to register user!").build();
		}
		
	}
	
//	<user>
//    <email>elai,</email>
//    <name>gokul</name>
//    <password>hii</password>
//</user>
//	{
//	    "email": "elai,",
//	    "name": "gokul",
//	    "password": "hii"
//	}
}
