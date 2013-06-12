package at.mts.server.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import at.mts.entity.Patient;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
import at.mts.entity.cda.CdaDocument;

public interface PatientService {
	public List<Patient> findAll() throws ServiceException;
	public void clear() throws ServiceException;
	
	public Patient findById(UUID id) throws ServiceException;
	public Patient findByIdV(UUID id, int version) throws ServiceException;
	public void update(CdaDocument cda, Date timestamp) throws ServiceException;
	public List<Patient> findBy(TriageCategory category, Treatment treatment) throws ServiceException;
}
