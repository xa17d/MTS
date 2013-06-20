package at.mts.server.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import at.mts.entity.Bodyparts;
import at.mts.entity.Condition;
import at.mts.entity.Gender;
import at.mts.entity.Patient;
import at.mts.entity.PhaseOfLife;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

/**
 * Implementierung des PatientDao mittels JDBC-Verbindung
 */
public class PatientDaoJdbc extends GenericDaoJdbc implements PatientDao {
	
	public PatientDaoJdbc(Connection connection) {
		super(connection);
	}

	/**
	 * SQL-Query, das alle Patienten Versionen aus der Datenbank ausliest
	 */
	private static final String sqlSelectQueryBase = "SELECT"+
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
			" v.SalvageInfo," +
			" v.PlacePosition," +
			" v.Urgency," +
			" v.BloodPressureSystolic," +
			" v.BloodPressureDiastolic," +
			" v.Pulse," +
			" v.ReadyForTransport," +
			" v.Hospital," +
			" v.HealthInsurance," +
			" v.Treatment, " +
			" v.Category, " +
			" v.Timestamp, " +
			" v.Gps, " +
			" v.Diagnosis, " +
			" v.CourseOfTreatment, " +
			" v.Id, " +
			" v.Author " +
			"FROM Patient p JOIN PatientVersion v on p.id = v.Patient ";
	
	/**
	 * SQL-Query, das alle Patienten in der aktuellsten Version aus der Datenbank ausliest
	 */
	private static final String sqlSelectQueryLatest = sqlSelectQueryBase + "AND p.Version=v.Version ";

	/**
	 * SQL-Statement fuer das einfuegen einer neuen Patientenversion
	 */
	private static final String sqlInsertPatientVersion =
			"INSERT INTO PatientVersion ("+
			" Patient," +
			" Version," +
			" NameGiven," +
			" NameFamily," +
			" BirthTime," +
			" Gender," +
			" Walkable," +
			" Respiration," +
			" Perfusion," +
			" MentalStatus," +
			" PhaseOfLife," +
			" SalvageInfo," +
			" PlacePosition," +
			" Urgency," +
			" BloodPressureSystolic," +
			" BloodPressureDiastolic," +
			" Pulse," +
			" ReadyForTransport," +
			" Hospital," +
			" HealthInsurance," +
			" Treatment," +
			" Category," +
			" Timestamp," +
			" Gps," +
			" Diagnosis," +
			" CourseOfTreatment," +
			" Author" +
			") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String sqlInsertPatient = "INSERT INTO Patient (guid, version) VALUES (?, ?)";
	
	private static final String sqlUpdatePatient = "UPDATE Patient SET version = ? WHERE id = ?";
	
	private static final String sqlInsertBodyPart = "INSERT INTO Bodypart (patientVersion, key, value) VALUES (?,?,?)";
	private static final String sqlSelectQueryBodyPart = "SELECT key, value FROM Bodypart WHERE patientVersion = ?";
	
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
		if (id == null) { return null; }
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return sqlSelectQueryLatest + " WHERE p.Guid = ?";
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
				return sqlSelectQueryBase + " WHERE p.Guid = ? AND v.version = ?";
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
	public Patient findByTimestamp(final UUID id, final Date timestamp) throws PersistenceException {
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return sqlSelectQueryBase + " WHERE p.Guid = ? AND v.timetamp >= ? ORDER BY v.timestamp ASC LIMIT 1";
			}
			
			@Override
			public void setParameters(PreparedStatement p) throws SQLException {
				p.setString(1, id.toString());
				statementSetDate(p, 2, timestamp);
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

		if (patient == null) { throw new PersistenceArgumentException("Patient darf nicht null sein", null); }
		if (patient.getId() == null) { throw new PersistenceArgumentException("Patient muss eine ID haben", null); }

		PreparedStatement p = null;
		try {
			
			p = connection.prepareStatement("SELECT id, version FROM Patient WHERE guid = ?");
			
			String guidString = patient.getId().toString();
			p.setString(1, guidString);
			
			ResultSet r = p.executeQuery();
			
			int patientId = -1;
			int version = -1;
			
			if (r.next()) {
				patientId = r.getInt(1);
				version = r.getInt(2) + 1;
				p.close();
			}
			else {
				p.close();
				
				p = connection.prepareStatement(sqlInsertPatient, Statement.RETURN_GENERATED_KEYS);
				
				p.setString(1, guidString);
				p.setInt(2, 1);
				
				p.executeUpdate();
				
				ResultSet keySet = p.getGeneratedKeys();
				if (keySet != null && keySet.next()) {
					patientId = keySet.getInt(1);
					version = 1;
				}
				else {
					//log.debug("No keys returned in create");
					throw new PersistenceAccessException(null);
				}
				
				p.close();
			}
			
			
			p = connection.prepareStatement(sqlInsertPatientVersion, Statement.RETURN_GENERATED_KEYS);
			setPreparedStatement(p, patient, patientId, version);
			p.executeUpdate();
			int patientVersionId = -1;
			ResultSet keySet = p.getGeneratedKeys();
			if (keySet != null && keySet.next()) {
				patientVersionId = keySet.getInt(1);
			}
			else {
				//log.debug("No keys returned in create");
				throw new PersistenceAccessException(null);
			}
			p.close();
			
			p = connection.prepareStatement(sqlUpdatePatient);
			p.setInt(1, version);
			p.setInt(2, patientId);
			p.executeUpdate();
			p.close();
			
			patient.setVersion(version);
			
			insertBodyParts(patient.getBodyparts(), patientVersionId);
			
		} catch (SQLIntegrityConstraintViolationException e) {
			//log.debug("Integrity violation in create", e);
			throw new PersistenceIntegrityException(e);
		} catch (SQLException e) {
			//log.debug("Error in create", e);
			throw new PersistenceAccessException(e);
		}
		finally {
			if (p!=null) { 
				try {
					p.close();
				} catch (SQLException e) {
					//log.debug("Error while closing statement in create", e);
				}
			}
		}
	}

