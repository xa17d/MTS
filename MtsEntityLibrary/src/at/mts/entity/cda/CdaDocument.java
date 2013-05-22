package at.mts.entity.cda;

import java.util.UUID;
import java.util.Date;
import at.mts.entity.Gender;
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
	
	
	/**
	 * Vorname des Patienten im Header
	 */
	private String patientNameGiven;
	/**
	 * @return Vorname des Patienten im Header
	 */
	public String getPatientNameGiven(){  return patientNameGiven;  }
	/**
	 * Setzt den Vornamen des Patienten im Header
	 * @param name neuer Name
	 */
	private void setPatientNameGiven(String name){
		patientNameGiven=name;
	}
	
	/**
	 * Nachname des Patienten im Header
	 */
	private String patientNameFamily;
	/**
	 * @return Nachname des Patienten im Header
	 */
	public String getPatientNameFamily(){  return patientNameFamily;  }
	/**
	 * Setzt den Nachnamen des Patienten im Header
	 * @param name neuer Name
	 */
	private void setPatientNameFamily(String name){
		patientNameFamily=name;
	}

	/**
	 * Geburtsdatum des Patienten im Header
	 */
	private Date patientBirthTime;
	/**
	 * @return Geburtsdatum des Patienten im Header
	 */
	public Date getPatientBirthTime(){  return patientBirthTime;  }
	/**
	 * Setzt das Geburtsdatum des Patienten im Header
	 * @param date neues Datum
	 */
	public void setPatientBirthTime(Date date){
		patientBirthTime = date;
	}	

	/**
	 * Geschlecht des Patienten im Header
	 */
	private Gender patientGender;
	/**
	 * @return Geschlecht des Patienten im Header
	 */
	public Gender getPatientGender(){  return patientGender;  }
	/**
	 * Setzt das Geschlecht des Patienten im Header
	 * @param gender neues Geschlecht
	 */
	private void setPatientGender(Gender gender){
		patientGender = gender;
	}
	
	/**
	 * Nachname des Authors im Header
	 */
	private String authorNameFamily;
	/**
	 * @return Nachname des Authors im Header
	 */
	public String getAuthorNameFamily(){  return authorNameFamily;  }
	/**
	 * Setzt den Nachnamen des Authors im Header
	 * @param name neuer Name
	 */
	private void setAuthorNameFamily(String name){
		authorNameFamily=name;
	}

	/**
	 * Vorname des Authors im Header
	 */
	private String authorNameGiven;
	/**
	 * @return Vorname des Authors im Header
	 */
	public String getAuthorNameGiven(){  return authorNameGiven;  }
	/**
	 * Setzt den Vornamen des Authors im Header
	 * @param name neuer Name
	 */
	private void setAuthorNameGiven(String name){
		authorNameGiven=name;
	}
	
	
	
	/**
	 * ID des Authors im Header
	 */
	private UUID authorId;
	/**
	 * @return ID des Authors im Header
	 */
	public UUID getAuthorId(){  return authorId;  }
	/**
	 * Setzt die ID des Authors im Header
	 * @param value neuer Wert
	 */
	public void setAuthorId(UUID value){
		authorId = value;
	}
	

	private CdaBody body = new CdaBody();
	/**
	 * @return CDA-Body
	 */
	public CdaBody getBody() { return body; }
}
