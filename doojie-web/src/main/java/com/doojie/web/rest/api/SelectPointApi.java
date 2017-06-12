package com.doojie.web.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@Path("/slectPoint")
public class SelectPointApi {

	Logger logger = Logger.getLogger(SelectPointApi.class);

	@GET
	@Path("/users/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser(@PathParam("username") String username) {
		logger.info("username:" + username);
		return username;
	}
}
