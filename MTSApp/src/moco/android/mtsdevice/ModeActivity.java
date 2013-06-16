package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.maps.SalvageMap;
import moco.android.mtsdevice.therapy.TherapyAreaActivity;
import moco.android.mtsdevice.triage.TriageSelectionActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

public class ModeActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode_selection);
		
		Mode.setActiveMode(Mode.undef);
		
		if(!checkGps()) {
			
			new AlertDialog.Builder(this) 
					.setMessage(R.string.activate_gps)
			    	.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {

							Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(intent);
						}
					})
			    	.show();
		}
	}
	
	public void triageModeSelected(View v) {
		
		Mode.setActiveMode(Mode.triage);
		Intent intent = new Intent(this, TriageSelectionActivity.class);
		startActivity(intent);
	}
	
	public void salvageModeSelected(View v) {
		
		Mode.setActiveMode(Mode.salvage);
		Intent intent = new Intent(this, SalvageMap.class);
		startActivity(intent);
	}
	
	public void therapyModeSelected(View v) {
		
		Mode.setActiveMode(Mode.therapy);
		Intent intent = new Intent(this, TherapyAreaActivity.class);
		startActivity(intent);
	}
	
	private boolean checkGps() {
		
		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		return service.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	@Override
	public void onBackPressed() {

		DeviceButtons.onBackPressed(this);
	}
}