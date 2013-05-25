package at.mts.server.service;

/**
 * Fehler der in der Service-Schicht aufgetreten ist
 * @author Daniel
 */
@SuppressWarnings("serial")
public abstract class ServiceException extends Exception {
	public ServiceException() { }
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
