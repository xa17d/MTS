package at.mts.server.persistence;

/**
 * Fehler in der Persistence-Schicht
 * @author Daniel
 */
@SuppressWarnings("serial")
public abstract class PersistenceException extends Exception {
	public PersistenceException() { }
	/**
	 * Erzeugt neue PersistenceException
	 * @param message Nachricht
	 * @param cause Ausloeser fuer diesen Fehler
	 */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}
}
