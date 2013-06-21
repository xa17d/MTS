package at.mts.entity;

/**
 * Ein Eintrag in PatientList
 * Hat nur die Eigenschaften Category, Treatment, NameGiven und NameFamily. Zusaetzlich noch der URL zur vollstaendigen Patienten-Resource
 */
public class PatientListItem {
	
	/**
	 * Leeres PatientListItem
	 */
	public PatientListItem() { }
	
	/**
	 * Erzeugt ein PatientListItem anhand eines Patienten
	 * @param patient Patient
	 * @param url URL des Patienten
	 */
	public PatientListItem(Patient patient, String url) {
		setNameFamily(patient.getNameFamily());
		setNameGiven(patient.getNameGiven());
		setCategory(patient.getCategory());
		setTreatment(patient.getTreatment());
		setUrl(url);
	}
	
	private TriageCategory category;
	public TriageCategory getCategory() { return category; }
	public void setCategory(TriageCategory value) { this.category = value; }

	private Treatment treatment;
	public Treatment getTreatment() { return treatment; }
	public void setTreatment(Treatment value) { this.treatment = value; }

	private String nameGiven;
	public String getNameGiven() { return nameGiven; }
	public void setNameGiven(String value) { this.nameGiven = value; }

	private String nameFamily;
	public String getNameFamily() { return nameFamily; }
	public void setNameFamily(String value) { this.nameFamily = value; }

	private String url;
	public String getUrl() { return url; }
	public void setUrl(String value) { this.url = value; }
	
	
	public String toSalvageString() {
		
		return String.valueOf(category) + " (G/A)\nEntfernung: --.- Meter"; 
	}
	
	public String toTherapyString() {
		
		return nameFamily + ", " + nameGiven + " (G / ??)\nDringlichkeit: undef";
	}
}
