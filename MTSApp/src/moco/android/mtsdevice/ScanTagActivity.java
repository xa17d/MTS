package moco.android.mtsdevice;

import java.util.UUID;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.Role;
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
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;

public class ScanTagActivity extends Activity implements LocationListener {
	
	private PatientService service;
	
	private Patient selectedPatient;
	private String scannedString;
	private UUID scannedId;
	
	/**
	 * Location
	 */
	private LocationManager locationManager;
	private String provider;
	private String locationString;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_tag);
		
		service = PatientServiceImpl.getInstance();
		
		initGps();
		//startNfcScan();
	}
	
	/*
	private void startNfcScan() {
		
		NdefMessage[] msgs = null;
		Intent intent = getIntent();
		
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
			scannedId = UUID.fromString(msgs[0].toString());
		} catch(Exception e) {
			new AlertDialog.Builder(this) 
		        	.setMessage(R.string.error_scan_tag)
		        	.setNeutralButton(R.string.ok, null)
		        	.show();
		}
		
		tagScanned();
	}
	*/
	
	
	/**
	 * alternativer QR-Scan
	 */
	public void scanQr(View v) {
		
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.setPackage("com.google.zxing.client.android");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 0) {
	        if (resultCode == RESULT_OK) {
	        	
	        	scannedString = intent.getStringExtra("SCAN_RESULT");
	        	boolean error = false;
	        	
	        	try {
		        	if(Mode.getActiveMode() != Mode.loggedout)
		        		scannedId = UUID.fromString(scannedString);	  
	        	} catch(Exception e) {
	        		error = true;
					new AlertDialog.Builder(this) 
			        	.setMessage(R.string.error_scan_tag)
			        	.setNeutralButton(R.string.ok, null)
			        	.show();
	        	}
	            
	        	if(!error)
	        		tagScanned();
	            
	        }
	    }
	}
	
	
	
	private void tagScanned() {
		
		Intent intent;
		
		/**
		 * Triage-Modus
		 * Patient mit GPS-Koordinaten speichern und an Backend senden
		 */
		if(Mode.getActiveMode() == Mode.triage) {
			
			selectedPatient = SelectedPatient.getPatient();
			selectedPatient.setId(scannedId);
			selectedPatient.setGps(locationString);
			
			boolean error = false;
			
			try {
				service.saveNewPatient(selectedPatient);
				Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
			} catch (ServiceException e) {
				error = true;
				new AlertDialog.Builder(this) 
		        	.setMessage(R.string.error_save_data)
		        	.setNeutralButton(R.string.ok, null)
		        	.show();
			}
			
			if(!error) {
				intent = new Intent(this, TriageSelectionActivity.class);
				startActivity(intent);
				finish();
			}
		}
		
		/**
		 * Berge-Modus
		 * Patientendaten laden
		 */
		if(Mode.getActiveMode() == Mode.salvage) {
			
			boolean error = false;
			
			try {
				selectedPatient = service.loadPatientById(scannedId);
			} catch (ServiceException e) {
				error = true;
				new AlertDialog.Builder(this) 
			        	.setMessage(R.string.error_load_data)
			        	.setNeutralButton(R.string.ok, null)
			        	.show();
			}
			
			if(!error) {
			
				if(selectedPatient.getTreatment() == Treatment.sighted) {
					SelectedPatient.setPatient(selectedPatient);
					
					intent = new Intent(this, SalvageActivity.class);
					startActivity(intent);
					finish();
				}
				else
					new AlertDialog.Builder(this) 
			        	.setMessage(R.string.error_not_on_salvagelist)
			        	.setNeutralButton(R.string.ok, null)
			        	.show();
			}
		}
		
		/**
		 * Behandlungs-Modus
		 * Patientendaten laden
		 */
		if(Mode.getActiveMode() == Mode.therapy) {
			
			boolean error = false;
			
			try {
				selectedPatient = service.loadPatientById(scannedId);
			} catch (ServiceException e) {
				error = true;
				new AlertDialog.Builder(this) 
			        	.setMessage(R.string.error_load_data)
			        	.setNeutralButton(R.string.ok, null)
			        	.show();
			}
			
			if(!error) {
			
				SelectedPatient.setPatient(selectedPatient);
				
				if(Area.getActiveArea().matchesCategory(selectedPatient.getCategory()) && selectedPatient.getTreatment() == Treatment.salvaged) {
					intent = new Intent(this, TherapySelectionActivity.class);
					startActivity(intent);
					finish();
				}
				else
					new AlertDialog.Builder(this) 
				        	.setMessage(R.string.error_wrong_area)
				        	.setNeutralButton(R.string.ok, null)
				        	.show();
			}
		}
		
		/**
		 * Anmeldung
		 */
		if(Mode.getActiveMode() == Mode.loggedout) {
			
			String[] params = scannedString.split(";");
			
			if(params[2].equals("1"))
				Role.setActiveRole(Role.MD);
			else if(params[2].equals("2"))
				Role.setActiveRole(Role.PARAMEDIC);
			else if(params[2].equals("3"))
				Role.setActiveRole(Role.FIREFIGHTER);
			else
				Role.setActiveRole(Role.NATIONALGUARD);
			
			service.setAuthorId(params[3]);
			Mode.setActiveMode(Mode.loggedin);
			
			finish();
		}
	}
	
	private void initGps() {
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			onLocationChanged(location);
		} else if(Mode.getActiveMode() == Mode.triage) {
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

		locationString = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude()) + "," + String.valueOf(location.getAccuracy());
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		//do nothing
	}

	@Override
	public void onProviderEnabled(String provider) {
		//do nothing
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//do nothing
	}

	@Override
	public void onBackPressed() {

		DeviceButtons.getToModeSelection(this);
	}
}
