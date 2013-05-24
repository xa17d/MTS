package at.mts.server.persistence;

import java.util.List;
import java.util.UUID;

import at.mts.entity.Patient;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public interface PatientDao {
	public Patient findById(UUID id);
	public Patient findByIdV(UUID id, int version);
	public void update(Patient patient);
	public List<Patient> findBy(TriageCategory category, Treatment treatment);
	public List<Patient> findAll();
	public void clear();
}
