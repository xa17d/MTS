package at.mts.server.tests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.cda.CdaDocument;

public class PatientTest {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testXml() {
		Patient p = new Patient();
		p.setId(UUID.randomUUID());
		CdaDocument doc = new CdaDocument(p);
		String xml = doc.asXml();
		
		assertFalse("".equals(xml));
	}
	
}
