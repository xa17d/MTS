package at.mts.server.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.entity.Bodyparts;
import at.mts.entity.Condition;
import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.SalvageInfo;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
import at.mts.entity.cda.CdaDocument;
import at.mts.entity.cda.CdaIdV;
import at.mts.server.persistence.HsqldbConnection;
import at.mts.server.persistence.PatientDao;
import at.mts.server.persistence.PatientDaoJdbc;
import at.mts.server.service.PatientService;
import at.mts.server.service.PatientServiceImpl;

public class PatientServiceTest {

	private PatientService patientService;
	
	@Before
	public void setUp() throws Exception {
		PatientDao patientDao = new PatientDaoJdbc(HsqldbConnection.getConnection());
		patientService = new PatientServiceImpl(patientDao);
	}

	@After
	public void tearDown() throws Exception {
	}

	private Patient createPatient1() {
		Patient p = new Patient();
		
		p.setBirthTime(new Date());
		p.setBloodPressureSystolic(120);
		p.setBloodPressureDiastolic(80);
		p.setCategory(TriageCategory.minor);
		p.setCourseOfTreatment("alles ok");
		p.setDiagnosis("leichter schock");
		p.setGender(Gender.male);
		p.setGps("kA");
		p.setHealthInsurance("VGKK");
		p.setHospital("AKH");
		p.setMentalStatus(Condition.stable);
		p.setNameFamily("Fam");
		p.setNameGiven("Giv");
		p.setPerfusion(Condition.stable);
		p.setPhaseOfLife(PhaseOfLife.adult);
		p.setPlacePosition("ganz hinten irgendwo");
		p.setPulse(50);
		p.setReadyForTransport(true);
		p.setRespiration(Condition.stable);
		p.addSalvageInfo(SalvageInfo.Absaugeinheit);
		p.addSalvageInfo(SalvageInfo.Schaufeltrage);
		p.setTimestamp(new Date());
		p.setTreatment(Treatment.transported);
		p.setUrgency(1);
		p.setVersion(1);
		p.setWalkable(true);
		
		p.getBodyparts().set(Bodyparts.FRONT_HEAD, "nix im Kopf");
		p.getBodyparts().set(Bodyparts.BACK_L_FOOT, "aua am Fuss");
		
		return p;
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	@Test
	public void mergingTest1() throws Exception {
		patientService.clear();
		
		UUID id = UUID.fromString("bbbbbbbb-7941-4c45-bd78-ec1fc16df445");
		Patient p = createPatient1();
		p.setId(id);
		
		patientService.update(new CdaDocument(p), new Date());
		
		sleep(100);
		
		Patient p2 = createPatient1();
		p2.setId(id);
		
		p2.setBloodPressureSystolic(130);
		p2.setBloodPressureDiastolic(90);
		p2.setCourseOfTreatment("alles ok; kuehlung");
		p2.getBodyparts().set(Bodyparts.FRONT_HEAD, "kleiner schnitt");
		p2.getBodyparts().set(Bodyparts.BACK_L_UPPERARM, "prellung");
		
		CdaDocument cda2 = new CdaDocument(p2);
		cda2.setParentIdV(new CdaIdV(id, 1));
		
		patientService.update(cda2, new Date());
		
		sleep(100);
		
		Patient p3 = createPatient1();
		p3.setId(id);
		
		p3.setBloodPressureSystolic(140);
		p3.setBloodPressureDiastolic(100);
		p3.setCourseOfTreatment("bla");
		
		CdaDocument cda3 = new CdaDocument(p3);
		cda3.setParentIdV(new CdaIdV(id, 1));
		
		patientService.update(cda3, new Date());
		
		/////
		Patient fromDb = patientService.findById(id);
		
		assertTrue(fromDb.getBloodPressureSystolic().equals(140));
		assertTrue(fromDb.getBodyparts().get(Bodyparts.BACK_L_UPPERARM).equals("prellung"));
		assertTrue(fromDb.getBodyparts().get(Bodyparts.FRONT_HEAD).equals("nix im Kopf; kleiner schnitt"));
		assertTrue(fromDb.getBodyparts().get(Bodyparts.FRONT_HEAD).equals("nix im Kopf; kleiner schnitt"));
		assertTrue(fromDb.getCourseOfTreatment().equals("alles ok; kuehlung; bla"));
	}


}
