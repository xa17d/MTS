package moco.android.mtsdevice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import moco.android.mtsdevice.communication.CommunicationException;
import moco.android.mtsdevice.communication.ServerCommunication;
import moco.android.mtsdevice.communication.ServerCommunicationImpl;

import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.PatientListItem;
import at.mts.entity.cda.CdaDocument;
import at.mts.entity.cda.CdaIdV;

public class PatientServiceImpl implements PatientService {
	
	private ServerCommunication com;
	
	private static final String DEFAULT_ADDRESS = "http://88.116.105.228:30104/MtsServer/restApi/patients/";
	private String mtsUrl;
	private String authorId;
	
	private int code;
	private Set<String[]> putBuffer = new HashSet<String[]>();
	private Set<String[]> postBuffer = new HashSet<String[]>();
	
	@Override
	public ArrayList<PatientListItem> loadAllPatients() throws ServiceException {
		
		checkAuthentification();
		
		String xmlResult;
		
		try {
			xmlResult = com.getData(mtsUrl);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}
		
		checkBuffer();
		
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
		checkBuffer();
		
		return new Patient(doc);
	}

	@Override
	public void saveNewPatient(Patient patient) throws ServiceException {
		
		checkAuthentification();
		
		String urlString = mtsUrl + patient.getId();
		
		CdaDocument doc = new CdaDocument(patient);
		String xmlData = doc.asXml();
		
		try {
			code = com.putData(urlString, xmlData);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}
		
		if(code >= 400)
			putBuffer.add(new String[] {urlString,xmlData});
		else
			checkBuffer();
	}

	@Override
	public void updateExistingPatient(Patient patient) throws ServiceException {
		
		checkAuthentification();
		
		String urlString = mtsUrl + patient.getId();
		
		int oldVersion = patient.getVersion() + 1;
		patient.setVersion(oldVersion);
		CdaDocument doc = new CdaDocument(patient);
		doc.setParentIdV(new CdaIdV(patient.getId(),oldVersion));
		
		String xmlData = doc.asXml();
		
		try {
			code = com.postData(urlString, xmlData);
		} catch (CommunicationException e) {
			throw new ServiceException(e.getMessage());
		}
		
		if(code >= 400)
			postBuffer.add(new String[] {urlString,xmlData});
		else
			checkBuffer();
	}
	
	private void checkBuffer() throws ServiceException {
		
		if(putBuffer.size() != 0) {
			
			Iterator<String[]> it = putBuffer.iterator();
			
			int code;
			String[] temp;
			
			while(it.hasNext()) {
				temp = it.next();
				try {
					code = com.putData(temp[0], temp[1]);
				} catch (CommunicationException e) {
					throw new ServiceException(e.getMessage());
				}
				
				if(code < 400)
					it.remove();
			}
			
			it = postBuffer.iterator();
			
			while(it.hasNext()) {
				temp = it.next();
				try {
					code = com.postData(temp[0], temp[1]);
				} catch (CommunicationException e) {
					throw new ServiceException(e.getMessage());
				}
				
				if(code < 400)
					it.remove();
			}
		}
	}
	
	
	@Override
	public void setAuthorId(String id) {
		
		this.authorId = id;
		com.setAuthorId(authorId);
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

		if(authorId == null)
			throw new ServiceException("Es muss zuerst der Helfer authentifiziert werden!");
	}
	
	
	/**
	 * Singleton
	 */
	private static PatientService instance = null;
	
	private PatientServiceImpl() {
		
		com = new ServerCommunicationImpl();
		this.setDefaultMtsServerAddress();
	}
	
	public static PatientService getInstance() {
		
		if(instance == null)
			instance = new PatientServiceImpl();
	
		return instance;
	}
}
