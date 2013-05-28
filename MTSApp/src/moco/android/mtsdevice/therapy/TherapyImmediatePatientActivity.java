package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.triage.TriageSelectionActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import at.mts.entity.Patient;

public class TherapyImmediatePatientActivity extends Activity {
	
	private Patient patient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_immediate);
		
		patient = SelectedPatient.getPatient();
		
		TextView txtName = (TextView)findViewById(R.id.immediatePatientName);
		txtName.setText(patient.getNameFamily() + " " + patient.getNameGiven());
	}
	
	public void save(View v) {
		
		new AlertDialog.Builder(this) 
    	.setMessage(R.string.info_saved)
    	.setNeutralButton(R.string.ok, new DialogListener(this))
    	.show();
		
		
	}
	
	private class DialogListener implements android.content.DialogInterface.OnClickListener {

		TherapyImmediatePatientActivity activity;
		
		public DialogListener(TherapyImmediatePatientActivity activity) {
			
			this.activity = activity;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Intent intent = new Intent(activity, TriageSelectionActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
}
