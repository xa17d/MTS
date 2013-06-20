package moco.android.mtsdevice.handler;

public enum Mode {

	triage, salvage, therapy, loggedout, loggedin, undef;
	
	
	/**
	 * Aktuellen Modus speichern
	 */
	private static Mode mode;
	public static void setActiveMode(Mode m) { mode = m; }
	public static Mode getActiveMode() { return mode; }
}
