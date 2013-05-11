package at.mts.entity;

import java.util.UUID;

import at.mts.entity.cda.CdaBody;
import at.mts.entity.cda.CdaDocument;

public class Patient {
	
	public Patient(CdaDocument document) {
		// TODO: Patienten Properties aus CDA-Dokument auslesen
		
		setId(document.getPatientId());
		
		// TODO: weitere Properties einlesen, z.B.:
		// setRespiration(Zustand.getValueOf(document.getBody().get(CdaBody.KEY_RESPIRATION)));
	}
	
	private UUID id;
	public void setId(UUID value) { id = value; }
	public UUID getId() { return id; }
}
