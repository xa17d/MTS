package moco.android.mtsdevice.therapy;

import java.util.ArrayList;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.Bodypart;
import moco.android.mtsdevice.handler.MTSListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import at.mts.entity.Patient;

public class BodySelectionList extends Activity {

	private TextView txtBodyPart;
	
	Bodypart selectedBodypart;
	
	private ExpandableListView injuryView;
	private ListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.body_list);
		
		selectedBodypart = Bodypart.getBodypart();
		
		//adapter = new
		
		initContent();
	}
	
	private void initContent() {
		
		txtBodyPart = (TextView)findViewById(R.id.textListBodypart);
		
		txtBodyPart.setText(selectedBodypart.toString());
		
		injuryView = (ExpandableListView)findViewById(R.id.expandableBodyInjuryList); 
		//injuryView.setAdapter(adapter);
	}
}
