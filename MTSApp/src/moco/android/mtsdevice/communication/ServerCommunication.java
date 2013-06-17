package moco.android.mtsdevice.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.cda.CdaDocument;

public class ServerCommunication {
	
	private static final String DEFAULT_ADDRESS = "http://88.116.105.228:30104/MtsServer/restApi/patients/";
	private String mtsServerAddress;
	
	/*
	 * ### Daten empfangen ###
	 */
	
	/**
	 * liefert alle Patienten
	 * @return alle Patienten
	 * @throws CommunicationException
	 */
	public PatientList loadAllPatients() throws CommunicationException {

		String urlString = mtsServerAddress;
		
		resetPolicy();
		
		String xmlResult = this.getData(urlString);
		
		return new PatientList(xmlResult);
	}
	
	/**
	 * liefert einen Patienten mit einer eindeutigen ID
	 * @param id ID
	 * @return Patient mit der ID id
	 * @throws CommunicationException
	 */
	public Patient loadPatientById(String id) throws CommunicationException {
		
		String urlString = mtsServerAddress + id;
		
		String xmlResult = this.getData(urlString);
		CdaDocument doc = new CdaDocument(xmlResult);
		return new Patient(doc);
	}
	
	/**
	 * liefert einen Patienten mit einer eindeutigen URL
	 * @param urlString URL
	 * @return  Patient mit der URL urlString
	 * @throws CommunicationException
	 */
	public Patient loadPatientByUrl(String urlString) throws CommunicationException {
		
		String xmlResult = this.getData(urlString);
		CdaDocument doc = new CdaDocument(xmlResult);
		return new Patient(doc);
	}
	
	/**
	 * sendet Anfrage an den Server und liefert das Ergebnis als String zurueck
	 * @param urlString Adresse des Servers
	 * @return Antwort des Servers
	 * @throws CommunicationException
	 */
	private String getData(String urlString) throws CommunicationException {
		
		String body = "";
		
		resetPolicy();
		
		try {
			
			URL url = new URL(urlString);
			URLConnection con = url.openConnection();

			InputStream in = con.getInputStream();
			body = IOUtils.toString(in, "UTF-8");
			
		} catch (MalformedURLException e1) {
			throw new CommunicationException(e1.getMessage());
		} catch (IOException e2) {
			throw new CommunicationException(e2.getMessage());
		}

		return body;
	}
	
	/*
	 * ### Daten versenden ###
	 */
	
	public void savePatient(Patient patient) throws CommunicationException {
		
		String urlString = mtsServerAddress + patient.getId();
		
		CdaDocument doc = new CdaDocument(patient);
		String xmlData = doc.asXml();
		
		this.putData(urlString, xmlData);
	}
	
	private void putData(String urlString, String xmlData) throws CommunicationException {
		
		resetPolicy();
		
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			
			con.setRequestMethod("PUT");

			
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			writer.write(xmlData);
			writer.close();
			
		} catch (MalformedURLException e1) {
			throw new CommunicationException(e1.getMessage());
		} catch (ProtocolException e2) {
			throw new CommunicationException(e2.getMessage());
		} catch (IOException e3) {
			throw new CommunicationException(e3.getMessage());
		}
	}
	
	/**
	 * aendert die default-Adresse des Servers
	 * @param mtsServerAddress neue Adresse
	 */
	public void changeMtsServerAddress(String mtsServerAddress) { this.mtsServerAddress = mtsServerAddress; }
	public void setDefaultMtsServerAddress() { this.mtsServerAddress = DEFAULT_ADDRESS; }
	
	/**
	 * Singleton
	 */
	private static ServerCommunication instance = null;
	
	private ServerCommunication() {
		setDefaultMtsServerAddress();
	}
	
	public static ServerCommunication getInstance() {
		
		if(instance == null)
			instance = new ServerCommunication();
		
		return instance;
			
	}
	
	/**
	 * TODO
	 * remove
	 */
	private void resetPolicy() {
		
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
	}
}
