package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TherapyRecordDeathActivity extends Activity {

	private TextView txtReason;
	
	/*
	 * TODO
	 * Tod feststellen
	 */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_record_death);
		
		txtReason = (TextView)findViewById(R.id.textDeathReason);
	}
	
	public void recordDeath(View v) {
		
		if(txtReason.getText().equals("")) {
			new AlertDialog.Builder(this) 
		        	.setMessage(R.string.error_no_reason)
		        	.setNeutralButton(R.string.ok, null)
		        	.show();
		}
		else
			finish();
	}
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
