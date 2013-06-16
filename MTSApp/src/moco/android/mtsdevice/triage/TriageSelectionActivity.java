package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TriageSelectionActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.triage_selection);
		
		Mode.setActiveMode(Mode.triage);
	}
	
	public void createPatient(View v) {
		
		Intent intent = new Intent(this, TriageActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void editPatient(View v) {
		
		new AlertDialog.Builder(this) 
    	.setMessage("TODO")
    	.setNeutralButton(R.string.ok, null)
    	.show();
		
		/* TODO
		Intent intent = new Intent(this, ScanTagActivity.class);
		startActivity(intent);
		finish();
		*/
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
