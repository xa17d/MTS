package at.mts.server.service;

/**
 * Fehler beim Zugriff auf die persistenten Daten
 * @author Daniel
 */
@SuppressWarnings("serial")
public class ServiceStorageException extends ServiceException {
	
	public ServiceStorageException(Throwable e) {
		super("Es ist ein Fehler beim Zugriff auf die Programm-Daten aufgetreten", e);
	}
}
