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
import at.mts.entity.PatientList;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.SalvageInfo;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
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
	
	@Test
	public void testXml2() {
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
		
		
		p.setId(UUID.randomUUID());
		CdaDocument doc = new CdaDocument(p);
		String xml = doc.asXml();
		
        System.out.println(xml);
		
		assertFalse("".equals(xml));
		
		CdaDocument fromX = new CdaDocument(xml);
		Patient p2 = new Patient(fromX);
		
		String xml2 = fromX.asXml();
		
        System.out.println(xml2);
		
		if (!xml2.equals(xml)) {System.out.println("Muss gleich sein, FAIL");}else{System.out.println("awesome");}
	}
}
