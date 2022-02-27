package com.example.PurSalAutoServer.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.crypto.SecretKey;
import javax.json.JsonObject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.PurSalAutoServer.Bean.AuthTokenBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Provider
@AuthNeeded
public class AuthFilter implements ContainerRequestFilter{

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		//getting the list of auth headers
		List<String> authHeader = requestContext.getHeaders().get("Authorization");
		Long id;
		if(authHeader != null) {
			
			String Authtokenformuser = authHeader.get(0).replace("Bearer ", "");
			
			StringTokenizer Tokens = new StringTokenizer(Authtokenformuser, ".");
			
			if(Tokens.countTokens() != 3) {
				//checking if tokens only has three parts
				
				System.out.println("token failed due to less parts in token");
				Response unauthresponse = Response.status(Status.UNAUTHORIZED).entity("Invalid Auth Token").build();
				
				requestContext.abortWith(unauthresponse);
				return;
			}
			
			//getting the header payload validator part of jws
			String Header = Tokens.nextToken();
			String PayLoad = Tokens.nextToken();
			String validator = Tokens.nextToken();
			
			//decoding payload to string
			byte[] DecodedPayload = Base64.getDecoder().decode(PayLoad);
			String Decodedpayloadstring = new String(DecodedPayload);
			String base64encodedkey = "";
			try {
				//parsing the json to get the id to get key from db
				JSONParser parser = new JSONParser();
				JSONObject PayloadObject = (JSONObject) parser.parse(Decodedpayloadstring);
				id = (Long) PayloadObject.get("id");
				
				//retriving the key from db
				AuthTokenBean bean = new AuthTokenBean();
				base64encodedkey = bean.getkeybyid(id.intValue());
			}
			catch (Exception e) {
				e.printStackTrace();
				
				Response exceptionresponse = Response.serverError().entity("Server Error Unble to auth user!").build();
				
				requestContext.abortWith(exceptionresponse);
				return;
			}
			
			//the db stored encoded string of the key is decoded and then given to an secret key
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64encodedkey));
					
			try {
				Jws<Claims> result1 = Jwts.parser().
										setSigningKey(key).
										parseClaimsJws(Authtokenformuser);
				System.out.println("Valid Jws");
				requestContext.getHeaders().add("uid",id.toString());
			}
			catch (JwtException e) {
				System.out.println("Invalid jws");
				Response unauthresponse = Response.status(Status.UNAUTHORIZED).entity("Invalid Token Avaliable").build();
				
				requestContext.abortWith(unauthresponse);
				return;
			}
		}
		else {
			
			Response unauthresponse = Response.status(Status.UNAUTHORIZED).entity("No Auth Token Avaliable").build();
		
			requestContext.abortWith(unauthresponse);
			return;
		}
	}
	
}
