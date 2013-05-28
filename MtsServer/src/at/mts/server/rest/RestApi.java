package at.mts.server.rest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.HttpServletRequest;
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

import com.sun.jersey.core.util.Base64;

import at.mts.entity.Patient;
import at.mts.server.Server;
import at.mts.server.service.ServiceException;

@Path("/restApi")
public class RestApi {

	@Context
	HttpServletRequest request;
	
	@Context
	HttpServletResponse response;
	
	private void authentificate() {
		/*
		String authHeader = request.getHeader("authorization");
		if (authHeader != null) {
			String encodedValue = authHeader.split(" ")[1];
			String decodedValue = Base64.base64Decode(encodedValue);
			System.out.println(decodedValue);
		}
		else {
			try {
				response.sendError(403, "nicht authorisiert!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}

	@PUT
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putPatient(@PathParam("id") String id,
			@FormParam("document") String document) throws IOException {
		authentificate();
	    //servletResponse.sendRedirect("../create_todo.html");
	}
	
	@POST
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postPatient(@PathParam("id") String id,
			@FormParam("document") String document,
			@FormParam("timestamp") String timestamp) throws IOException {
		
		authentificate();
	    //servletResponse.sendRedirect("../create_todo.html");
	}
	
	@GET
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	public String getPatient(@PathParam("id") String id,
			@QueryParam("version") int version,
			@Context HttpServletRequest servletRequest) {
		
		authentificate();
		return "not implemented yet [id="+id+";v="+version+"]"; 
	}
	
	@GET
	@Path("patients")
	@Produces(MediaType.TEXT_XML)
	public String getPatient(@QueryParam("triagekategorie") String triagekategorie,
			@QueryParam("behandlung") String behandlung,
			@Context HttpServletRequest servletRequest) {
		
		authentificate();
		return "not implemented yet [t="+triagekategorie+";b="+behandlung+"]"; 
	}
	
	@GET
	@Path("status")
	@Produces(MediaType.TEXT_HTML)
	public String status(@Context HttpServletRequest servletRequest) {
		
		StringBuilder r = new StringBuilder();
		r.append("<h1>Status</h1>\n"); 
		
		List<Patient> patients;
		try {
			patients = Server.getInstance().getPatientService().findAll();
			
			for (Patient patient : patients) {
				r.append("<p><pre>"+patient.toString()+"</pre></p>\n");
			}
			
		} catch (ServiceException e) {
			r.append(e.getMessage());
		}
		
		return r.toString();

	}
	
	@GET
	@Path("clear")
	@Produces(MediaType.TEXT_PLAIN)
	public String clear(@Context HttpServletRequest servletRequest) {
		
		authentificate();
		
		try {
			Server.getInstance().getPatientService().clear();
		} catch (ServiceException e) {
			return e.getMessage();
		}
		return "cleared "+new Date().toString(); 
	}
} 