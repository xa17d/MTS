package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.SelectedPatient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import at.mts.entity.Patient;

public class TherapySelectionActivity extends Activity {

	private Button btnPersonalData;
	private Button btnVitalParameter;
	private Button btnDiagnosis;
	private Button btnTherapy;
	
	private Patient selectedPatient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_selection);
		
		selectedPatient = SelectedPatient.getPatient();
		
		initButtons();
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
				
		btnPersonalData.setTextColor(selectedPatient.getPersonalDataStatusColor());
		btnVitalParameter.setTextColor(selectedPatient.getVitalParameterStatusColor());
		btnDiagnosis.setTextColor(selectedPatient.getDiagnosisStatusColor());
		btnTherapy.setTextColor(selectedPatient.getTherapyStatusColor());
	}
	
	private void initButtons() {
		
		btnPersonalData = (Button)findViewById(R.id.buttonPersonalData);
		btnVitalParameter = (Button)findViewById(R.id.buttonVitalParameter);
		btnDiagnosis = (Button)findViewById(R.id.buttonDiagnosis);
		btnTherapy = (Button)findViewById(R.id.buttonTherapy);
		
		if(Area.getActiveArea() == Area.IV) {
			btnVitalParameter.setText(R.string.record_death);
			btnDiagnosis.setVisibility(BIND_NOT_FOREGROUND);
			btnTherapy.setVisibility(BIND_NOT_FOREGROUND);
		}
	}
	
	public void gotoPersonalData(View v) {
		
		Intent intent = new Intent(this, TherapyPersonalDataActivity.class);
		startActivity(intent);
	}
	
	public void gotoVitalParameter(View v) {
		
		Intent intent;
		
		if(Area.getActiveArea() == Area.IV)
			intent = new Intent(this, TherapyRecordDeathActivity.class);
		else
			intent = new Intent(this, TherapyVitalParameterActivity.class);
		startActivity(intent);
	}
	
	public void gotoDiagnosis(View v) {
		
		Intent intent = new Intent(this, TherapyDiagnosisActivity.class);
		startActivity(intent);
	}
	
	public void gotoTherapy(View v) {
		
		Intent intent = null;
		
		if(Area.getActiveArea() == Area.I || Area.getActiveArea() == Area.II)
			intent = new Intent(this, TherapyImmediateActivity.class);
		else if(Area.getActiveArea() == Area.III)
			intent = new Intent(this, TherapyMinorActivity.class);
		
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
