package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.salvage.SalvageActivity;
import moco.android.mtsdevice.therapy.TherapyActivity;
import moco.android.mtsdevice.triage.TriageActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScanTagActivity extends Activity implements OnClickListener {

	private Button btnChangeMode;
	private Button btnTagScanned;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_tag);
		
		initButtons();
	}
	
	@Override
	public void onClick(View v) {

		Intent intent = null;
		
		if(v == btnChangeMode)
			finish();
		
		//TODO NFC-Tag lesen
		if(v == btnTagScanned) {
			if(Mode.getActiveMode() == Mode.triage)
				intent = new Intent(this, TriageActivity.class);
			if(Mode.getActiveMode() == Mode.salvage)
				intent = new Intent(this, SalvageActivity.class);
			if(Mode.getActiveMode() == Mode.therapy)
				intent = new Intent(this, TherapyActivity.class);
		}
		
		if(intent != null)
			startActivity(intent);
	}
	
	private void initButtons() {
		
		btnChangeMode = (Button)findViewById(R.id.buttonChangeModeInScan);
		btnTagScanned = (Button)findViewById(R.id.buttonTagScanned);
		
		btnChangeMode.setOnClickListener(this);
		btnTagScanned.setOnClickListener(this);
	}
	
	/*
	@Override
	public void onBackPressed() {
		//DO NOTHING
	}
	*/
}
