package at.mts.server.persistence;

/**
 * Integritaetsverletzung in der Persistence-Schicht
 * @author Daniel
 */
@SuppressWarnings("serial")
public class PersistenceIntegrityException extends PersistenceException {
	
	public PersistenceIntegrityException() { }
	public PersistenceIntegrityException(Throwable cause) {
		super("Datenintegritaet verletzt", cause);
	}
}
