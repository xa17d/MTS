package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.handler.Role;
import moco.android.mtsdevice.salvage.SalvageListActivity;
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
import android.widget.Button;

public class ModeActivity extends Activity {
	
	Button btnTriage;
	Button btnSalvage;
	Button btnTherapy;
	Button btnLogin;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode_selection);
		
		if(Mode.getActiveMode() == null || Mode.getActiveMode() == Mode.undef) {
			Mode.setActiveMode(Mode.loggedout);
			Role.setActiveRole(Role.loggedout);
		}
		
		initComponent();
		
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
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(Mode.getActiveMode() == Mode.loggedout) {
			btnTriage.setEnabled(false);
			btnSalvage.setEnabled(false);
			btnTherapy.setEnabled(false);
		}
		else {
			if(Role.getActiveRole() == Role.MD || Role.getActiveRole() == Role.PARAMEDIC) {
				
				btnTriage.setEnabled(true);
				btnTherapy.setEnabled(true);
			}
			
			btnSalvage.setEnabled(true);
		}
	}
	
	public void triageModeSelected(View v) {
		
		Mode.setActiveMode(Mode.triage);
		Intent intent = new Intent(this, TriageSelectionActivity.class);
		startActivity(intent);
	}
	
	public void salvageModeSelected(View v) {
		
		Mode.setActiveMode(Mode.salvage);
		Intent intent = new Intent(this, SalvageListActivity.class);
		startActivity(intent);
	}
	
	public void therapyModeSelected(View v) {
		
		Mode.setActiveMode(Mode.therapy);
		Intent intent = new Intent(this, TherapyAreaActivity.class);
		startActivity(intent);
	}
	
	public void login(View v) {
		
		if(Mode.getActiveMode() == Mode.loggedout) {
			Intent intent = new Intent(this, ScanTagActivity.class);
			startActivity(intent);
		}
		else {
			btnLogin.setText(R.string.logout);
		}
	}
	
	private void initComponent() {
		
		btnTriage = (Button)findViewById(R.id.buttonTriageMode);
		btnSalvage = (Button)findViewById(R.id.buttonSalvageMode);
		btnTherapy = (Button)findViewById(R.id.buttonTherapyMode);
		btnLogin = (Button)findViewById(R.id.buttonLogin);
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