package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.handler.Therapy;
import moco.android.mtsdevice.handler.listadapter.MTSListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import at.mts.entity.Patient;
import at.mts.entity.SalvageInfo;

public class TherapyListAdd extends Activity implements OnItemClickListener {

	private Patient selectedPatient;
	private ListView therapyView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_list_add);

		selectedPatient = SelectedPatient.getPatient();
        
        ListAdapter adapter = new MTSListAdapter<String>(getApplicationContext(), R.layout.mts_list, Therapy.getSelectedTherapy().getTherapys());
        
        therapyView = (ListView)findViewById(R.id.ListViewTherapyAdd); 
        therapyView.setAdapter(adapter);
        therapyView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		selectedPatient.addCourseOfTreatment(therapyView.getAdapter().getItem(arg2).toString());
		
		//TODO Uhrzeit speichern
		
		finish();
	}	
}
