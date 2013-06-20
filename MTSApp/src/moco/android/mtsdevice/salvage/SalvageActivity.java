package moco.android.mtsdevice.salvage;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import at.mts.entity.Patient;
import at.mts.entity.Treatment;

public class SalvageActivity extends Activity {

	private PatientService service;
	private Patient selectedPatient;
	
	private TextView txtInfo;
	private EditText txtPlace;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_main);
		
		service = PatientServiceImpl.getInstance();
		selectedPatient = SelectedPatient.getPatient();
		
		initContent();
	}
	
	public void putPatient(View v) {
		
		selectedPatient.setTreatment(Treatment.salvaged);
		selectedPatient.setPlacePosition(String.valueOf(txtPlace.getText()));
		
		int code = 0;
		
		try {
			code = service.updateExistingPatient(selectedPatient);
			Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
		} catch (ServiceException e) {
			new AlertDialog.Builder(this) 
	        	.setMessage(R.string.error_save_data)
	        	.setNeutralButton(R.string.ok, null)
	        	.show();
		}
		
		finish();
	}
	
	private void initContent() {
		
		txtInfo = (TextView)findViewById(R.id.textViewSalvageMainSalvageText);
		txtInfo.setText("Zielort:\nBehandlungsplatz " + Area.getAreaToCategory(selectedPatient.getCategory()) + "\nPatient abgegeben auf Platz Nummer: ");
		
		txtPlace = (EditText)findViewById(R.id.editTextPlacePositionNr);
	}
	
	
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
