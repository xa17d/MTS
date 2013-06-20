package moco.android.mtsdevice.service;

import java.util.ArrayList;
import java.util.UUID;

import at.mts.entity.Patient;
import at.mts.entity.PatientListItem;

public interface PatientService {

	/**
	 * liefert alle Patienten
	 * @return alle Patienten
	 * @throws ServiceException
	 */
	public ArrayList<PatientListItem> loadAllPatients() throws ServiceException;
	
	/**
	 * liefert einen Patienten mit einer eindeutigen ID
	 * @param id ID
	 * @return Patient mit der ID id
	 * @throws ServiceException
	 */
	public Patient loadPatientById(UUID id) throws ServiceException;
	
	/**
	 * liefert einen Patienten mit einer eindeutigen URL
	 * @param urlString URL
	 * @return  Patient mit der URL urlString
	 * @throws ServiceException
	 */
	public Patient loadPatientByUrl(String urlString) throws ServiceException;
	
	/**
	 * erstellt neuen Patienten am Server
	 * @param patient neuer Patient
	 * @return Response-Code
	 * @throws ServiceException
	 */
	public int saveNewPatient(Patient patient) throws ServiceException;
	
	/**
	 * veraendert die Daten eines bestehenden Patienten am Server
	 * @param patient bestehender Patient
	 * @throws ServiceException
	 */
	public void updateExistingPatient(Patient patient) throws ServiceException;
	
	
	/**
	 * setzt den aktuellen Benutzer der Anwendung
	 * @param id Benutzer-ID
	 */
	public void setAuthorId(String id);
	
	
	
	/**
	 * aendert die aktuelle Adresse des Servers
	 * @param mtsServerAddress Adresse des Servers
	 */
	public void changeMtsServerAddress(String mtsServerAddress);
	
	/**
	 * setzt wieder die default-Adresse des Servers
	 */
	public void setDefaultMtsServerAddress();
}
