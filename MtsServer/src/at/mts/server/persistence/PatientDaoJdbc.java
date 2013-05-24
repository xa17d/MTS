package at.mts.server.persistence;

import java.sql.Connection;

public class PatientDaoJdbc extends GenericDaoJdbc implements PatientDao {
	
	public PatientDaoJdbc(Connection connection) {
		super(connection);
	}
	
	
}
