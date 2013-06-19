package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.SelectedPatient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import at.mts.entity.*;

public class TriageSalvageinfoActivity extends Activity {
	
	private Button save;
	private TextView salvageText;
	
	private Patient selectedPatient;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_salvageinfo);
        
        selectedPatient = SelectedPatient.getPatient();
        
        save = (Button)findViewById(R.id.save);
        salvageText = (TextView)findViewById(R.id.salvageText);
	}

	public void addSalvageInfo(View v) {
		
		Intent intent = new Intent(this, TriageSalvageinfoAdd.class);
        startActivity(intent);
	}
	
	public void saveSalvageInfo(View v) {
		
		selectedPatient.setTreatment(Treatment.sighted);
		
		Intent intent = new Intent(this, ScanTagActivity.class);
        startActivity(intent);
        finish();
	}
	
	@Override
	public void onRestart() {
		
		super.onRestart();
		
		/**
		 * Textfeld aktualisieren
		 */
		salvageText.setText(selectedPatient.getSalvageInfoString());
		
		/**
		 * Button aendern
		 */
		save.setText(R.string.save);
	}
	
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}