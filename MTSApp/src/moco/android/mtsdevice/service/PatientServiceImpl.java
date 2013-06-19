package moco.android.mtsdevice.service;

import java.util.ArrayList;
import java.util.UUID;

import moco.android.mtsdevice.communication.CommunicationException;
import moco.android.mtsdevice.communication.ServerCommunication;
import moco.android.mtsdevice.communication.ServerCommunicationImpl;

import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.PatientListItem;
import at.mts.entity.cda.CdaDocument;

public class PatientServiceImpl implements PatientService {
	
	private ServerCommunication com;
	
	private static final String DEFAULT_ADDRESS = "http://88.116.105.228:30104/MtsServer/restApi/patients/";
	private String mtsUrl;
	private UUID authorId;
	
	@Override
	public ArrayList<PatientListItem> loadAllPatients() throws ServiceException {
		
		checkAuthentification();
		
		String xmlResult;
		
		try {
			xmlResult = com.getData(mtsUrl);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}
		
		return new PatientList(xmlResult);
	}

	@Override
	public Patient loadPatientById(UUID id) throws ServiceException {

		String urlString = mtsUrl + id.toString();

		return this.loadPatientByUrl(urlString);
	}

	@Override
	public Patient loadPatientByUrl(String urlString) throws ServiceException {

		checkAuthentification();
		
		String xmlResult;
		
		try {
			xmlResult = com.getData(urlString);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}		
		
		CdaDocument doc = new CdaDocument(xmlResult);
		return new Patient(doc);
	}

	@Override
	public void saveNewPatient(Patient patient) throws ServiceException {
		
		checkAuthentification();
		
		String urlString = mtsUrl + patient.getId();
		
		CdaDocument doc = new CdaDocument(patient);
		String xmlData = doc.asXml();
		
		try {
			com.putData(urlString, xmlData);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateExistingPatient(Patient patient) throws ServiceException {
		
		checkAuthentification();
		
		String urlString = mtsUrl + patient.getId();
		
		patient.setVersion(patient.getVersion() + 1);
		CdaDocument doc = new CdaDocument(patient);
		
		String xmlData = doc.asXml();
		
		try {
			com.putData(urlString, xmlData);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}
	}
	
	@Override
	public void networkConnectionStarted() {
		
		//TODO
	}
	
	@Override
	public void networkConnectionEnded() {
		
		//TODO
	}
	
	@Override
	public void setAuthorId(UUID id) {
		
		this.authorId = id;
	}
	
	@Override
	public void changeMtsServerAddress(String mtsUrl) {
		if(!mtsUrl.endsWith("/"))
			mtsUrl.concat("/");
		
		this.mtsUrl = mtsUrl;
	}

	@Override
	public void setDefaultMtsServerAddress() {

		this.mtsUrl = DEFAULT_ADDRESS;
	}
	
	

	private void checkAuthentification() throws ServiceException {

		/*
		if(authorId == null)
			throw new ServiceException("Es muss zuerst der Helfer authentifiziert werden!");
		*/
	}
	
	
	/**
	 * Singleton
	 */
	private static PatientService instance = null;
	
	private PatientServiceImpl() {
		
		com = new ServerCommunicationImpl(this);
		this.setDefaultMtsServerAddress();
	}
	
	public static PatientService getInstance() {
		
		if(instance == null)
			instance = new PatientServiceImpl();
	
		return instance;
	}
}
