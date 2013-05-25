package at.mts.server.tests;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.server.Server;
import at.mts.server.persistence.HsqldbConnection;
import at.mts.server.service.PatientService;

public class HsqldbConnectionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnection() throws Exception {
		Connection connection = HsqldbConnection.getConnection();
		if (connection == null) { fail("connection is null"); }
		if (connection.isClosed()) { fail("connection is not open"); }
	}

	@Test
	public void testServer() throws Exception {
		Server server = Server.getInstance();
		if (server == null) { fail("server is null"); }
		PatientService service = server.getPatientService();
		if (service == null) { fail("service is null"); }
	}
}
