package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.handler.Therapy;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;

public class TherapyImmediateActivity extends Activity {

	private Patient selectedPatient;
	
	private PatientService service;
	
	private Button btnMedication;
	private Button btnTherapy;
	private Button btnWound;
	private Button btnCpr;
	
	private TextView txtTherapy;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_immediate);
		
		service = PatientServiceImpl.getInstance();
		selectedPatient = SelectedPatient.getPatient();
		
		initControls();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if(!selectedPatient.getCourseOfTreatmentString().equals(""))
			txtTherapy.setText(selectedPatient.getCourseOfTreatmentString());
	}
	
	public void addTherapy(View v) {
		
		if(v == btnMedication)
			Therapy.setSelectedTherapy(Therapy.MEDICATION);
		else if(v == btnTherapy)
			Therapy.setSelectedTherapy(Therapy.THERAPY);
		else if(v == btnWound)
			Therapy.setSelectedTherapy(Therapy.WOUND);
		else if(v == btnCpr)
			Therapy.setSelectedTherapy(Therapy.CPR);
			
		
		Intent intent = new Intent(this, TherapyListAdd.class);
		startActivity(intent);
	}
	
	public void readyForTransport(View v) {
		
		new AlertDialog.Builder(this) 
				.setMessage(R.string.sure_ready_transport)
				.setNegativeButton(R.string.no, null)
		    	.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
		
						selectedPatient.setTreatment(Treatment.transported);
						Intent intent = new Intent(TherapyImmediateActivity.this, TherapyListActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						
						try {
							service.updateExistingPatient(selectedPatient);
							Toast.makeText(TherapyImmediateActivity.this, R.string.info_saved, Toast.LENGTH_LONG).show();
						} catch (ServiceException e) {
							new AlertDialog.Builder(TherapyImmediateActivity.this) 
					        	.setMessage(R.string.error_save_data)
					        	.setNeutralButton(R.string.ok, null)
					        	.show();
						}
						
						startActivity(intent);
					}
				})
		    	.show();
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
	
	private void initControls() {
		
		btnMedication = (Button)findViewById(R.id.buttonMedication);
		btnTherapy = (Button)findViewById(R.id.buttonTherapy);
		btnWound = (Button)findViewById(R.id.buttonWound);
		btnCpr = (Button)findViewById(R.id.buttonCpr);
		
		txtTherapy = (TextView)findViewById(R.id.textViewTherapyListIm);
	}
	
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
