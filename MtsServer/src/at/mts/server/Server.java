package at.mts.server;

import java.sql.Connection;

import at.mts.server.persistence.HsqldbConnection;
import at.mts.server.persistence.PatientDao;
import at.mts.server.persistence.PatientDaoJdbc;
import at.mts.server.persistence.PersistenceException;
import at.mts.server.service.PatientService;
import at.mts.server.service.PatientServiceImpl;

public class Server {
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
	
	
	private static Server instance = createInstance();
	
	private static Server createInstance() 
	{
		try {
			return new Server();
		} catch (ServerException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Server getInstance() {
		return instance;
	}
}
