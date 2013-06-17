package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import at.mts.entity.Patient;

public class TherapyVitalParameterActivity extends Activity {
	
	private EditText textSystolic;
	private EditText textDiastolic;
	private EditText textOxygen;
	private EditText textPulse;
	
	private Patient patient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_vital_parameter);
		
		patient = SelectedPatient.getPatient();
		
		TextView txtName = (TextView)findViewById(R.id.vitalPatientName);
		txtName.setText(patient.getNameFamily() + " " + patient.getNameGiven());
		
		initControls();
		loadData();
	}
	
	public void openFrontBody(View v) {
		
		Intent intent = new Intent(this, BodyViewFrontActivity.class);
		startActivity(intent);
	}
	
	public void openBackBody(View v) {
		
		Intent intent = new Intent(this, BodyViewBackActivity.class);
		startActivity(intent);
	}
	
	public void save(View v) {
				
		try {
			
			if(textSystolic.getText() != null)
				patient.setBloodPressureSystolic(Integer.valueOf(textSystolic.getText().toString()));
			
			if(textDiastolic.getText() != null)
				patient.setBloodPressureDiastolic(Integer.valueOf(textDiastolic.getText().toString()));
			
			if(textPulse.getText() != null)
				patient.setPulse(Integer.valueOf(textPulse.getText().toString()));
			
			//TODO Oxygen
			
		} catch(NumberFormatException ex) {
			
			new AlertDialog.Builder(this)
		    		.setMessage(R.string.error)
		    		.setNeutralButton(R.string.ok, null)
		    		.show();
		}
		
		Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
		finish();		
	}
	
	private void initControls() {
		
		textSystolic = (EditText)findViewById(R.id.bloodPressureSystolic);
		textDiastolic = (EditText)findViewById(R.id.bloodPressureDiastolic);
		textOxygen = (EditText)findViewById(R.id.bloodOxygen);
		textPulse = (EditText)findViewById(R.id.bloodPulse);
	}
	
	private void loadData() {
		
		if(patient.getBloodPressureSystolic() != null)
			textSystolic.setText(String.valueOf(patient.getBloodPressureSystolic()));
		
		if(patient.getBloodPressureDiastolic() != null)
			textDiastolic.setText(String.valueOf(patient.getBloodPressureDiastolic()));
		
		if(patient.getPulse() != null)
			textPulse.setText(String.valueOf(patient.getPulse()));
		
		//TODO Oxygen
	}
}
