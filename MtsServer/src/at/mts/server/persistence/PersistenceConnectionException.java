package at.mts.server.persistence;

/**
 * Fehler waehrend eines Verbindungs-Aufbaus oder Verbindungs-Abbaus
 * @author Daniel
 */
@SuppressWarnings("serial")
public class PersistenceConnectionException extends PersistenceException {	public PersistenceConnectionException() { }
	public PersistenceConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
