package moco.android.mtsdevice.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import moco.android.mtsdevice.service.PatientService;

import org.apache.commons.io.IOUtils;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

public class ServerCommunicationImpl implements ServerCommunication {

	public String getData(String urlString) throws CommunicationException {
		
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
	
	public int putData(String urlString, String xmlData) throws CommunicationException {
		
		resetPolicy();
		
		int code = -1;
		
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type","text/xml");
			
			con.connect();

			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
			
			writer.append(xmlData);
			writer.flush();
			
			code = con.getResponseCode();
			
			writer.close();
			
		} catch (MalformedURLException e1) {
			throw new CommunicationException(e1.getMessage());
		} catch (ProtocolException e2) {
			throw new CommunicationException(e2.getMessage());
		} catch (IOException e3) {
			throw new CommunicationException(e3.getMessage());
		}
		
		return code;
	}
	
	
	/**
	 * TODO
	 * aendert Thread-Policy, sodass im Main-Thread auf den Server zugegriffen werden kann
	 */
	private void resetPolicy() {
		
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
	}
	
	
	
	
//	PatientService service;
//	
//	String getBody;
//	int putCode;
//	int postCode;
//	
//	public ServerCommunicationImpl(PatientService service) {
//		
//		this.service = service;
//	}
//	
//	public String getData(String urlString) throws CommunicationException {
//		
//		service.networkConnectionStarted();
//				
//		AsyncTask<String,Void,String> asyncTask = new GetDataTask().execute(urlString);
//		
//		try {
//			asyncTask.wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(getBody.equals("**EXCEPTION**"))
//			throw new CommunicationException("Fehler beim Laden der Daten");
//		
//		return this.getBody;
//	}
//	
//	public int putData(String urlString, String xmlData) throws CommunicationException {
//		
//		service.networkConnectionStarted();
//		
//		AsyncTask<String,Void,Integer> asyncTask = new PutDataTask().execute(urlString, xmlData);
//		
//		try {
//			asyncTask.wait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		if(putCode == -1)
//			throw new CommunicationException("Fehler beim Senden der Daten");
//		
//		return this.putCode;
//	}
//	
//	public int postData(String urlString, String xmlData) throws CommunicationException {
//		
//		//TODO
//		return -1;
//	}
//	
//	private class PutDataTask extends AsyncTask<String,Void,Integer> {
//
//		/**
//		 * Sendet Daten an den Server
//		 * params[0] URL-String
//		 * params[1] XML-Daten
//		 */
//		@Override
//		protected Integer doInBackground(String... params) {
//
//			int code = -1;
//			
//			try {
//				
//				URL url = new URL(params[0]);
//				HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//				con.setDoOutput(true);
//				con.setDoInput(true);
//				con.setRequestMethod("PUT");
//				con.setRequestProperty("Content-Type","text/xml");
//				
//				con.connect();
//
//				OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
//				writer.append(params[1]);
//				writer.flush();
//				
//				code = con.getResponseCode();
//				
//				writer.close();
//				
//			} catch (MalformedURLException e1) {
//				return -1;
//			} catch (ProtocolException e2) {
//				return -1;
//			} catch (IOException e3) {
//				return -1;
//			}
//			
//			return code;
//		}
//		
//		@Override
//		protected void onPostExecute(Integer result) {
//			
//			notifyAll();
//			
//			service.networkConnectionEnded();
//			putCode = result;
//		}
//	}
//	
//	
//	private class GetDataTask extends AsyncTask<String,Void,String> {
//
//		/**
//		 * Holt Daten vom Server
//		 * params[0] URL-String
//		 */
//		@Override
//		protected String doInBackground(String... params) {
//
//			String body = null;
//			
//			try {
//				
//				URL url = new URL(params[0]);
//				URLConnection con = url.openConnection();
//
//				InputStream in = con.getInputStream();
//				body = IOUtils.toString(in, "UTF-8");
//				
//			} catch (MalformedURLException e1) {
//				return "**EXCEPTION**";
//			} catch (IOException e2) {
//				return "**EXCEPTION**";
//			}
//			
//			return body;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			
//			notifyAll();
//			
//			service.networkConnectionEnded();
//			getBody = result;
//		}
//	}
}
