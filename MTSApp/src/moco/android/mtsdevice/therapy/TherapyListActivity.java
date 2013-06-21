package moco.android.mtsdevice.therapy;

import java.util.ArrayList;
import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.listadapter.MTSListTherapyAdapter;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import at.mts.entity.PatientListItem;

public class TherapyListActivity extends Activity {

	private TextView area;	
	private ListView patientView;
	private ListAdapter adapter;
	
	private PatientService service;
	
	private ArrayList<PatientListItem> patientList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_list);
		
		service = PatientServiceImpl.getInstance();
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		
		boolean error = false;
		
		try {
			patientList = service.loadAllPatients();
		} catch (ServiceException e) {
			error = true;
			new AlertDialog.Builder(this) 
			    	.setMessage(R.string.error_load_data)
			    	.setNeutralButton(R.string.ok, null)
			    	.show();
		}
		
		//TODO falls geschwindigkeit es zulaesst: alle daten laden
		
		if(!error) {
			adapter = new MTSListTherapyAdapter<PatientListItem>(getApplicationContext(), R.layout.mts_list, patientList);
			initContent();
		}
	}
	
	private void initContent() {
		
		area = (TextView)findViewById(R.id.textViewTherapyListArea);
		
		area.setText("Behandlungsplatz " + Area.getActiveArea().toString());
		area.setTextColor(Area.getAreaColor());
		
		patientView = (ListView)findViewById(R.id.patientTherapyView); 
		patientView.setAdapter(adapter);
		
		//TODO Listener
	}
	
	public void scanTag(View view) {
		
		Intent intent = new Intent(this, ScanTagActivity.class);
        startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
