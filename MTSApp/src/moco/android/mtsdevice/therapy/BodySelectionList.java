package moco.android.mtsdevice.therapy;

import java.text.SimpleDateFormat;
import java.util.Date;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Bodypart;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.handler.listadapter.MTSBodyInjuryAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import at.mts.entity.Patient;

public class BodySelectionList extends Activity implements OnItemClickListener{

	private TextView txtBodyPart;
	
	private Bodypart selectedBodypart;
	private Patient selectedPatient;
	
	private ListView injuryView;
	private ListAdapter adapter;
	
	private SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.body_list);
		
		selectedBodypart = Bodypart.getBodypart();
		selectedPatient = SelectedPatient.getPatient();
		
		adapter = new MTSBodyInjuryAdapter<String>(getApplicationContext(), R.layout.mts_list, selectedBodypart.getInjury());
		
		initContent();
	}
	
	private void initContent() {
		
		txtBodyPart = (TextView)findViewById(R.id.textListBodypart);
		
		txtBodyPart.setText(selectedBodypart.toString());
		
		injuryView = (ListView)findViewById(R.id.bodyInjuryList); 
		injuryView.setAdapter(adapter);
		
		injuryView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		String diagnosis = injuryView.getAdapter().getItem(arg2).toString();
		selectedPatient.addDiagnosis(diagnosis + " - " + df.format(new Date()));
		Toast.makeText(this, diagnosis + " gespeichert", Toast.LENGTH_SHORT).show();
		finish();
	}
}
