package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TriageSelectionActivity extends Activity implements OnClickListener {

	private Button btnCreate;
	private Button btnEdit;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.triage_selection);
		
		Mode.setActiveMode(Mode.triage);
		
		initButtons();
	}
	
	@Override
	public void onClick(View v) {

		Intent intent = null;
		
		if(v == btnCreate)
			intent = new Intent(this, TriageActivity.class);
		
		if(v == btnEdit)	//TODO
			//intent = new Intent(this, ScanTagActivity.class);
			new AlertDialog.Builder(this) 
			    	.setMessage("TODO")
			    	.setNeutralButton(R.string.ok, null)
			    	.show();
		
		if(intent != null) {
			startActivity(intent);
			finish();
		}
	}
	
	private void initButtons() {
		
		btnCreate = (Button)findViewById(R.id.buttonCreatePatient);
		btnEdit = (Button)findViewById(R.id.buttonEditPatient);
		
		btnCreate.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		
		return DeviceButtons.getToModeSelection(this);
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.onBackPressed(this);
	}
}
