package moco.android.mtsdevice.salvage;

import java.text.NumberFormat;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import at.mts.entity.Patient;
import at.mts.entity.PatientListItem;

public class SalvageSingleViewActivity extends Activity implements LocationListener {

	private PatientService service;
	
	private PatientListItem selectedPatientItem;
	private Patient selectedPatient;
	
	private TextView salvageText;
	private TextView distanceText;
	private TextView urgencyText;
	
	/**
	 * Location
	 */
	private LocationManager locationManager;
	private String provider;
	private double dLat;
	private double dLon;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_single_view);
		
		initGps();
		
		service = PatientServiceImpl.getInstance();
		
		selectedPatientItem = SelectedPatient.getPatientItem();
		
		try {
			service.loadPatientByUrl(selectedPatientItem.getUrl());
		} catch (ServiceException e) {
			new AlertDialog.Builder(this) 
		        	.setMessage(R.string.error_load_data)
		        	.setNeutralButton(R.string.ok, null)
		        	.show();
		}
	}
	
	private void initContent() {
		
		salvageText = (TextView)findViewById(R.id.textViewSalvageSingleSalvageText);
		distanceText = (TextView)findViewById(R.id.textViewSalvageDistance);
		urgencyText = (TextView)findViewById(R.id.textViewSalvageUrgency);
		
		if(selectedPatient.getSalvageInfoString() != null || !selectedPatient.getSalvageInfoString().equals(""))
			salvageText.setText(selectedPatient.getSalvageInfoString());
		
		distanceText.setText(getDistanceTo(selectedPatient.getGps()));
		
		urgencyText.setText(String.valueOf(selectedPatient.getUrgency()));
	}
	
	public String getDistanceTo(String gps) {
		
		String[] coordinates = gps.split("; ");
		
		double pLat = Double.valueOf(coordinates[0]);
		double pLon = Double.valueOf(coordinates[1]);
		double C = 111.3;
		
		double lat = (pLat + dLat) / 2;
		
		double dx = C * Math.cos(lat * Math.PI / 180) * (dLon - pLon);
		double dy = C * (dLat - pLat);
		
		double distance = Math.sqrt(dx * dx + dy * dy);
		
		return NumberFormat.getInstance().format(distance * 1000) + " Meter";
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
		
		initContent();
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {

		dLat = location.getLatitude();
		dLon = location.getLongitude();
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
}
