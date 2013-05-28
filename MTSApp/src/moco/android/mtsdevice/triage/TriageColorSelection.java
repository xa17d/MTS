package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import at.mts.entity.Patient;
import at.mts.entity.TriageCategory;

public class TriageColorSelection extends Activity {
	
	private Button black;
	private Button red;
	private Button yellow;
	private Button green;
	
	private Patient patient;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_main_setcolor);
        
        patient = Patient.getSelectedPatient();
		
		black = (Button)findViewById(R.id.btnBlack);
        red = (Button)findViewById(R.id.btnRed);
        yellow = (Button)findViewById(R.id.btnYellow);
        green = (Button)findViewById(R.id.btnGreen);
        
        black.setBackgroundColor(TriageCategory.deceased.getTriageColor());
        red.setBackgroundColor(TriageCategory.immediate.getTriageColor());
        yellow.setBackgroundColor(TriageCategory.delayed.getTriageColor());
        green.setBackgroundColor(TriageCategory.minor.getTriageColor());
	}
	
	public void blackButtonClick(View view) {
		patient.setCategory(TriageCategory.deceased);
		finish();
	}
	
	public void redButtonClick(View view) {
		patient.setCategory(TriageCategory.immediate);
		finish();
	}
	
	public void yellowButtonClick(View view) {
		patient.setCategory(TriageCategory.delayed);
		finish();
	}
	
	public void greenButtonClick(View view) {
		patient.setCategory(TriageCategory.minor);
		finish();
	}
}
