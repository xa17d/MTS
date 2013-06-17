package moco.android.mtsdevice.communication;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.cda.CdaDocument;

/*
 * TODO
 * SINGLETON
 */

public class ServerCommunication {
	
	public PatientList loadAllPatients() throws CommunicationException {

		String urlString = "http://88.116.105.228:30104/MtsServer/restApi/patients";
		
		resetPolicy();
		
		String xmlResult = this.getBody(urlString);
		
		return new PatientList(xmlResult);
	}
	
	public Patient loadPatientById(String id) throws CommunicationException {
		
		String urlString = "http://88.116.105.228:30104/MtsServer/restApi/patients/" + id;
		
		String xmlResult = this.getBody(urlString);
		CdaDocument doc = new CdaDocument(xmlResult);
		return new Patient(doc);
	}
	
	public Patient loadPatientByUrl(String urlString) throws CommunicationException {
		
		String xmlResult = this.getBody(urlString);
		CdaDocument doc = new CdaDocument(xmlResult);
		return new Patient(doc);
	}
	
	public String getBody(String urlString) throws CommunicationException {
		
		String body = "";
		
		resetPolicy();
		
		try {
			URL url = new URL(urlString);

			URLConnection con;
			con = url.openConnection();

			InputStream in = con.getInputStream();
			body = IOUtils.toString(in, "UTF-8");
		} catch (MalformedURLException e1) {
			throw new CommunicationException(e1.getMessage());
		} catch (IOException e2) {
			throw new CommunicationException(e2.getMessage());
		}

		return body;
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
