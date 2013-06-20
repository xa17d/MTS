package moco.android.mtsdevice.therapy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
import moco.android.mtsdevice.service.PatientService;
import moco.android.mtsdevice.service.PatientServiceImpl;
import moco.android.mtsdevice.service.ServiceException;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import at.mts.entity.Patient;

public class TherapyPersonalDataActivity extends FragmentActivity {
	
	private Patient selectedPatient;
	private PatientService service;
	
	private EditText txtFirstname;
	private EditText txtLastname;
	private EditText txtBirthdate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_personal_data);
		
		service = PatientServiceImpl.getInstance();
		selectedPatient = SelectedPatient.getPatient();
		
		initControls();
		loadData();
	}
	
	public void pickBirthdate(View v) {
		
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void save(View v) {
		
		if(!txtFirstname.getText().equals(""))
			selectedPatient.setNameGiven(txtFirstname.getText().toString());
		
		if(!txtFirstname.getText().equals(""))
			selectedPatient.setNameFamily(txtLastname.getText().toString());
		
		try {
			service.updateExistingPatient(selectedPatient);
			Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
		} catch (ServiceException e) {
			new AlertDialog.Builder(this) 
	        	.setMessage(R.string.error_save_data)
	        	.setNeutralButton(R.string.ok, null)
	        	.show();
		}
		
		finish();
	}
	
	private void initControls() {
				
		txtFirstname = (EditText)findViewById(R.id.personalDataFirstname);
		txtLastname = (EditText)findViewById(R.id.personalDataLastname);
		txtBirthdate = (EditText)findViewById(R.id.personalDataBirthdate);
	}
	
	private void loadData() {
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date oldDate = null;
		try {
			oldDate = df.parse("01.01.1800");
		} catch (ParseException e) {
			//do nothing
		}
		
		if(!selectedPatient.getNameGiven().equals("John"))
			txtFirstname.setText(selectedPatient.getNameGiven());
		
		if(!selectedPatient.getNameFamily().equals("Doe"))
			txtLastname.setText(selectedPatient.getNameFamily());
		
		if(selectedPatient.getBirthTime().compareTo(oldDate) != 0)
			txtBirthdate.setText(df.format(selectedPatient.getBirthTime()));
	}
	
	
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			Calendar c = Calendar.getInstance();
			
			if(selectedPatient.getBirthTime() != null) {
				SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
				df.format(selectedPatient.getBirthTime());
				c = df.getCalendar();
			}
				
			
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			GregorianCalendar date = new GregorianCalendar(year, month, day);
			selectedPatient.setBirthTime(date.getTime());
			
			txtBirthdate.setText(day + "." + (month + 1) + "." + year);
		}
	}
	
	@Override
	public void onBackPressed() {
		
		//do nothing
	}
}
