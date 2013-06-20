package moco.android.mtsdevice.communication;

import java.util.UUID;

public interface ServerCommunication {

	/**
	 * sendet Anfrage an den Server und liefert das Ergebnis als String zurueck
	 * @param urlString Adresse des Servers
	 * @return Antwort des Servers
	 * @throws CommunicationException
	 */
	public String getData(String urlString) throws CommunicationException;
	
	/**
	 * sendet die Daten via PUT an den Server, der die Daten dann speichert
	 * @param urlString URL
	 * @param xmlData Daten
	 * @return Response Code des Servers
	 * @throws CommunicationException
	 */
	public int putData(String urlString, String xmlData) throws CommunicationException;
	
	/**
	 * sendet die Daten via POST an den Server, der die Daten dann updated
	 * @param urlString
	 * @param xmlData
	 * @return
	 * @throws CommunicationException
	 */
	public int postData(String urlString, String xmlData) throws CommunicationException;
	
	/**
	 * setzt neue Authoren-ID
	 * @param id ID
	 */
	public void setAuthorId(String id);
}
