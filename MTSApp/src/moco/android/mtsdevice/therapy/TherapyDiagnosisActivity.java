package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class TherapyDiagnosisActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_diagnosis);
	}
	
	
	public void addDiagnosis(View v) {
		
		//TODO
	}
	
	public void save(View v) {
		
		//TODO
		
		Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
		finish();
	}
}
