package moco.android.mtsdevice.unittest;

import static org.junit.Assert.*;

import java.util.UUID;

import moco.android.mtsdevice.communication.CommunicationException;
import moco.android.mtsdevice.communication.ServerCommunication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.TriageCategory;

public class ServerCommunicationTest {

	private ServerCommunication com;
	
	@Before
	public void setUp() throws Exception {
		
		com = ServerCommunication.getInstance();
		com.changeMtsServerAddress("LOCAL HOST");
	}

	@After
	public void tearDown() throws Exception {
		
		//TODO Daten loeschen
	}

	@Test
	public void testPutPatientToServerAndGetItById() {
		
		Patient pResult = null;
		
		Patient p = new Patient();
		p.setId(UUID.randomUUID());
		p.setNameFamily("Dobler");
		p.setNameGiven("Lucas");
		p.setCategory(TriageCategory.immediate);
		p.setGender(Gender.male);
		p.setPhaseOfLife(PhaseOfLife.child);
		
		try {
			com.savePatient(p);
		} catch (CommunicationException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Speichern keine CommunicationException geworfen werden!");
		}
		
		
		try {
			pResult = com.loadPatientById(p.getId().toString());
		} catch (CommunicationException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Laden keine CommunicationException geworfen werden!");
		}
		
		assertEquals(p, pResult);
	}
	
	@Test
	public void testPut3PatientsToServerandGetAll() {
		
		PatientList listResult = null;
		
		Patient p1 = new Patient();
		p1.setId(UUID.randomUUID());
		p1.setNameFamily("Dobler");
		p1.setNameGiven("Lucas");
		p1.setCategory(TriageCategory.immediate);
		p1.setGender(Gender.male);
		p1.setPhaseOfLife(PhaseOfLife.child);
		
		Patient p2 = new Patient();
		p2.setId(UUID.randomUUID());
		p2.setNameFamily("Gehrer");
		p2.setNameGiven("Daniel");
		p2.setCategory(TriageCategory.delayed);
		p2.setGender(Gender.female);
		p2.setPhaseOfLife(PhaseOfLife.adult);
		
		Patient p3 = new Patient();
		p3.setId(UUID.randomUUID());
		p3.setNameFamily("Filcic");
		p3.setNameGiven("Goran");
		p3.setCategory(TriageCategory.deceased);
		p3.setGender(Gender.male);
		p3.setPhaseOfLife(PhaseOfLife.adult);
		
		try {
			com.savePatient(p1);
			com.savePatient(p2);
			com.savePatient(p3);
		} catch (CommunicationException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Speichern keine CommunicationException geworfen werden!");
		}
		
		try {
			listResult = com.loadAllPatients();
		} catch (CommunicationException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Laden keine CommunicationException geworfen werden!");
		}
		
		//assertTrue(listResult.size() == 3);
		assertTrue(listResult.contains(p1) && listResult.contains(p2) && listResult.contains(p3));
	}
}
