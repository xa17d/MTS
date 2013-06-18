package moco.android.mtsdevice.therapy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.TestActivity;
import moco.android.mtsdevice.communication.CommunicationException;
import moco.android.mtsdevice.communication.ServerCommunicationImpl;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.listadapter.MTSListAdapter;
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
import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.PatientListItem;
import at.mts.entity.Treatment;
import at.mts.entity.TriageCategory;

public class TherapyListActivity extends Activity {

	private TextView area;	
	private ListView patientView;
	private ListAdapter adapter;
	
	private PatientService service;
	
	//TODO
	private ArrayList<Patient> patientListIm;
	private ArrayList<Patient> patientListDel;
	private ArrayList<Patient> patientListMin;
	private ArrayList<Patient> patientListDec;
	
	private ArrayList<PatientListItem> patientList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_list);
		
//		service = PatientServiceImpl.getInstance();
//		
//		try {
//			patientList = service.loadAllPatients();
//		} catch (ServiceException e) {
//			new AlertDialog.Builder(this) 
//			    	.setMessage(R.string.error_load_data)
//			    	.setNeutralButton(R.string.ok, null)
//			    	.show();
//		}
//		
//		PatientListItem patientItem;
//		Iterator<PatientListItem> it = patientList.iterator();
//		
//		while(it.hasNext()) {
//			patientItem = it.next();
//			
//			/**
//			 * wenn gewaehlter Patient noch nicht geborgen (oder bereits abtransportiert) wurde
//			 * ODER
//			 * Patient an anderem Behandlungsplatz
//			 * ==> aus Liste entfernen
//			 */
//			if(patientItem.getTreatment() != Treatment.salvaged || !Area.getActiveArea().matchesCategory(patientItem.getCategory()))
//				patientList.remove(patientItem);
//		}
//		
//		//TODO falls geschwindigkeit es zulaesst: alle daten laden
//		adapter = new MTSListAdapter<PatientListItem>(getApplicationContext(), R.layout.mts_list, patientList);
//
//		initContent();
		
		
		//TODO REMOVE
		createSomePatients();

		if(Area.getActiveArea() == Area.I) {
			adapter = new MTSListTherapyAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListIm);
		}
		
		if(Area.getActiveArea() == Area.II)
			adapter = new MTSListTherapyAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListDel);
		
		if(Area.getActiveArea() == Area.III)
			adapter = new MTSListTherapyAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListMin);
		
		if(Area.getActiveArea() == Area.IV)
			adapter = new MTSListTherapyAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListDec);
		
		initContent();
	}
	
	private void initContent() {
		
		area = (TextView)findViewById(R.id.textViewTherapyListArea);
		
		area.setText("Behandlungsplatz " + Area.getActiveArea().toString());
		area.setTextColor(Area.getAreaColor());
		
		patientView = (ListView)findViewById(R.id.patientTherapyView); 
		patientView.setAdapter(adapter);
		
		//TODO Listener
	}
	
	private void createSomePatients() {
		Patient p;
		
		/**
		 * 7 IMMEDIATE
		 */
		patientListIm = new ArrayList<Patient>();
		
		for(int i = 0; i < 7; i++) {
			p = new Patient();
			p.setCategory(TriageCategory.immediate);
			p.setId(new UUID(4,i));
			p.setPlacePosition(String.valueOf((i * (2 * i * i - 2 * (i + 3)) + 7)));
			
			if(i == 5) {
				p.setNameGiven("Fritz");
				p.setNameFamily("Fantom");
				p.setUrgency(4);
			}
			if(i == 6) {
				p.setNameGiven("Hans");
				p.setNameFamily("Wurst");
				p.setUrgency(2);
			}
			
			patientListIm.add(p);
		}
		
		/**
		 * 0 DELAYED
		 */
		patientListDel = new ArrayList<Patient>();
		
		/**
		 * 2 MINOR
		 */
		patientListMin = new ArrayList<Patient>();
		
		p = new Patient();
		p.setCategory(TriageCategory.minor);
		p.setId(new UUID(4,7));
		p.setNameGiven("Max");
		p.setNameFamily("Mustermann");
		patientListMin.add(p);
		
		p = new Patient();
		p.setCategory(TriageCategory.minor);
		p.setId(new UUID(4,8));
		p.setNameGiven("Lucas");
		p.setNameFamily("Dobler");
		patientListMin.add(p);
		
		/**
		 * 1 DECEASED
		 */
		patientListDec = new ArrayList<Patient>();
		
		p = new Patient();
		p.setCategory(TriageCategory.deceased);
		p.setId(new UUID(4,15));
		p.setPlacePosition("1");
		patientListDec.add(p);
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
