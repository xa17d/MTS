package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Area;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TherapyAreaActivity extends Activity implements OnClickListener {

	private Button btnArea1;
	private Button btnArea2;
	private Button btnArea3;
	private Button btnArea4;
	private Button btnChangeMode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_area);
		
		Area.setActiveArea(Area.undef);
		
		initButtons();
	}
	
	@Override
	public void onClick(View v) {

		if(v == btnChangeMode)
			finish();
		
		if(v == btnArea1) {
			Area.setActiveArea(Area.I);
		}
		
		if(v == btnArea2) {
			Area.setActiveArea(Area.II);
		}
		
		if(v == btnArea3) {
			Area.setActiveArea(Area.III);
		}
		
		if(v == btnArea4) {
			Area.setActiveArea(Area.IV);
		}
		
		Intent intent = new Intent(this, TherapyActivity.class);
		startActivity(intent);
	}
	
	private void initButtons() {
		
		btnArea1 = (Button)findViewById(R.id.buttonArea1);
		btnArea2 = (Button)findViewById(R.id.buttonArea2);
		btnArea3 = (Button)findViewById(R.id.buttonArea3);
		btnArea4 = (Button)findViewById(R.id.buttonArea4);
		btnChangeMode = (Button)findViewById(R.id.buttonChangeModeInArea);
		
		btnArea1.setOnClickListener(this);
		btnArea2.setOnClickListener(this);
		btnArea3.setOnClickListener(this);
		btnArea4.setOnClickListener(this);
		btnChangeMode.setOnClickListener(this);
	}
	
	/*
	@Override
	public void onBackPressed() {
		//DO NOTHING
	}
	*/
}
