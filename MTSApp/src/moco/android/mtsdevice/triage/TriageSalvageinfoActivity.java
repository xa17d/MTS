package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.handler.DeviceButtons;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import at.mts.entity.*;

public class TriageSalvageinfoActivity extends Activity implements OnClickListener {
	
	private Button add;
	private Button save;
	private TextView salvageText;
	
	private Patient patient;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_salvageinfo);
        
        patient = Patient.getSelectedPatient();
        
        add = (Button)findViewById(R.id.addButton);
        save = (Button)findViewById(R.id.save);
        salvageText = (TextView)findViewById(R.id.salvageText);
             
        save.setOnClickListener(this);
        add.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		
		/**
		 * Hinzufuegen
		 */
		if(v == add) {
			Intent intent = new Intent(this, TriageSalvageinfoAdd.class);
            startActivity(intent);
		}
		
		/**
		 * Speichern
		 */
		if(v == save) {

			/**
			 * zu Tag einscannen
			 */
			Intent intent = new Intent(this, ScanTagActivity.class);
            startActivity(intent);
			
            finish();
		}
	}
	
	@Override
	public void onRestart() {
		
		super.onRestart();
		
		/**
		 * Textfeld aktualisieren
		 */
		salvageText.setText(patient.getSalvageInfoString());
		
		/**
		 * Button aendern
		 */
		save.setText(R.string.save);
	}
	
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		
		DeviceButtons.getToModeSelection(this);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.onBackPressed(this);
	}
}