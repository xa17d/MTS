package at.mts.server.persistence;

import java.sql.Connection;

public class GenericDaoJdbc {
	
	public GenericDaoJdbc(Connection connection) {
		this.connection = connection;
	}
	
	protected Connection connection;
}
