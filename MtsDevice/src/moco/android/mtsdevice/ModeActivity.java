package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.Mode;
import android.app.Activity;
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

		if(v == btnTriageMode)
			Mode.setActiveMode(Mode.triage);
		
		if(v == btnSalvageMode)
			Mode.setActiveMode(Mode.salvage);
		
		if(v == btnTherapyMode)
			Mode.setActiveMode(Mode.therapy);
		
		Intent intent = new Intent(this, ScanTagActivity.class);
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
		//DO NOTHING
	}
}