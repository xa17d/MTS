package at.mts.server.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.mts.entity.Condition;
import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public class PatientDaoJdbc extends GenericDaoJdbc implements PatientDao {
	
	public PatientDaoJdbc(Connection connection) {
		super(connection);
	}

	private static final String selectQueryBase = "SELECT"+
			" p.Guid," +
			" v.Version," +
			" v.NameGiven," +
			" v.NameFamily," +
			" v.BirthTime," +
			" v.Gender," +
			" v.Walkable," +
			" v.Respiration," +
			" v.Perfusion," +
			" v.MentalStatus," +
			" v.PhaseOfLife," +
			" v.PlacePosition," +
			" v.Urgency," +
			" v.BloodPressureSystolic," +
			" v.BloodPressureDiastolic," +
			" v.Pulse," +
			" v.ReadyForTransport," +
			" v.Hospital," +
			" v.HealthInsurance," +
			" v.Treatment "+
			"FROM Patient p JOIN PatientVersion v on p.id = v.Patient AND p.Version=v.Version ";

	
	private interface StatementPreparation {
		public String sql();
		public void setParameters(PreparedStatement p) throws SQLException;
	}
	
	private List<Patient> queryPatients(StatementPreparation statementPreparation) throws PersistenceException {
		ArrayList<Patient> items = new ArrayList<Patient>();
		
		PreparedStatement p = null;
		try {
			p = connection.prepareStatement(statementPreparation.sql());
			statementPreparation.setParameters(p);
			
			ResultSet r = p.executeQuery();
			
			while (r.next())
			{
				Patient item = patientByResultSet(r);
				items.add(item);
			}
			
			p.close();
		} catch (SQLException e) {
			//log.debug("Error in findAll", e);
			throw new PersistenceAccessException(e);
		}
		finally {
			if (p!=null) { 
				try {
					p.close();
				} catch (SQLException e) {
					//log.debug("Error while closing statement in findAll", e);
				}
			}
		}
		return items;
	}
	
	@Override
	public Patient findById(final UUID id) throws PersistenceException {
		
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return selectQueryBase + " WHERE p.Guid = ?";
			}
			
			@Override
			public void setParameters(PreparedStatement p) throws SQLException {
				p.setString(1, id.toString());
			}
		});
		
		if (l.isEmpty()) {
			return null;
		} else {
			return l.get(0);
		}
	}

	@Override
	public Patient findByIdV(final UUID id, final int version) throws PersistenceException {
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return selectQueryBase + " WHERE p.Guid = ? AND v.version = ?";
			}
			
			@Override
			public void setParameters(PreparedStatement p) throws SQLException {
				p.setString(1, id.toString());
				p.setInt(2, version);
			}
		});
		
		if (l.isEmpty()) {
			return null;
		} else {
			return l.get(0);
		}
	}

	@Override
	public void update(final Patient patient) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Patient> findBy(final TriageCategory category, final Treatment treatment) throws PersistenceException {
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return selectQueryBase + " WHERE v.Category = ? AND v.Treatment = ?";
			}
			
			@Override
			public void setParameters(PreparedStatement p) throws SQLException {
				p.setString(1, category.toString());
				p.setString(2, treatment.toString());
			}
		});
		
		return l;
	}

	@Override
	public List<Patient> findAll() throws PersistenceException {
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return selectQueryBase;
			}
			
			@Override
			public void setParameters(PreparedStatement p) throws SQLException {

			}
		});
		
		return l;
	}

	@Override
	public void clear() throws PersistenceException {
		PreparedStatement p = null;
		try {
			p = connection.prepareStatement("DELETE FROM BODYPART; DELETE FROM PATIENTVERSION; DELETE FROM PATIENT;");
			p.execute();
			p.close();
		} catch (SQLException e) {
			//log.debug("Error in findAll", e);
			throw new PersistenceAccessException(e);
		}
		finally {
			if (p!=null) { 
				try {
					p.close();
				} catch (SQLException e) {
					//log.debug("Error while closing statement in findAll", e);
				}
			}
		}
	}
	
	private Patient patientByResultSet(ResultSet r) throws SQLException {
		Patient p = new Patient();

		p.setId(UUID.fromString(r.getString(1)));
		p.setVersion(r.getInt(2));
		p.setNameGiven(r.getString(3));
		p.setNameFamily(r.getString(4));
		p.setBirthTime(r.getDate(5));
		p.setGender(Gender.getValueOf(r.getString(6)));
		p.setWalkable(r.getBoolean(7));
		p.setRespiration(Condition.getValueOf(r.getString(8)));
		p.setPerfusion(Condition.getValueOf(r.getString(9)));
		p.setMentalStatus(Condition.getValueOf(r.getString(10)));
		p.setPhaseOfLife(PhaseOfLife.getValueOf(r.getString(11)));
		p.setPlacePosition(r.getString(12));
		p.setUrgency(r.getInt(13));
		p.setBloodPressureSystolic(r.getInt(14));
		p.setBloodPressureDiastolic(r.getInt(15));
		p.setPlacePosition(r.getString(16));
		p.setReadyForTransport(r.getBoolean(17));
		p.setHospital(r.getString(18));
		p.setHealthInsurance(r.getString(19));
		p.setTreatment(Treatment.getValueOf(r.getString(20)));
		
		return p;
	}
	
	
}
