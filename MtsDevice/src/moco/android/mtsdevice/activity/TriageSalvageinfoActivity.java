package moco.android.mtsdevice.activity;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TriageSalvageinfoActivity extends Activity implements OnClickListener {
	
	private Button add;
	private Button save;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_salvageinfo);
        
        add = (Button)findViewById(R.id.addButton);
        save = (Button)findViewById(R.id.save);
                
        save.setOnClickListener(this);
        add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		/**
		 * Hinzufuegen
		 */
		if(v == add) {
			Intent myIntent = new Intent(v.getContext(), TriageSalvageinfoAdd.class);
            startActivityForResult(myIntent, 0);
		}
		/**
		 * Speichern
		 */
		if(v == save) {
			//TODO Patient aktualisieren
			
			/**
			 * naechste Seite
			 */
			Intent myIntent = new Intent(v.getContext(), TriageActivity.class);
            startActivityForResult(myIntent, 0);
		}
	}
}