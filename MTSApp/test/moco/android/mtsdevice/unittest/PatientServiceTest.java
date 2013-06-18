package moco.android.mtsdevice.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PatientListItem;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.TriageCategory;

public class PatientServiceTest {

	private PatientService service;
	
	@Before
	public void setUp() throws Exception {
		service = PatientServiceImpl.getInstance();
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
			service.saveNewPatient(p);
		} catch (ServiceException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Speichern keine CommunicationException geworfen werden!");
		}
		
		
		try {
			pResult = service.loadPatientById(p.getId());
		} catch (ServiceException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Laden keine CommunicationException geworfen werden!");
		}
		
		assertEquals(p, pResult);
	}
	
	@Test
	public void testPut3PatientsToServerandGetAll() {
		
		ArrayList<PatientListItem> listResult = null;
		
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
			service.saveNewPatient(p1);
			service.saveNewPatient(p2);
			service.saveNewPatient(p3);
		} catch (ServiceException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Speichern keine CommunicationException geworfen werden!");
		}
		
		try {
			listResult = service.loadAllPatients();
		} catch (ServiceException e) {
			System.out.print(e.getMessage());
			fail("Es darf beim Laden keine CommunicationException geworfen werden!");
		}
		
		//assertTrue(listResult.size() == 3);
		assertTrue(listResult.contains(p1) && listResult.contains(p2) && listResult.contains(p3));
	}
}
