package at.mts.server.service;

import at.mts.server.persistence.PatientDao;

public class PatientServiceImpl implements PatientService {
	public PatientServiceImpl(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	
	private PatientDao patientDao;
}