	@Override
	public List<Patient> findBy(final TriageCategory category, final Treatment treatment) throws PersistenceException {
		
		List<Patient> l;
		
		if ((category != TriageCategory.notSpecified)&&(treatment != Treatment.notSpecified)) {
			l = queryPatients(new StatementPreparation() {
				@Override public String sql() { return sqlSelectQueryLatest + " WHERE v.Category = ? AND v.Treatment = ?"; }
				@Override public void setParameters(PreparedStatement p) throws SQLException {
					p.setString(1, category.toString());
					p.setString(2, treatment.toString());
				}
			});
		}
		else if ((category == TriageCategory.notSpecified)&&(treatment != Treatment.notSpecified)) {
			l = queryPatients(new StatementPreparation() {
				@Override public String sql() { return sqlSelectQueryLatest + " WHERE v.Treatment = ?"; }
				@Override public void setParameters(PreparedStatement p) throws SQLException {
					p.setString(1, treatment.toString());
				}
			});
		}
		else if ((category != TriageCategory.notSpecified)&&(treatment == Treatment.notSpecified)) {
			l = queryPatients(new StatementPreparation() {
				@Override public String sql() { return sqlSelectQueryLatest + " WHERE v.Category = ?"; }
				@Override public void setParameters(PreparedStatement p) throws SQLException {
					p.setString(1, category.toString());
				}
			});
		}
		else {
			l = queryPatients(new StatementPreparation() {
				@Override public String sql() { return sqlSelectQueryLatest; }
				@Override public void setParameters(PreparedStatement p) throws SQLException { }
			});
		}
		
		return l;
	}

