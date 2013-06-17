package moco.android.mtsdevice.therapy;

import java.util.ArrayList;
import java.util.UUID;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.ScanTagActivity;
import moco.android.mtsdevice.TestActivity;
import moco.android.mtsdevice.communication.CommunicationException;
import moco.android.mtsdevice.communication.ServerCommunication;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.MTSListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import at.mts.entity.Patient;
import at.mts.entity.PatientList;
import at.mts.entity.TriageCategory;

public class TherapyListActivity extends Activity {

	private TextView area;	
	private ListView patientView;
	private ListAdapter adapter;
	
	//TODO
	private ArrayList<Patient> patientListIm;
	private ArrayList<Patient> patientListDel;
	private ArrayList<Patient> patientListMin;
	private ArrayList<Patient> patientListDec;
	
	private PatientList patientList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_list);
		
		ServerCommunication com = new ServerCommunication();
		String s = "";
		try {
			s = com.loadAllPatients();
		} catch (CommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TestActivity.setTestText(s);
		
		//TEST
		Intent intent = new Intent(this, TestActivity.class);
		startActivity(intent);
		finish();

		
		//createSomePatients();
		/*
		if(Area.getActiveArea() == Area.I) {
			//TODO
			//patientList = 
			adapter = new MTSListAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListIm);
		}
		
		if(Area.getActiveArea() == Area.II)
			adapter = new MTSListAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListDel);
		
		if(Area.getActiveArea() == Area.III)
			adapter = new MTSListAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListMin);
		
		if(Area.getActiveArea() == Area.IV)
			adapter = new MTSListAdapter<Patient>(getApplicationContext(), R.layout.mts_list, patientListDec);
		
		initContent();
		*/
	}
	
	private void initContent() {
		
		area = (TextView)findViewById(R.id.textViewTherapyListArea);
		
		area.setText("Behandlungsplatz " + Area.getActiveArea().toString());
		area.setTextColor(Area.getAreaColor());
		
		patientView = (ListView)findViewById(R.id.patientTherapyView); 
		patientView.setAdapter(adapter);
		patientView.setClickable(false);
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
