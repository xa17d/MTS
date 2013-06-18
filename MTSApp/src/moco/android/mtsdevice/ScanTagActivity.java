package moco.android.mtsdevice;

import java.util.GregorianCalendar;
import java.util.UUID;

import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.salvage.SalvageActivity;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import moco.android.mtsdevice.therapy.TherapySelectionActivity;
import moco.android.mtsdevice.triage.TriageSelectionActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;
import at.mts.entity.cda.CdaDocument;

public class ScanTagActivity extends Activity implements LocationListener {
	
	//private NdefMessage[] msgs = null;
	
	private PatientService service;
	
	private Patient selectedPatient;
	private UUID scannedId;
	
	/**
	 * Location
	 */
	private LocationManager locationManager;
	private String provider;
	private String locationString;
	private String locationAccuracyString;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_tag);
		
		service = PatientServiceImpl.getInstance();
		
		initGps();
		startScan();
	}
	
	private void startScan() {
		
		/*
		//TODO Testen
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
	        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
	        if (rawMsgs != null) {
	            msgs = new NdefMessage[rawMsgs.length];
	            for (int i = 0; i < rawMsgs.length; i++) {
	                msgs[i] = (NdefMessage) rawMsgs[i];
	            }
	        }
	    }
		
		try {
			scanText.setText(msgs[0].toString());
			scannedId = msgs[0].toString()
		} catch(Exception e) {
			scanText.setText("kein Tag gescannt");
		}
		
		tagScanned(null);
		*/
	}
	
	public void tagScanned(View v) {
		
		Intent intent;
		
		/**
		 * Triage-Modus
		 * Patient mit GPS-Koordinaten speichern und an Backend senden
		 */
		if(Mode.getActiveMode() == Mode.triage) {
			
			selectedPatient = SelectedPatient.getPatient();
			scannedId = UUID.randomUUID();				//TODO zuweisung fuer Test
			selectedPatient.setId(scannedId);
			selectedPatient.setGps(locationString + ";" + locationAccuracyString);
			selectedPatient.setTreatment(Treatment.sighted);
			
//			try {
//				service.saveNewPatient(selectedPatient);
//			} catch (ServiceException e) {
//				new AlertDialog.Builder(this) 
//		        	.setMessage(R.string.error_save_data)
//		        	.setNeutralButton(R.string.ok, null)
//		        	.show();
//			}
			
			Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
			Toast.makeText(this, "GPS-Koordinaten: " + locationString + "\nGenauigkeit: " + locationAccuracyString, Toast.LENGTH_LONG).show();
			
			intent = new Intent(ScanTagActivity.this, TriageSelectionActivity.class);
			startActivity(intent);
			finish();
		}
		
		/**
		 * Berge-Modus
		 * Patientendaten laden
		 */
		if(Mode.getActiveMode() == Mode.salvage) {
			
//			try {
//				selectedPatient = service.loadPatientById(scannedId);
//			} catch (ServiceException e) {
//				new AlertDialog.Builder(this) 
//			        	.setMessage(R.string.error_load_data)
//			        	.setNeutralButton(R.string.ok, null)
//			        	.show();
//			}
//			
//			if(selectedPatient.getTreatment() == Treatment.sighted) {
//				SelectedPatient.setPatient(selectedPatient);
				
				intent = new Intent(this, SalvageActivity.class);
				startActivity(intent);
//			}
//			else
//				new AlertDialog.Builder(this) 
//		        	.setMessage(R.string.error_not_on_salvagelist)
//		        	.setNeutralButton(R.string.ok, null)
//		        	.show();
		}
		
		/**
		 * Behandlungs-Modus
		 * Patientendaten laden
		 */
		if(Mode.getActiveMode() == Mode.therapy) {
			
			if(SelectedPatient.getPatient() == null) {
				Patient p = new Patient();			//TODO TESTPATIENT
				p.setNameFamily("Dobler");
				p.setNameGiven("Lucas");
				p.setCategory(TriageCategory.immediate);
				GregorianCalendar date = new GregorianCalendar(1991, 1, 15);
				p.setBirthTime(date.getTime());
				p.setPulse(95);
				
				selectedPatient = p;
				SelectedPatient.setPatient(selectedPatient);
			}
			
//			try {
//				selectedPatient = service.loadPatientById(scannedId);
//			} catch (ServiceException e) {
//				new AlertDialog.Builder(this) 
//			        	.setMessage(R.string.error_load_data)
//			        	.setNeutralButton(R.string.ok, null)
//			        	.show();
//			}
//			
//			SelectedPatient.setPatient(selectedPatient);
//			
//			if(Area.getActiveArea().matchesCategory(selectedPatient.getCategory()) && selectedPatient.getTreatment() == Treatment.salvaged) {
				intent = new Intent(this, TherapySelectionActivity.class);
				startActivity(intent);
				finish();
//			}
//			else
//				new AlertDialog.Builder(this) 
//			        	.setMessage(R.string.error_wrong_area)
//			        	.setNeutralButton(R.string.ok, null)
//			        	.show();
		}
	}
	
	private void initGps() {

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			onLocationChanged(location);
		} else {
			Toast.makeText(this, R.string.error_gps, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		locationManager.requestLocationUpdates(provider, 200, 1, this);
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {

		locationString = String.valueOf(location.getLatitude()) + "; " + String.valueOf(location.getLongitude());
		locationAccuracyString = String.valueOf(location.getAccuracy());
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackPressed() {

		DeviceButtons.getToModeSelection(this);
	}
}
