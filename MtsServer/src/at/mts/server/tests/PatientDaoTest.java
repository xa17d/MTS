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
		patientDao.clear();
		
		List<Patient> list = patientDao.findAll();
		assertTrue("Size is not null", list.size() == 0);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void updateAndFind() throws PersistenceException {
		
		patientDao.clear();
		
		Patient patient = new Patient();
		UUID id = UUID.fromString("bbbbbbbb-7941-4c45-bd78-ec1fc16df445");
		patient.setId(id);
		
		patient.setNameFamily("Fam");
		patient.setNameGiven("Giv");
		patient.setBirthTime(new Date(50,2,3));
		patient.setCategory(TriageCategory.deceased);
		
		patientDao.update(patient);
		
		patient.setNameFamily("Fam2");
		patient.setNameGiven("Giv2");
		patient.setBirthTime(new Date(51,3,4));
		patient.setCategory(TriageCategory.delayed);
		
		patientDao.update(patient);
		
		Patient fromDao;
		
		fromDao = patientDao.findById(id);
		assertTrue(fromDao.getNameFamily().equals("Fam2"));
		assertTrue(fromDao.getNameGiven().equals("Giv2"));
		assertTrue(Math.abs(fromDao.getBirthTime().getTime() - new Date(51,3,4).getTime()) < 24*60*60*1000);
		assertTrue(fromDao.getCategory().equals(TriageCategory.delayed));
		
		fromDao = patientDao.findByIdV(id, 1);
		assertTrue(fromDao.getNameFamily().equals("Fam"));
		assertTrue(fromDao.getNameGiven().equals("Giv"));
		assertTrue(Math.abs(fromDao.getBirthTime().getTime() - new Date(50,2,3).getTime()) < 24*60*60*1000);
		assertTrue(fromDao.getCategory().equals(TriageCategory.deceased));
	}
}
