package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.os.Bundle;

public class TherapyMinorActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_minor);
	}
	
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
