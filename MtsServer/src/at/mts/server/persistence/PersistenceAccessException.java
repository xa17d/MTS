package at.mts.server.persistence;

/**
 * Unbekannter Fehler waehrend eines Daten-Zugriffs
 * @author Daniel
 */
@SuppressWarnings("serial")
public class PersistenceAccessException extends PersistenceException {
	public PersistenceAccessException(Throwable cause) {
		super("Daten-Zugriffs Fehler", cause);
	}
}
