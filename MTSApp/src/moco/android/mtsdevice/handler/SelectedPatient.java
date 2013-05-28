package moco.android.mtsdevice.handler;

import at.mts.entity.Patient;

public class SelectedPatient {

	private static Patient selectedPatient;
	
	public static void setPatient(Patient p) { selectedPatient = p; }
	public static Patient getPatient() { return selectedPatient; }
}
