package at.mts.server;

@SuppressWarnings("serial")
public class ServerException extends Exception {
	public ServerException() { }
	/**
	 * Erzeugt neue PersistenceException
	 * @param cause Ausloeser fuer diesen Fehler
	 */
	public ServerException(Throwable cause) {
		super(cause);
	}
}
