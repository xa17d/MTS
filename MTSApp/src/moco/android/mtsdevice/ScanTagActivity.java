package moco.android.mtsdevice;

import moco.android.mtsdevice.handler.DeviceButtons;
import moco.android.mtsdevice.handler.Mode;
import moco.android.mtsdevice.salvage.SalvageActivity;
import moco.android.mtsdevice.therapy.TherapyListActivity;
import moco.android.mtsdevice.triage.TriageSelectionActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScanTagActivity extends Activity implements OnClickListener {

	private Button btnTagScanned;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scan_tag);
		
		initButtons();
	}
	
	@Override
	public void onClick(View v) {

		Intent intent = null;
		
		//TODO NFC-Tag lesen

		if(Mode.getActiveMode() == Mode.triage) {
			
			new AlertDialog.Builder(this) 
            	.setMessage(R.string.info_saved)
            	.setNeutralButton(R.string.ok, new DialogListener(this))
            	.show();
		}
		if(Mode.getActiveMode() == Mode.salvage)
			intent = new Intent(this, SalvageActivity.class);
		if(Mode.getActiveMode() == Mode.therapy)
			intent = new Intent(this, TherapyListActivity.class);
		
		if(intent != null)
			startActivity(intent);
	}
	
	private class DialogListener implements android.content.DialogInterface.OnClickListener {

		ScanTagActivity activity;
		
		public DialogListener(ScanTagActivity activity) {
			
			this.activity = activity;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Intent intent = new Intent(activity, TriageSelectionActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}
	}
	
	private void initButtons() {

		btnTagScanned = (Button)findViewById(R.id.buttonTagScanned);

		btnTagScanned.setOnClickListener(this);
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
