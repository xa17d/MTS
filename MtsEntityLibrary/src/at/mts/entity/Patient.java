package at.mts.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import at.mts.entity.cda.CdaDocument;

public class Patient {
	
	private UUID id;
	private TriageCategory category;
	private ArrayList<SalvageInfo> salvageinfo = new ArrayList<SalvageInfo>();
	
	public Patient(CdaDocument document) {
		// TODO: Patienten Properties aus CDA-Dokument auslesen
		
		setId(document.getPatientId());
		
		// TODO: weitere Properties einlesen, z.B.:
		// setRespiration(Zustand.getValueOf(document.getBody().get(CdaBody.KEY_RESPIRATION)));
	}
	
	public Patient(UUID id) {
		
		setId(id);
	}
	
	/**
	 * Setter
	 */
	public void setId(UUID value) { this.id = value; }
	public void setCategory(TriageCategory category) { this.category = category; }
	
	public void addSalvageInfo(SalvageInfo info) { this.salvageinfo.add(info); }
	
	/**
	 * Getter
	 */
	public UUID getId() { return id; }
	public ArrayList<SalvageInfo> getSalvageInfo() { return salvageinfo; }
	public TriageCategory getCategory() { return category; }
	
	public String getSalvageInfoString() {
		
		String s = "";
		
		Iterator<SalvageInfo> it = salvageinfo.iterator();
		
		while(it.hasNext())
			s = s + it.next() + ";\n";
		
		return s;
	}
	
	/**
	 * statisch aktuellen Patienten definieren
	 */
	private static Patient selectedPatient;
	
	public static Patient getSelectedPatient() {
		return selectedPatient;
	}
	
	public static void setSelectedPatient(Patient p) {
		selectedPatient = p;
	}
}
