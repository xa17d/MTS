package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TriageSalvageinfoActivity extends Activity implements OnClickListener {
	
	private Button add;
	private Button save;
	private TextView salvageText;
	
	private Bundle savedInstanceState;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_salvageinfo);
        
        this.savedInstanceState = savedInstanceState;
        
        add = (Button)findViewById(R.id.addButton);
        save = (Button)findViewById(R.id.save);
        salvageText = (TextView)findViewById(R.id.salvageText);
        
        Intent intent = getIntent();
        
        if(intent.getStringExtra("newItem") != null) {
        	if(salvageText.equals(R.string.no_info))
        		salvageText.setText(intent.getStringExtra("newItem") + ";");
        	salvageText.setText(salvageText.getText() + "\n" + intent.getStringExtra("newItem") + ";");
        }
                
        save.setOnClickListener(this);
        add.setOnClickListener(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		
		/**
		 * Hinzufuegen
		 */
		if(v == add) {
			Intent myIntent = new Intent(this, TriageSalvageinfoAdd.class);
            startActivityForResult(myIntent, 0, savedInstanceState);
		}
		/**
		 * Speichern
		 */
		if(v == save) {
			//TODO Patient aktualisieren
			
			/**
			 * naechste Seite
			 */
			Intent intent = new Intent(this, TriageActivity.class);
            startActivity(intent);
		}
	}
	
	@Override
	public void onBackPressed() {
		//DO NOTHING
	}
}