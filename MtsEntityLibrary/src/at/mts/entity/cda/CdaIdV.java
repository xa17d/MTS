package at.mts.entity.cda;

import java.util.UUID;

/**
 * CDA-Dokument ID und Version
 * @author Daniel
 */
public class CdaIdV {
	public CdaIdV(UUID id, int version) {
		this.id = id;
		this.version = version;
	}
	
	private UUID id;
	public UUID getId() { return id; }
	
	private int version;
	public int getVersion() { return version; }
}
