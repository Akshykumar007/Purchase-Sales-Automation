package com.example.PurSalAutoServer.Resources;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.constraints.Positive;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.CompanyBean;
import com.example.PurSalAutoServer.Entities.Company;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.sun.javafx.fxml.BeanAdapter;

/**
 * <p>This is used for adding,editing and retriving company details for an particular user<br>
 * The base path is <b>api/company/....</b><br>
 * The sample json object for the company details operation is<br>
 * <b>
 * {<br>
 *  "companyaddress": "sample",<br>
 *  "companymailid": "sample",<br>
 *  "companyname": "sample",<br>
 *  "companyphno": "56789",<br>
 *  "id": 0<br>
 * }</b></p>
 * @author gokul
 *
 */
@AuthNeeded
@Path("company")
public class CompanyResource {
	
	/**
	 * <p>used to get the company details for an user
	 * for ascessing the operation use <b>api/company/getcompany</b> path
	 * for adding company details use the path <b>company/putcompany</b></p>
	 * @return the result json object with the company details
	 */
	@GET
	@Path("get")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getcompanyfromauth(@HeaderParam("uid") String uid) {
		Company c = null;
		try {
			CompanyBean bean = new CompanyBean();
			c = bean.getcompanydetailswithuid(uid);
			if(c==null) {
				return Response.ok("No company Details Found",MediaType.APPLICATION_JSON).build();
			}
			bean.closecommection();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot retrive company details :(").build();
		}
		return Response.ok(c, MediaType.APPLICATION_JSON).build();
	}
	
	/**
	 * <p>used to add the company details for an user
	 * for ascessing the operation use <b>api/company/putcompany</b> path
	 * can be used to add only for first time
	 * for editing an existing details use the path <b>company/editcompany</b></p>
	 * @param compnany object for which add 
	 * @return the result json object with result code and result description
	 */
	@POST
	@Path("put")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response putcompanyfromauth(@HeaderParam("uid") String uid,Company c) {
		try {
		
			CompanyBean bean = new CompanyBean();
			try {
				bean.putcompanydetailswithuid(uid,c);
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Company Details added\"}";
				bean.closecommection();
				System.out.println("Edited company details for uid " + uid);
				return Response.ok(jsonresponse).build();
			}
			catch(MySQLIntegrityConstraintViolationException e){
				System.out.println("Company name already used error when adding company details for uid  " + uid);
				e.printStackTrace();
				String jsonresponse="{\"ResultCode\":0,\"Result\":\"Company name already Exists try using other name\"}";
				bean.closecommection();
				return Response.ok(jsonresponse).build();
			}
			catch (SQLException e) {
				System.out.println("error when adding company details for uid  " + uid);
				e.printStackTrace();
				bean.closecommection();
				return Response.serverError().entity("Server error when adding company details").build();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Server error when adding company detials").build();
		}
	}
	
	/**
	 * <p>used to edit the existing company details</p>
	 * <p>The operation asscessed using the <b>api/company/editcompany</b> path
	 * @param Company object you want to edit name cannot be edited
	 * @return the result json object with result code and result description 
	 */
	@PUT
	@Path("edit")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response editcompanyfromauth(@HeaderParam("uid") String uid,Company c) {
		try {
			CompanyBean bean = new CompanyBean();
			try {
				bean.editcompanywithuserid(uid,c);
				bean.closecommection();
				String jsonresponse="{\"ResultCode\":1,\"Result\":\"Company Details edited\"}";
				return Response.ok(jsonresponse).build();
			}
			catch (MySQLIntegrityConstraintViolationException e) {
				System.out.println("error when adding company details for uid  " + uid);
				e.printStackTrace();
				String jsonresponse="{\"ResultCode\":0,\"Result\":\"Company name already exists\"}";
				bean.closecommection();
				return Response.ok(jsonresponse).build();
			}
			catch (SQLException e) {
				System.out.println("error when editing company details for uid  " + uid);
				e.printStackTrace();
				bean.closecommection();
				return Response.serverError().entity("Server Error cannot edit company details try Later!..").build();
			}
			
		}
		catch (Exception e) {
			System.out.println("error when editing company details for uid  " + uid);
			e.printStackTrace();
			return Response.serverError().entity("Server error cannot edit company details try Later!..").build();
		}
	}
	
	/*
	 {
    "companyaddress": "sample",
    "companymailid": "sample",
    "companyname": "sample",
    "companyphno": "56789",
    "id": 0
	}
	*/
}
