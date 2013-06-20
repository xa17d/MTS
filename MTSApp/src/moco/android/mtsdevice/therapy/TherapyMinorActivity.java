package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;

public class TherapyMinorActivity extends Activity {

	private PatientService service;
	private Patient selectedPatient;
	
	private Button btnHospital;
	private Button btnDoctor;
	private Button btnPsychologist;
	private Button btnLetGo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_minor);
		
		service = PatientServiceImpl.getInstance();
		selectedPatient = SelectedPatient.getPatient();
		
		btnHospital = (Button)findViewById(R.id.buttonHospital);
		btnDoctor = (Button)findViewById(R.id.buttonDoctor);
		btnPsychologist = (Button)findViewById(R.id.buttonPsychologist);
		btnLetGo = (Button)findViewById(R.id.buttonLetGo);
	}
	
	public void setTherapy(View v) {
		
		if(v == btnHospital) {
			selectedPatient.setCourseOfTreatment("wurde ins Krankenhaus eingewiesen");
			selectedPatient.setReadyForTransport(true);
		}
		else if(v == btnDoctor)
			selectedPatient.setCourseOfTreatment("muss orgen einen Arzt aufsuchen");
		else if(v == btnPsychologist)
			selectedPatient.setCourseOfTreatment("sollte psychologisch betreut werden");
		else if(v == btnLetGo)
			selectedPatient.setCourseOfTreatment("wurde entlassen");
			
		selectedPatient.setTreatment(Treatment.transported);
		
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
	
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
