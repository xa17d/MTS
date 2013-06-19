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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.PatientListItem;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
import at.mts.entity.cda.CdaDocument;
import at.mts.server.Logger;
import at.mts.server.Server;
import at.mts.server.service.PatientService;
import at.mts.server.service.ServiceException;

@Path("/restApi")
public class RestApi {
	
	private static final Logger LOG = Logger.forClass(RestApi.class);

	@Context
	HttpServletRequest request;
	
	@Context
	HttpServletResponse response;
	
	private Server server = Server.getInstance();
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
	@Consumes(MediaType.TEXT_XML)
	public void putPatient(@PathParam("id") String id,
			String document) throws IOException {
		authentificate();
	    
		LOG.info("PUT patients/{id}");
		
		try {
			patientService.update(new CdaDocument(document), new Date());
		} catch (ServiceException e) {
			LOG.error("error", e);
			response.sendError(404, "Service Error: "+e.getMessage());
		}
	}
	
	final DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@POST
	@Path("patients/{id}")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.TEXT_XML)
	public void postPatient(@PathParam("id") String id,
			@HeaderParam("timestamp") String timestamp,
			String document) throws IOException {
		
		authentificate();
		
		LOG.info("POST patients/{id}");
		
		try {			
			Date timestampDate;
			if (timestamp == null) { timestampDate = new Date(); }
			else { timestampDate = parseTimestamp(timestamp); }
			
			patientService.update(new CdaDocument(document), timestampDate);
		} catch (ServiceException e) {
			LOG.error("error", e);
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
		
		LOG.info("GET patients/{id}");
		
		Patient p = null;
		
		try {
			if (version == null) {
				p = patientService.findById(parseId(id));
			} else {
				p = patientService.findByIdV(parseId(id), version);
			}

		} catch (ServiceException e) {
			LOG.error("error", e);
			response.sendError(404, "Service Error: "+e.getMessage());
		}
		
		if (p == null) {
			LOG.warn("Patient not found");
			response.sendError(404, "Patient nicht gefunden");
			return "";
		}
		else {
			LOG.info("Generate CDA");
			String xml = new CdaDocument(p).asXml();
			LOG.info("CDA ready");
			return xml;
		}
	}
	
	@GET
	@Path("patients")
	@Produces(MediaType.TEXT_XML)
	public String getPatient(@QueryParam("triagekategorie") String triagekategorie,
			@QueryParam("behandlung") String behandlung,
			@Context HttpServletRequest servletRequest) throws IOException {
		
		authentificate();
		
		LOG.info("GET patients");
		
		try {
			TriageCategory category = TriageCategory.getValueOf(triagekategorie);
			Treatment treatment = Treatment.getValueOf(behandlung);
		
			List<Patient> list;

			list = patientService.findBy(category, treatment);
			
			PatientList patientList = new PatientList();
			for (Patient patient : list) {
				patientList.add(new PatientListItem(patient, server.getUrlPatient(patient.getId())));
			}
			
			return patientList.asXml();
			
		} catch (ServiceException e) {
			LOG.error("error", e);
			response.sendError(404, e.getMessage());
			return "";
		}
	}
	
	@GET
	@Path("status")
	@Produces(MediaType.TEXT_HTML)
	public String status(@Context HttpServletRequest servletRequest) {
		
		LOG.info("GET status");
		
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