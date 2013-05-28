package at.mts.server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasse zum erzeugen von Hsqldb Datenbank-Verbindungen
 * @author Daniel
 */
public class HsqldbConnection {
	
	/**
	 * Erzeugt eine neue Hsqldb-Verbindung zu einer bestimmten Adresse
	 * @param path Adresse
	 * @throws PersistenceException Wenn der Teiber nicht geladen oder wenn keine Verbindung hergestellt werden kann. 
	 */
	private HsqldbConnection(String path) throws PersistenceException {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		}
		catch (Exception e) {
			throw new PersistenceConnectionException("Datenbank Treiber konnte nicht geladen werden", e);
		}
		try {
			sqlConnection = DriverManager.getConnection(path, "SA", "");
		} catch (SQLException e) {
			throw new PersistenceConnectionException("Verbindung zur Datenbank konnte nicht hergestellt werden. Ist der Datenbank-Server gestartet worden?", e);
		}
	}

	/**
	 * SQL Connection zur Datenbank
	 */
	private Connection sqlConnection = null;
	
	/**
	 * Schliesst die Datenbank-Verbindung
	 * @throws PersistenceException Wenn Verbindung nicht geschlossen werden kann
	 */
	public void close() throws PersistenceException {
		try {
			sqlConnection.close();
		} catch (SQLException e) {
			throw new PersistenceConnectionException("Verbindung kann nicht geschlossen werden", e);
		}
	}
	
	/**
	 * @return Gibt die SQL Verbindung zurueck
	 */
	public Connection getSqlConnection() {
		return sqlConnection;
	}
	
	/**
	 * Verbindung die fuer den Normal-Betrieb verwendet wird
	 */
	private static HsqldbConnection connection = null;
	
	/**
	 * @return Gibt die Verbindung zurueck
	 * @throws PersistenceException Wenn der Teiber nicht geladen oder wenn keine Verbindung hergestellt werden kann
	 */
	public static Connection getConnection() throws PersistenceException {
		
		if (connection == null) {
			connection = new HsqldbConnection("jdbc:hsqldb:hsql://localhost/MTS");
		}
		
		return connection.getSqlConnection();
	}
	
	/**
	 * Schliesst die Verbindung fuer den Normal-Betrieb
	 * @throws PersistenceException Wenn Verbindung nicht geschlossen werden kann
	 */
	public static void closeConnection() throws PersistenceException {
		
		if (connection != null)
		{
			connection.close();
			connection = null;
		}
	}
}

