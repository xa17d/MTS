package moco.android.mtsdevice.communication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import at.mts.entity.PatientList;

/*
 * TODO
 * SINGLETON
 */

public class ServerCommunication {
	
	public String loadAllPatients() throws CommunicationException {

		String xmlResponse;
		
		String url = "http://88.116.105.228:30104/MtsServer/restApi/patients";
		
		ThreadPolicy tp = ThreadPolicy.LAX;
		StrictMode.setThreadPolicy(tp);
		
		try {
			CredentialsProvider credProvider = new BasicCredentialsProvider();
			//credProvider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), 
			//							new UsernamePasswordCredentials("YOUR USER NAME HERE", "YOUR PASSWORD HERE"));

			DefaultHttpClient http = new DefaultHttpClient();
			http.setCredentialsProvider(credProvider);
			
			HttpPut put = new HttpPut(url);
			
			/*
			try {
				put.setEntity(new StringEntity(xmlData, "UTF8"));
			} catch (UnsupportedEncodingException ex3) {
				throw new CommunicationException("UnsupportedEncoding: " + ex3);
			}
			*/
			
			//put.addHeader("Content-type","SET CONTENT TYPE HERE IF YOU NEED TO");
			HttpResponse response = http.execute(put);

			xmlResponse = response.getEntity().toString();
			
		} catch (ClientProtocolException ex1) {
			throw new CommunicationException("Client protocol exception: " + ex1);
		} catch (IOException ex2) {
			throw new CommunicationException("IOException: " + ex2);
		}
		
		//PatientList patientList = new PatientList(xmlResponse);
		
		return xmlResponse;
	}
}
