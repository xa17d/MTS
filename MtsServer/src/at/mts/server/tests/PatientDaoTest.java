package at.mts.server.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.mts.entity.Patient;
import at.mts.entity.TriageCategory;
import at.mts.server.persistence.HsqldbConnection;
import at.mts.server.persistence.PatientDao;
import at.mts.server.persistence.PatientDaoJdbc;
import at.mts.server.persistence.PersistenceException;

public class PatientDaoTest {

	private PatientDao patientDao;
	
	@Before
	public void setUp() throws Exception {
		patientDao = new PatientDaoJdbc(HsqldbConnection.getConnection());
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void clear() throws PersistenceException {
		//patientDao.clear();
		
		List<Patient> list = patientDao.findAll();
		assertTrue("Size is not null", list.size() == 0);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void update() throws PersistenceException {
		
		/*
		Patient patient = new Patient(UUID.fromString("bbbbbbbb-7941-4c45-bd78-ec1fc16df445"));
		
		patient.setNameFamily("Fam");
		patient.setNameGiven("Giv");
		patient.setBirthTime(new Date(50,2,3));
		patient.setCategory(TriageCategory.deceased);
		
		patientDao.update(patient);*/
	}
	
	@Test
	public void get() throws PersistenceException {
		Patient p = patientDao.findByIdV(UUID.fromString("ec73f7da-7941-4c45-bd78-ec1fc16df445"), 6);
	}

	
}
