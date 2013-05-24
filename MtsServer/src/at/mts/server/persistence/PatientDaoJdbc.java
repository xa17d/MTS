package at.mts.server.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import at.mts.entity.Patient;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public class PatientDaoJdbc extends GenericDaoJdbc implements PatientDao {
	
	public PatientDaoJdbc(Connection connection) {
		super(connection);
	}

	@Override
	public Patient findById(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Patient findByIdV(UUID id, int version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Patient patient) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Patient> findBy(TriageCategory category, Treatment treatment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Patient> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	private Patient patientByResultSet(ResultSet r) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
