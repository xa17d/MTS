package moco.android.mtsdevice.handler;

public enum Therapy {

	MEDICATION	{ @Override public String[] getTherapys() { return new String[]{"Glukose 40%", "NaCl 0,9%", "Buscopan (1ml)", "Naloxon (1ml)", "Dipidolor (2ml)", "Ketanest (100mg)", "Dormicum (5ml)", "Aspirin (5ml)", "Heparin (0,2ml)", "Suprarenin (25ml)", "Lasix (2ml)"}; } },
	THERAPY		{ @Override public String[] getTherapys() { return new String[]{"TODO"}; } },
	WOUND		{ @Override public String[] getTherapys() { return new String[]{"Druckverband", "Abbindung" }; } },
	CPR			{ @Override public String[] getTherapys() { return new String[]{"Reanimation", "Intubation" }; } };
	
	public abstract String[] getTherapys();
	
	
	/**
	 * aktuelle Behandlung speichern
	 */
	private static Therapy selectedTherapy;
	
	public static void setSelectedTherapy(Therapy t) { selectedTherapy = t; }
	public static Therapy getSelectedTherapy() { return selectedTherapy; }
}
