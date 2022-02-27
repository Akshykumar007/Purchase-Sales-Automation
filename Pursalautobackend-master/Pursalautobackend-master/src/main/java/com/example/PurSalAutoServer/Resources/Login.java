package com.example.PurSalAutoServer.Resources;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import javax.crypto.SecretKey;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.example.PurSalAutoServer.Bean.AuthTokenBean;
import com.example.PurSalAutoServer.Bean.UserDetailsBean;
import com.example.PurSalAutoServer.Entities.AuthToken;
import com.example.PurSalAutoServer.Entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Path("login")
public class Login {
	
	@POST
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response userlogin(User u) {
		Response resultresponse;
		if(u.getName() != null && u.getPassword() != null && u.getEmail() != null) {
			
			UserDetailsBean bean = null;
			try {
				bean = new UserDetailsBean();
			}
			catch (Exception e) {
				System.out.println("Error when veladiting user");
				e.printStackTrace();
				bean.closeconnection();
				return Response.serverError().entity("Server Error Unable to validate user!").build();
			}
			
			System.out.println("verfing login....");
			try {
				boolean isuser = bean.verifyuser(u);
				if(isuser) {
					System.out.println("User Login " + u.getName());
					String jwttoken = generatejwttoken(u);
					String jsonresponse="{\"ResultCode\":1,\"Result\":\"Login Sucessfull\",\"Token\":\"" + jwttoken + "\"}";
					bean.closeconnection();
					resultresponse = Response.ok(jsonresponse,MediaType.APPLICATION_JSON).cookie(new NewCookie("u_auth",jwttoken)).build();
					return resultresponse;
				}
				else {
					bean.closeconnection();
					String jsonresponse="{\"ResultCode\":0,\"Result\":\"Invalid User details\"}";
					return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build(); 
				}
			}
			catch (Exception e) {
				System.out.println("Error when adding validating user login");
				e.printStackTrace();
				bean.closeconnection();
				return Response.serverError().entity("Server Error Unable to validate user!").build();
			}
		}
		else {
			String jsonresponse="{\"ResultCode\":0,\"Result\":\"Empty Feilds\"}";
			return Response.ok(jsonresponse,MediaType.APPLICATION_JSON).build();
		}
	}

	private String generatejwttoken(User u) throws Exception {
		
		System.out.println("Generating new token for the user " + u.getName());
		
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		//the bytes is encoded to get the string and the string is stored in the db
		String base64encodedkey = Encoders.BASE64.encode(key.getEncoded());
		
		//getting the user of logedin user
		UserDetailsBean bean = new UserDetailsBean();
		User logedinuser = bean.getuserwithdetails(u);
		
		//genetaring token
		String jwstokent = Jwts.builder()
				.claim("id", logedinuser.getId())
				.claim("uname",logedinuser.getName())
				.signWith(key)
				.compact();
		
		//saving the generated token to db
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR,2);
		AuthToken token = new AuthToken();
		token.setId(logedinuser.getId());
		token.setEncoded64key(base64encodedkey);
		Date expdate = new Date(cal.getTimeInMillis());
		token.setExpieyon(expdate);
		
		AuthTokenBean tokenbean = new AuthTokenBean();
		tokenbean.putnewtoken(token);
		tokenbean.closeconnection();
		
		//printing the jws tokens
		System.out.println("JWS token " + jwstokent);
		
		return jwstokent;
	}
}
