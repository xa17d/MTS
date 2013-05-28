package at.mts.server.service;

import java.util.List;

import at.mts.entity.Patient;

public interface PatientService {
	public List<Patient> findAll() throws ServiceException;
	public void clear() throws ServiceException;
}
