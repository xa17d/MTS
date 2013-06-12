package at.mts.server.service;

import java.util.List;

import at.mts.entity.Patient;
import at.mts.server.persistence.PatientDao;
import at.mts.server.persistence.PersistenceException;

public class PatientServiceImpl implements PatientService {
	public PatientServiceImpl(PatientDao patientDao) {
		this.patientDao = patientDao;
	}
	
	private PatientDao patientDao;

	@Override
	public List<Patient> findAll() throws ServiceException {
		try {
			return patientDao.findAll();
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}

	@Override
	public void clear() throws ServiceException {
		try {
			patientDao.clear();
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}
	
	public void save(Patient patient) throws ServiceException {
		try {
			Patient latest = patientDao.findById(patient.getId());
		} catch (PersistenceException e) {
			throw new ServiceStorageException(e);
		}
	}
}
