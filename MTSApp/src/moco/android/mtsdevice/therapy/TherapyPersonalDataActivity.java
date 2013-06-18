package moco.android.mtsdevice.therapy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.SelectedPatient;
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
	
	private EditText txtFirstname;
	private EditText txtLastname;
	private EditText txtBirthdate;
	private EditText txtStreet;
	private EditText txtCity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.therapy_personal_data);
		
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
		
		//TODO Adresse
		
		
		Toast.makeText(this, R.string.info_saved, Toast.LENGTH_LONG).show();
		finish();
	}
	
	private void initControls() {
				
		txtFirstname = (EditText)findViewById(R.id.personalDataFirstname);
		txtLastname = (EditText)findViewById(R.id.personalDataLastname);
		txtBirthdate = (EditText)findViewById(R.id.personalDataBirthdate);
		txtStreet = (EditText)findViewById(R.id.personalDataStreet);
		txtCity = (EditText)findViewById(R.id.personalDataCity);
	}
	
	private void loadData() {
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		if(!selectedPatient.getNameGiven().equals("John"))
			txtFirstname.setText(selectedPatient.getNameGiven());
		
		if(!selectedPatient.getNameFamily().equals("Doe"))
			txtLastname.setText(selectedPatient.getNameFamily());
		
		if(selectedPatient.getBirthTime() != null)
			txtBirthdate.setText(df.format(selectedPatient.getBirthTime()));
		
		//TODO Adresse
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
