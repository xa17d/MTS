package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.handler.Therapy;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;

public class TherapyImmediateActivity extends Activity {

	private Patient selectedPatient;
	
	private Button btnMedication;
	private Button btnTherapy;
	private Button btnWound;
	private Button btnCpr;
	private Button btnReadyTransport;
	
	private TextView txtTherapy;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_immediate);
		
		initContent();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		selectedPatient = SelectedPatient.getPatient();	
		if(selectedPatient.getCourseOfTreatment() != null)
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
						finish();
					}
				})
		    	.show();
	}
	
	public void save(View v) {
		
		//TODO
		finish();
	}
	
	private void initContent() {
		
		btnMedication = (Button)findViewById(R.id.buttonMedication);
		btnTherapy = (Button)findViewById(R.id.buttonTherapy);
		btnWound = (Button)findViewById(R.id.buttonWound);
		btnCpr = (Button)findViewById(R.id.buttonCpr);
		btnReadyTransport = (Button)findViewById(R.id.buttonReadyTransport);
		
		txtTherapy = (TextView)findViewById(R.id.textViewTherapyListIm);
	}
	
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
