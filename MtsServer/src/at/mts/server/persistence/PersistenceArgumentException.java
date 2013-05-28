package at.mts.server.persistence;

/**
 * Fehler waehrend eines Verbindungs-Aufbaus oder Verbindungs-Abbaus
 * @author Daniel
 */
@SuppressWarnings("serial")
public class PersistenceArgumentException extends PersistenceException {	
	public PersistenceArgumentException() { }
	public PersistenceArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
}