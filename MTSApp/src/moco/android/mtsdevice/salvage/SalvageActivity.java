package moco.android.mtsdevice.salvage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.DeviceButtons;
import android.app.Activity;
import android.os.Bundle;

public class SalvageActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_main);
		
	}
	
	
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
