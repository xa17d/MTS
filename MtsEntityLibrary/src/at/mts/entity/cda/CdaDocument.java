package at.mts.entity.cda;

import java.util.UUID;

/**
 * CDA-Dokument.
 * Ermoeglicht das einlesen oder erzeugen eines CDA-Dokuments.
 * Eigenschaften koennen mittels get- und set-Methoden ausgelesen oder geandert werden.
 * @author Daniel Gehrer
 */
public class CdaDocument {
	
	/**
	 * Erzeugt ein neues, leeres CDA-Dokument
	 */
	public CdaDocument() {
		setIdV(new CdaIdV(UUID.randomUUID(), 1));
	}
	
	/**
	 * Erzeugt ein CdaDocument aus einem XML-String
	 * @param document CDA Dokument als XML-String
	 */
	public CdaDocument(String document) {
		load(document);
	}
	
	/**
	 * Liest ein CdaDocument aus einem XML-String.
	 * Die bestehenden Daten gehen dabei verloren.
	 * @param document CDA Dokument als XML-String
	 */
	public void load(String document) {
		body = new CdaBody(); // Body loeschen
		
		// TODO: document auslesen und Property Werte aktualisieren
	}
	
	/**
	 * Gibt das CDA-Dokument als XML-String zurueck
	 * @return CDA-Dokument als XML-String
	 */
	public String asXml() {
		// TODO: aus Eigenschaften ein CDA XML erzeugen
		return null;
	}
	
	/**
	 * ID und Version des Dokuments
	 */
	private CdaIdV idV = null;
	/**
	 * @return ID und Version des Dokuments
	 */
	public CdaIdV getIdV() { return idV; }
	/**
	 * Setzt ID und Version des Dokuments
	 * @param value neuer Wert
	 */
	public void setIdV(CdaIdV value) { idV = value; }
	
	/**
	 * ID und Version des vorgaenger Dokuments
	 */
	private CdaIdV parentIdV = null;
	/**
	 * @return ID und Version des vorgaenger Dokuments
	 */
	public CdaIdV getParentIdV() { return parentIdV; }
	/**
	 * Setzt ID und Version des vorgaenger Dokuments 
	 * @param value neuer Wert
	 */
	public void setParentIdV(CdaIdV value) { parentIdV = value; }
	
	/**
	 * ID des Patienten im Header
	 */
	private UUID patientId = null;
	/**
	 * @return ID des Patienten im Header
	 */
	public UUID getPatientId() { return patientId; }
	/**
	 * Setzt ID des Patienten im Header
	 * @param value neuer Wert
	 */
	public void setPatientId(UUID value) { patientId = value; }
	
	// TODO: Private Variablen, Getter und Setter fuer:
	// PatientId : UUID
	// PatientNameGiven : String
	// PatientNameFamily : String
	// PatientGender : Geschlecht
	// PatientBirthTime : Date
	// AuthorId : UUID
	// AuthorNameGiven : String
	// AuthorNameFamily : String
	
	private CdaBody body = new CdaBody();
	/**
	 * @return CDA-Body
	 */
	public CdaBody getBody() { return body; }
}
