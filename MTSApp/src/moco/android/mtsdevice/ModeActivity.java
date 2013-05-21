package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.maps.SalvageMap;
import moco.android.mtsdevice.therapy.TherapyAreaActivity;
import moco.android.mtsdevice.triage.TriageSelectionActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ModeActivity extends Activity implements OnClickListener {

	private Button btnTriageMode;
	private Button btnSalvageMode;
	private Button btnTherapyMode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mode_selection);
		
		Mode.setActiveMode(Mode.undef);
		
		initButtons();
	}
	
	@Override
	public void onClick(View v) {

		Intent intent = null;
		
		if(v == btnTriageMode) {
			Mode.setActiveMode(Mode.triage);
			intent = new Intent(this, TriageSelectionActivity.class);
		}
		
		if(v == btnSalvageMode) {
			//Mode.setActiveMode(Mode.salvage);
			//intent = new Intent(this, SalvageMap.class);
			new AlertDialog.Builder(this) 
			    	.setMessage("TODO")
			    	.setNeutralButton(R.string.ok, null)
			    	.show();
		}
		
		if(v == btnTherapyMode) {
			Mode.setActiveMode(Mode.therapy);
			intent = new Intent(this, TherapyAreaActivity.class);
		}
		
		if(intent != null)
			startActivity(intent);
	}
	
	private void initButtons() {
		
		btnTriageMode = (Button)findViewById(R.id.buttonTriageMode);
		btnSalvageMode = (Button)findViewById(R.id.buttonSalvageMode);
		btnTherapyMode = (Button)findViewById(R.id.buttonTherapyMode);
		
		btnTriageMode.setOnClickListener(this);
		btnSalvageMode.setOnClickListener(this);
		btnTherapyMode.setOnClickListener(this);
	}
	
	@Override
	public void onBackPressed() {

		DeviceButtons.onBackPressed(this);
	}
}