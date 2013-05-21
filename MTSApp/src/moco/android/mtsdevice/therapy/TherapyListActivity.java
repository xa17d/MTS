package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Area;
import moco.android.mtsdevice.handler.DeviceButtons;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class TherapyListActivity extends Activity {

	private TextView area;
	private TextView category;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_list);
		
		initContent();
	}
	
	private void initContent() {
		
		area = (TextView)findViewById(R.id.textTherapyListArea);
		category = (TextView)findViewById(R.id.textTherapyListCategory);
		
		area.setText("Behandlungsplatz " + Area.getActiveArea().toString());
		area.setTextColor(Area.getAreaColor());
		
		category.setText(Area.getActiveArea().toString());
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		
		DeviceButtons.getToModeSelection(this);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.onBackPressed(this);
	}
}
