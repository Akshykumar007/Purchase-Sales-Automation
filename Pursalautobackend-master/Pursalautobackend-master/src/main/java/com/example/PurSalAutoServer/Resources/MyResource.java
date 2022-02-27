package com.example.PurSalAutoServer.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.PurSalAutoServer.Entities.sampleresource;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @AuthNeeded
    @Produces(MediaType.APPLICATION_XML)
    public sampleresource getIt(@HeaderParam("uid") String uid) {
    	System.out.println("The user id is : " + uid);
    	sampleresource s = new sampleresource();
    	s.setName("Gokul");
        return s;
    }
}
