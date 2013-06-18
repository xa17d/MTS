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
	
	public void putData(String urlString, String xmlData) throws CommunicationException {
		
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
	 * TODO
	 * aendert Thread-Policy, sodass im Main-Thread auf den Server zugegriffen werden kann
	 */
	private void resetPolicy() {
		
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
	}
}
