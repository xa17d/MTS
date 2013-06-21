package moco.android.mtsdevice.salvage;

import java.util.ArrayList;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.handler.listadapter.MTSListSalvageAdapter;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import at.mts.entity.PatientListItem;

public class SalvageListActivity extends Activity implements OnItemClickListener {

	private PatientService service;
	
	private ListView patientView;
	private ListAdapter adapter;
	
	private ArrayList<PatientListItem> patientList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_list);
		
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
		
		if(!error) {
			adapter = new MTSListSalvageAdapter<PatientListItem>(getApplicationContext(), R.layout.mts_list, patientList);
			initContent();
		}
	}
	
	public void scanTag(View view) {
		
		Intent intent = new Intent(this, ScanTagActivity.class);
        startActivity(intent);
	}
	
	private void initContent() {
		
		patientView = (ListView)findViewById(R.id.patientSalvageView); 
		patientView.setAdapter(adapter);
		patientView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		SelectedPatient.setPatientItem((PatientListItem)patientView.getAdapter().getItem(arg2));
		
		Intent intent = new Intent(this, SalvageSingleViewActivity.class);
		startActivity(intent);
	}

	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
