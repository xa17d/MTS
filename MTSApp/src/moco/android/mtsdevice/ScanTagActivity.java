package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.salvage.SalvageActivity;
import moco.android.mtsdevice.therapy.TherapyImmediatePatientActivity;
import moco.android.mtsdevice.triage.TriageSelectionActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import at.mts.entity.Patient;

public class ScanTagActivity extends Activity implements OnClickListener {

	private Button btnTagScanned;
	
	private Patient selectedPatient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_tag);
		
		initButtons();
	}
	
	@Override
	public void onClick(View v) {

		Intent intent = null;
		
		//TODO NFC-Tag lesen
		selectedPatient = new Patient();

		if(Mode.getActiveMode() == Mode.triage) {
			
			new AlertDialog.Builder(this) 
            	.setMessage(R.string.info_saved)
            	.setNeutralButton(R.string.ok, new DialogListener(this))
            	.show();
		}
		
		if(Mode.getActiveMode() == Mode.salvage) {
			
			intent = new Intent(this, SalvageActivity.class);
			SelectedPatient.setPatient(selectedPatient);
			
			startActivity(intent);
		}
		
		if(Mode.getActiveMode() == Mode.therapy) {
			
			//TODO Kategorie unterscheiden (minor und deceased zuerst stammdaten / immediate und delayed zuerst med Infos)
			intent = new Intent(this, TherapyImmediatePatientActivity.class);
			SelectedPatient.setPatient(selectedPatient);
			
			startActivity(intent);
		}
	}
	
	private class DialogListener implements android.content.DialogInterface.OnClickListener {

		ScanTagActivity activity;
		
		public DialogListener(ScanTagActivity activity) {
			
			this.activity = activity;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Intent intent = new Intent(activity, TriageSelectionActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
	
	private void initButtons() {

		btnTagScanned = (Button)findViewById(R.id.buttonTagScanned);

		btnTagScanned.setOnClickListener(this);
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
