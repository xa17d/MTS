package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.salvage.SalvageActivity;
import moco.android.mtsdevice.therapy.TherapyVitalParameterActivity;
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

public class ScanTagActivity extends Activity implements LocationListener {
	
	//private NdefMessage[] msgs = null;
	
	private Patient selectedPatient;
	
	private LocationManager locationManager;
	private String provider;
	private String locationString;
	private String locationAccuracyString;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_tag);
		
		initGps();
	}
	
	public void tagScanned(View v) {
		
		Intent intent = this.getIntent();
		
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
		} catch(Exception e) {
			scanText.setText("kein Tag gescannt");
		}
		*/
		
		/**
		 * Patient mit GPS-Koordinaten speichern und an Backend senden
		 */
		if(Mode.getActiveMode() == Mode.triage) {
			
			
			selectedPatient = SelectedPatient.getPatient();
			
			//TODO GPS Koordinaten speichern
			
			//TODO Daten versenden
			
			Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
			Toast.makeText(this, "GPS-Koordinaten: " + locationString + "\nGenauigkeit: " + locationAccuracyString, Toast.LENGTH_LONG).show();
			
			intent = new Intent(ScanTagActivity.this, TriageSelectionActivity.class);
			startActivity(intent);
			finish();
			
			/*
			new AlertDialog.Builder(this) 
            	.setMessage(R.string.info_saved + "\nGPS: " + locationString)
            	.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(ScanTagActivity.this, TriageSelectionActivity.class);
						startActivity(intent);
						finish();
					}
				})
            	.show();
            */
		}
		
		if(Mode.getActiveMode() == Mode.salvage) {
			
			SelectedPatient.setPatient(selectedPatient);
			intent = new Intent(this, SalvageActivity.class);
			startActivity(intent);
		}
		
		if(Mode.getActiveMode() == Mode.therapy) {
			
			//TODO Kategorie unterscheiden (minor und deceased zuerst stammdaten / immediate und delayed zuerst med Infos)
			
			if(SelectedPatient.getPatient() == null) {
				Patient p = new Patient();			//TESTPATIENT
				p.setNameFamily("Dobler");
				p.setNameGiven("Lucas");
				p.setPulse(95);
				
				SelectedPatient.setPatient(p);
			}
			
			
			intent = new Intent(this, TherapyVitalParameterActivity.class);
			startActivity(intent);
		}
	}
	
	private void initGps() {

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// Define the criteria how to select the locatioin provider -> use
		// default
		Criteria criteria = new Criteria();
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
		locationManager.requestLocationUpdates(provider, 2000, 2, this);
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
