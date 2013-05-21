package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import at.mts.entity.*;

public class TriageSalvageinfoAdd extends Activity implements OnItemClickListener {

	private ListView infoView;
	
	private Patient patient;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_salvageinfo_add);
        
        patient = Patient.getSelectedPatient();
        
        ListAdapter adapter = new ArrayAdapter<SalvageInfo>(getApplicationContext(), android.R.layout.simple_list_item_1, SalvageInfo.values());
        
        infoView = (ListView)findViewById(R.id.infoView); 
        infoView.setAdapter(adapter);
        infoView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		patient.addSalvageInfo(SalvageInfo.valueOf(infoView.getAdapter().getItem(arg2).toString()));
		
		finish();
	}
}