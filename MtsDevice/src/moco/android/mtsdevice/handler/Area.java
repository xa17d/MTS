package moco.android.mtsdevice.handler;

public enum Area {

	I, II, III, IV, undef;
	
	
	/**
	 * Aktuellen Behandlungsplatz speichern
	 */
	private static Area area;
	public static void setActiveArea(Area a) { area = a; }
	public static Area getActiveArea() { return area; }
}