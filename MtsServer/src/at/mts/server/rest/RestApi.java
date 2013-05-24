package at.mts.server.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/restApi")
public class RestApi {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@PUT
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putPatient(@PathParam("id") String id,
			@FormParam("document") String document,
			@Context HttpServletResponse servletResponse) throws IOException {
		
		
	    //servletResponse.sendRedirect("../create_todo.html");
	}
	
	@POST
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postPatient(@PathParam("id") String id,
			@FormParam("document") String document,
			@FormParam("timestamp") String timestamp,
			@Context HttpServletResponse servletResponse) throws IOException {
		
		
	    //servletResponse.sendRedirect("../create_todo.html");
	}
	
	@GET
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	public String getPatient(@PathParam("id") String id,
			@QueryParam("version") int version) {
		
		return "null [id="+id+";v="+version+"]"; 
	}
	
	@GET
	@Path("patients")
	@Produces(MediaType.TEXT_XML)
	public String getPatient(@QueryParam("triagekategorie") String triagekategorie,
			@QueryParam("behandlung") String behandlung) {
		
		return "null [t="+triagekategorie+";b="+behandlung+"]"; 
	}
	
	@GET
	@Path("status")
	@Produces(MediaType.TEXT_HTML)
	public String status() {
		
		return "<h1>Status</h1>"; 
	}
	
	@GET
	@Path("clear")
	@Produces(MediaType.TEXT_PLAIN)
	public String clear() {
		
		return "clear not implemented yet"; 
	}
} 