package moco.android.mtsdevice.salvage;

import java.util.ArrayList;
import java.util.UUID;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.MTSListAdapter;
import moco.android.mtsdevice.handler.SelectedPatient;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import at.mts.entity.Patient;
import at.mts.entity.TriageCategory;

public class SalvageListActivity extends Activity implements OnItemClickListener {

	private ListView patientView;
	private ListAdapter adapter;
	
	private ArrayList<Patient> patientList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_list);
		
		createSomePatients();
		
		adapter = new MTSListAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientList);
		
		initContent();
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		SelectedPatient.setPatient((Patient)patientView.getAdapter().getItem(arg2));
		
		Intent intent = new Intent(this, SalvageSingleViewActivity.class);
		startActivity(intent);
	}
	
	private void initContent() {
		
		patientView = (ListView)findViewById(R.id.patientSalvageView); 
		patientView.setAdapter(adapter);
		patientView.setOnItemClickListener(this);
	}

	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
	
	
	//TODO
	private void createSomePatients() {
		Patient p;
		patientList = new ArrayList<Patient>();
		
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
			
			patientList.add(p);
		}
		
		/**
		 * 0 DELAYED
		 */
		p = new Patient();
		p.setCategory(TriageCategory.delayed);
		p.setId(new UUID(4,7));
		p.setNameGiven("Max");
		p.setNameFamily("Mustermann");
		patientList.add(p);
		
		/**
		 * 2 MINOR
		 */		
		p = new Patient();
		p.setCategory(TriageCategory.minor);
		p.setId(new UUID(4,7));
		p.setNameGiven("Max");
		p.setNameFamily("Mustermann");
		patientList.add(p);
		
		p = new Patient();
		p.setCategory(TriageCategory.minor);
		p.setId(new UUID(4,8));
		p.setNameGiven("Lucas");
		p.setNameFamily("Dobler");
		patientList.add(p);
		
		/**
		 * 1 DECEASED
		 */
		p = new Patient();
		p.setCategory(TriageCategory.deceased);
		p.setId(new UUID(4,15));
		p.setPlacePosition("1");
		patientList.add(p);
	}
}
