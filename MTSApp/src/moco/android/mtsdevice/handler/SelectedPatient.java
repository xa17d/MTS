package moco.android.mtsdevice.handler;

import at.mts.entity.Patient;
import at.mts.entity.PatientListItem;

public class SelectedPatient {

	private static Patient selectedPatient;
	private static PatientListItem selectedPatientItem;
	
	public static void setPatient(Patient p) {
		selectedPatientItem = null;
		selectedPatient = p;
	}
	
	public static void setPatientItem(PatientListItem p) {
		selectedPatient = null;
		selectedPatientItem = p;
	}
	
	
	public static Patient getPatient() { return selectedPatient; }
	public static PatientListItem getPatientItem() { return selectedPatientItem; }
}
