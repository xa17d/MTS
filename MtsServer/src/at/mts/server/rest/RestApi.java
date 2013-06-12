package at.mts.server.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import at.mts.entity.Patient;
import at.mts.entity.cda.CdaDocument;
import at.mts.server.Server;
import at.mts.server.service.PatientService;
import at.mts.server.service.ServiceException;

@Path("/restApi")
public class RestApi {

	@Context
	HttpServletRequest request;
	
	@Context
	HttpServletResponse response;
	
	private PatientService patientService = Server.getInstance().getPatientService();
	
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

	private UUID parseId(String id) throws IOException {
		try {
			return UUID.fromString(id);
		} catch (IllegalArgumentException e) {
			response.sendError(404, "ungueltiges ID Format");
			
			return null;
		}
	}
	
	private Date parseTimestamp(String timestamp) throws IOException {
		try {
			return timeFormat.parse(timestamp);
		} catch (ParseException e) {
			response.sendError(404, "ungueltiges Timestamp Format");
			
			return null;
		}
	}
	
	@PUT
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void putPatient(@PathParam("id") String id,
			@FormParam("document") String document) throws IOException {
		authentificate();
	    
		try {
			patientService.update(new CdaDocument(document), new Date());
		} catch (ServiceException e) {
			response.sendError(404, "Service Error: "+e.getMessage());
		}
	}
	
	final DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@POST
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void postPatient(@PathParam("id") String id,
			@FormParam("document") String document,
			@FormParam("timestamp") String timestamp) throws IOException {
		
		authentificate();
	    
		try {			
			patientService.update(new CdaDocument(document), parseTimestamp(timestamp));
		} catch (ServiceException e) {
			response.sendError(404, "Service Error: "+e.getMessage());
		}
	}
	
	@GET
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	public String getPatient(@PathParam("id") String id,
			@QueryParam("version") Integer version,
			@Context HttpServletRequest servletRequest) throws IOException {
		
		authentificate();
		
		Patient p = null;
		
		try {
			if (version == null) {
				p = patientService.findById(parseId(id));
			} else {
				p = patientService.findByIdV(parseId(id), version);
			}

		} catch (ServiceException e) {
			response.sendError(404, "Service Error: "+e.getMessage());
		}
		
		if (p == null) {
			response.sendError(404, "Patient nicht gefunden");
			return "";
		}
		else {
			return "<notimplemented />";
		}
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
			patientService.clear();
		} catch (ServiceException e) {
			return e.getMessage();
		}
		return "cleared "+new Date().toString(); 
	}
} 