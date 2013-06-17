package moco.android.mtsdevice.salvage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.handler.SelectedPatient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import at.mts.entity.Patient;

public class SalvageSingleViewActivity extends Activity {

	private Patient selectedPatient;
	
	private TextView salvageText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_single_view);
		
		selectedPatient = SelectedPatient.getPatient();
		
		salvageText = (TextView)findViewById(R.id.textViewSalvageSingleSalvageText);
		
		if(selectedPatient.getSalvageInfoString() != null || !selectedPatient.getSalvageInfoString().equals(""))
			salvageText.setText(selectedPatient.getSalvageInfoString());		
	}
	
	
	public void scanTag(View view) {
		
		Intent intent = new Intent(this, ScanTagActivity.class);
        startActivity(intent);
        finish();
	}
}
