package at.mts.server.persistence;

import java.util.List;
import java.util.UUID;

import at.mts.entity.Patient;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public interface PatientDao {
	public Patient findById(UUID id) throws PersistenceException;
	public Patient findByIdV(UUID id, int version) throws PersistenceException;
	public void update(Patient patient) throws PersistenceException;
	public List<Patient> findBy(TriageCategory category, Treatment treatment) throws PersistenceException;
	public List<Patient> findAll() throws PersistenceException;
	public void clear() throws PersistenceException;
}
