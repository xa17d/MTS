package moco.android.mtsdevice.salvage;

import java.util.ArrayList;
import java.util.UUID;

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
import at.mts.entity.Patient;
import at.mts.entity.PatientListItem;
import at.mts.entity.TriageCategory;

public class SalvageListActivity extends Activity implements OnItemClickListener {

	private PatientService service;
	
	private ListView patientView;
	private ListAdapter adapter;
	
	private ArrayList<PatientListItem> patientList;
	private ArrayList<Patient> patientListTemp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_list);
		
//		service = PatientServiceImpl.getInstance();
//		
//		try {
//			patientList = service.loadAllPatients();
//		} catch (ServiceException e) {
//			new AlertDialog.Builder(this) 
//		        	.setMessage(R.string.error_load_data)
//		        	.setNeutralButton(R.string.ok, null)
//		        	.show();
//		}
//		
//		adapter = new MTSListAdapter<PatientListItem>(getApplicationContext(), R.layout.mts_list, patientList);
//		
//		initContent();
		
		
		//TODO delete
		createSomePatients();
		
		adapter = new MTSListSalvageAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListTemp);
		
		initContent();
	}
	
	public void scanTag(View view) {
		
		Intent intent = new Intent(this, ScanTagActivity.class);
        startActivity(intent);
        finish();
	}
	
	private void initContent() {
		
		patientView = (ListView)findViewById(R.id.patientSalvageView); 
		patientView.setAdapter(adapter);
		patientView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		SelectedPatient.setPatient((Patient)patientView.getAdapter().getItem(arg2));
		
		Intent intent = new Intent(this, SalvageSingleViewActivity.class);
		startActivity(intent);
	}

	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
	
	
	//TODO DELETE
	private void createSomePatients() {
		Patient p;
		patientListTemp = new ArrayList<Patient>();
		
		/**
		 * 7 IMMEDIATE
		 */
		for(int i = 0; i < 7; i++) {
			p = new Patient();
			p.setCategory(TriageCategory.immediate);
			p.setId(new UUID(4,i));
			p.setPlacePosition(String.valueOf((i * (2 * i * i - 2 * (i + 3)) + 7)));
			
			if(i == 5) {
				p.setNameGiven("Fritz");
				p.setNameFamily("Fantom");
				p.setUrgency(4);
				p.setSalvageInfo("Feuerwehr;\nSchaufeltrage;\nVakuummatratze;");
			}
			if(i == 6) {
				p.setNameGiven("Hans");
				p.setNameFamily("Wurst");
				p.setUrgency(2);
			}
			
			patientListTemp.add(p);
		}
		
		/**
		 * 0 DELAYED
		 */
		p = new Patient();
		p.setCategory(TriageCategory.delayed);
		p.setId(new UUID(4,7));
		p.setNameGiven("Max");
		p.setNameFamily("Mustermann");
		patientListTemp.add(p);
		
		/**
		 * 2 MINOR
		 */		
		p = new Patient();
		p.setCategory(TriageCategory.minor);
		p.setId(new UUID(4,7));
		p.setNameGiven("Max");
		p.setNameFamily("Mustermann");
		patientListTemp.add(p);
		
		p = new Patient();
		p.setCategory(TriageCategory.minor);
		p.setId(new UUID(4,8));
		p.setNameGiven("Lucas");
		p.setNameFamily("Dobler");
		patientListTemp.add(p);
		
		/**
		 * 1 DECEASED
		 */
		p = new Patient();
		p.setCategory(TriageCategory.deceased);
		p.setId(new UUID(4,15));
		p.setPlacePosition("1");
		patientListTemp.add(p);
	}
}
