package at.mts.server;

import java.sql.Connection;
import java.util.UUID;

import at.mts.server.persistence.HsqldbConnection;
import at.mts.server.persistence.PatientDao;
import at.mts.server.persistence.PatientDaoJdbc;
import at.mts.server.persistence.PersistenceException;
import at.mts.server.service.PatientService;
import at.mts.server.service.PatientServiceImpl;

/**
 * Singleton Klasse, die eine Server Instanz bereitstellt, die in der 
 * ganzen Anwendung verwendet werden kann.
 */
public class Server {
	
	private static final Logger LOG = Logger.forClass(Server.class);
	
	private Server() throws ServerException {
		
		Connection connection = null;
		try {
			connection = HsqldbConnection.getConnection();
		} catch (PersistenceException e) {
			throw new ServerException(e);
		}
		
		PatientDao patientDao = new PatientDaoJdbc(connection);
		patientService = new PatientServiceImpl(patientDao);
	}
		
	private PatientService patientService;
	public PatientService getPatientService() {
		return patientService;
	}
	
	public String getUrlBase() {
		return "http://88.116.105.228:30104/MtsServer/restApi/";
	}
	
	public String getUrlPatient(UUID patientId) {
		return getUrlBase() + "patients/"+patientId.toString();
	}
	
	
	private static Server instance = createInstance();
	
	private static Server createInstance() 
	{
		LOG.info("create singleton...");
		
		try {
			Server server = new Server();
			LOG.info("singleton created");
			return server;
		} catch (ServerException e) {
			LOG.error("create singleton failed", e);
			throw new RuntimeException(e);
		}
	}
	
	public static Server getInstance() {
		return instance;
	}
}
