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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import at.mts.entity.Patient;

public class TherapyDiagnosisActivity extends Activity {

	private PatientService service;
	private Patient selectedPatient;
	
	EditText txtDiagnosisAdd;
	
	RadioGroup radioUrgency;
	RadioButton r1;
	RadioButton r2;
	RadioButton r3;
	RadioButton r4;
	RadioButton r5;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_diagnosis);
		
		service = PatientServiceImpl.getInstance();
		selectedPatient = SelectedPatient.getPatient();
		
		txtDiagnosisAdd = (EditText)findViewById(R.id.editTextDiagnosisAdd);
		radioUrgency = (RadioGroup)findViewById(R.id.radioGroup1);
		
		r1 = (RadioButton)findViewById(R.id.radio1);
		r2 = (RadioButton)findViewById(R.id.radio2);
		r3 = (RadioButton)findViewById(R.id.radio3);
		r4 = (RadioButton)findViewById(R.id.radio4);
		r5 = (RadioButton)findViewById(R.id.radio5);

		if(selectedPatient.getUrgency() == 1)
			r1.setSelected(true);
		else if(selectedPatient.getUrgency() == 2)
			r2.setSelected(true);
		else if(selectedPatient.getUrgency() == 3)
			r3.setSelected(true);
		else if(selectedPatient.getUrgency() == 4)
			r4.setSelected(true);
		else
			r5.setSelected(true);
			
	}
	
	
	public void addDiagnosis(View v) {
		
		if(!txtDiagnosisAdd.getText().equals(""))
			selectedPatient.addDiagnosis(String.valueOf(txtDiagnosisAdd.getText()));
		
		RadioButton checkedRadioButton = (RadioButton)radioUrgency.findViewById(radioUrgency.getCheckedRadioButtonId());
		
		selectedPatient.setUrgency(Integer.getInteger(String.valueOf(checkedRadioButton.getText().charAt(0))));
	}
	
	public void save(View v) {
		
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
