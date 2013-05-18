package moco.android.mtsdevice.triage;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import at.mts.entity.SalvageInfo;

public class TriageSalvageinfoAdd extends Activity implements OnItemClickListener {

	private ListView infoView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.triage_salvageinfo_add);
        
        ListAdapter adapter = new ArrayAdapter<SalvageInfo>(getApplicationContext(), android.R.layout.simple_list_item_1, SalvageInfo.values());
        
        infoView = (ListView)findViewById(R.id.infoView); 
        infoView.setAdapter(adapter);
        infoView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		String s = infoView.getAdapter().getItem(arg2).toString();
		
		Intent intent = new Intent(this, TriageSalvageinfoActivity.class);
		intent.putExtra("newItem", s);
		
		startActivity(intent);
	}
}