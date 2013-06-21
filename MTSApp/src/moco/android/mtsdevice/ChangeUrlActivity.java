package moco.android.mtsdevice;

import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChangeUrlActivity extends Activity {

	private EditText txtUrl;
	private PatientService service;
	
	private static final String DEFAULTURL = "http://88.116.105.228:30104/MtsServer/restApi/patients/";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_url);
		
		service = PatientServiceImpl.getInstance();
		
		txtUrl = (EditText)findViewById(R.id.editTextnewUrl);
		txtUrl.setText(DEFAULTURL);
	}
	
	public void save(View v) {
		
		service.changeMtsServerAddress(String.valueOf(txtUrl.getText()));
		finish();
	}
}