	@Override
	public List<Patient> findAll() throws PersistenceException {
		List<Patient> l = queryPatients(new StatementPreparation() {
			
			@Override
			public String sql() {
				return sqlSelectQueryLatest;
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
			p = connection.prepareStatement("DELETE FROM BODYPART;");
			p.executeUpdate();
			p.close();
			
			p = connection.prepareStatement("DELETE FROM PATIENTVERSION;");
			p.executeUpdate();
			p.close();
			
			p = connection.prepareStatement("DELETE FROM PATIENT;");
			p.executeUpdate();
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
		p.setNameGiven((String)r.getObject(3));
		p.setNameFamily((String)r.getObject(4));
		p.setBirthTime((Date)r.getObject(5));
		p.setGender(Gender.getValueOf(r.getString(6)));
		p.setWalkable((Boolean)r.getObject(7));
		p.setRespiration(Condition.getValueOf(r.getString(8)));
		p.setPerfusion(Condition.getValueOf(r.getString(9)));
		p.setMentalStatus(Condition.getValueOf(r.getString(10)));
		p.setPhaseOfLife(PhaseOfLife.getValueOf(r.getString(11)));
		p.setSalvageInfo((String)r.getObject(12));
		p.setPlacePosition((String)r.getObject(13));
		p.setUrgency((Integer)r.getObject(14));
		p.setBloodPressureSystolic((Integer)r.getObject(15));
		p.setBloodPressureDiastolic((Integer)r.getObject(16));
		p.setPulse((Integer)r.getObject(17));
		p.setReadyForTransport((Boolean)r.getObject(18));
		p.setHospital((String)r.getObject(19));
		p.setHealthInsurance((String)r.getObject(20));
		p.setTreatment(Treatment.getValueOf(r.getString(21)));
		p.setCategory(TriageCategory.getValueOf(r.getString(22)));
		p.setTimestamp((Date)r.getObject(23));
		p.setGps((String)r.getObject(24));
		p.setDiagnosis((String)r.getObject(25));
		p.setCourseOfTreatment((String)r.getObject(26));
		int patientVersionId = r.getInt(27);
		String author = ((String)r.getObject(28));
		if (author == null) {
			p.setAuthorId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
			p.setAuthorNameFamily("");
			p.setAuthorNameGiven("");
		}
		else {
			String[] authorData = author.split(";");
			p.setAuthorId(UUID.fromString(authorData[2]));
			p.setAuthorNameFamily(authorData[0]);
			p.setAuthorNameGiven(authorData[1]);
		}
		
		p.setBodyparts(bodypartsById(patientVersionId));
		
		return p;
	}
	
	private void statementSetLongVarchar(PreparedStatement s, int index, String value) throws SQLException {
		if (value == null) {
			s.setNull(index, java.sql.Types.LONGNVARCHAR);
		}
		else {
			s.setString(index, value);
		}
	}
	
	private void statementSetDate(PreparedStatement s, int index, Date value) throws SQLException {
		if (value == null) {
			s.setNull(index, java.sql.Types.DATE);
		}
		else {
			s.setDate(index, new java.sql.Date(value.getTime()));
		}
	}
	
	private void statementSetVarchar(PreparedStatement s, int index, Object value) throws SQLException {
		if (value == null) {
			s.setNull(index, java.sql.Types.VARCHAR);
		}
		else {
			s.setString(index, value.toString());
		}
	}
	
	private void statementSetEnum(PreparedStatement s, int index, Object value) throws SQLException {
		statementSetVarchar(s, index, value);
	}
	
	private void statementSetBoolean(PreparedStatement s, int index, Boolean value) throws SQLException {
		if (value == null) {
			s.setNull(index, java.sql.Types.BOOLEAN);
		}
		else {
			s.setBoolean(index, value);
		}
	}
	
	private void statementSetInt(PreparedStatement s, int index, Integer value) throws SQLException {
		if (value == null) {
			s.setNull(index, java.sql.Types.INTEGER);
		}
		else {
			s.setInt(index, value);
		}
	}
	private void setPreparedStatement(PreparedStatement s, Patient p, int patientId, int version) throws SQLException {
		
		s.setInt(1, patientId);
		s.setInt(2, version);
		statementSetLongVarchar(s, 3, p.getNameGiven());
		statementSetLongVarchar(s, 4, p.getNameFamily());
		statementSetDate(s, 5, p.getBirthTime());
		statementSetEnum(s, 6, p.getGender());
		statementSetBoolean(s, 7, p.getWalkable());
		statementSetEnum(s, 8, p.getRespiration());
		statementSetEnum(s, 9, p.getPerfusion());
		statementSetEnum(s, 10, p.getMentalStatus());
		statementSetEnum(s, 11, p.getPhaseOfLife());
		statementSetLongVarchar(s, 12, p.getSalvageInfoString());
		statementSetLongVarchar(s, 13, p.getPlacePosition());
		statementSetInt(s, 14, p.getUrgency());
		statementSetInt(s, 15, p.getBloodPressureSystolic());
		statementSetInt(s, 16, p.getBloodPressureDiastolic());
		statementSetInt(s, 17, p.getPulse());
		statementSetBoolean(s, 18, p.getReadyForTransport());
		statementSetLongVarchar(s, 19, p.getHospital());
		statementSetLongVarchar(s, 20, p.getHealthInsurance());
		statementSetEnum(s, 21, p.getTreatment());
		statementSetEnum(s, 22, p.getCategory());
		statementSetDate(s, 23, p.getTimestamp());
		statementSetVarchar(s, 24, p.getGps());
		statementSetLongVarchar(s, 25, p.getDiagnosisString());
		statementSetLongVarchar(s, 26, p.getCourseOfTreatmentString());
		String author;
		if (p.getAuthorId() != null) {
			author = p.getAuthorNameFamily() + ";" + p.getAuthorNameGiven() + ";" + p.getAuthorId().toString();
		} else { author = ";;00000000-0000-0000-0000-000000000000"; }
		statementSetLongVarchar(s, 27, author);
	}
	
	private void insertBodyParts(Bodyparts bodyparts, int patientVersionId) throws SQLException {
		Set<String> keys = bodyparts.keySet();
		for (String key : keys) {
		
			PreparedStatement p = null;
			try {
				p = connection.prepareStatement(sqlInsertBodyPart);
				p.setInt(1, patientVersionId);
				p.setString(2, key);
				p.setString(3, bodyparts.get(key));
				p.executeUpdate();
				p.close();
			}
			catch (SQLException e) {
				throw e;
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
	}
	
	private Bodyparts bodypartsById(int patientVersionId) throws SQLException {
		Bodyparts items = new Bodyparts();
		
		PreparedStatement p = null;
		try {
			p = connection.prepareStatement(sqlSelectQueryBodyPart);

			p.setInt(1, patientVersionId);
			
			ResultSet r = p.executeQuery();
			
			while (r.next())
			{
				String key = (String)r.getObject(1);
				String value = (String)r.getObject(2);
				
				items.set(key, value);
			}
			
			p.close();
		} catch (SQLException e) {
			throw e;
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
}
