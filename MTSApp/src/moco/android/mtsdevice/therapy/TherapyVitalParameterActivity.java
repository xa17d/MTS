package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
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
	
	private TextView txtDiagnosis;
	
	private Patient selectedPatient;
	private PatientService service;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_vital_parameter);
		
		service = PatientServiceImpl.getInstance();
		selectedPatient = SelectedPatient.getPatient();
		
		TextView txtName = (TextView)findViewById(R.id.vitalPatientName);
		txtName.setText(selectedPatient.getNameFamily() + " " + selectedPatient.getNameGiven());
		
		initControls();
		loadData();
	}
	
	public void onResume() {
		super.onResume();
		
		if(!selectedPatient.getDiagnosisString().equals(""));
			txtDiagnosis.setText(selectedPatient.getDiagnosisString());
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
				selectedPatient.setBloodPressureSystolic(Integer.valueOf(textSystolic.getText().toString()));
			
			if(textDiastolic.getText() != null)
				selectedPatient.setBloodPressureDiastolic(Integer.valueOf(textDiastolic.getText().toString()));
			
			if(textPulse.getText() != null)
				selectedPatient.setPulse(Integer.valueOf(textPulse.getText().toString()));
				
		} catch(NumberFormatException ex) {
			
			//impossible
		}
		
		try {
			service.updateExistingPatient(selectedPatient);
			Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
		} catch (ServiceException e) {
			new AlertDialog.Builder(this) 
	        	.setMessage(R.string.error_save_data)
	        	.setNeutralButton(R.string.ok, null)
	        	.show();
		}
		
		finish();		
	}
	
	private void initControls() {
		
		textSystolic = (EditText)findViewById(R.id.bloodPressureSystolic);
		textDiastolic = (EditText)findViewById(R.id.bloodPressureDiastolic);
		textOxygen = (EditText)findViewById(R.id.bloodOxygen);
		textPulse = (EditText)findViewById(R.id.bloodPulse);
		
		txtDiagnosis = (TextView)findViewById(R.id.textViewVitalDiagnosis);
	}
	
	private void loadData() {
		
		if(selectedPatient.getBloodPressureSystolic() != null)
			textSystolic.setText(String.valueOf(selectedPatient.getBloodPressureSystolic()));
		
		if(selectedPatient.getBloodPressureDiastolic() != null)
			textDiastolic.setText(String.valueOf(selectedPatient.getBloodPressureDiastolic()));
		
		if(selectedPatient.getPulse() != null)
			textPulse.setText(String.valueOf(selectedPatient.getPulse()));
	}
	
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
