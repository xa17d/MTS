package at.mts.entity.cda;

import java.util.HashMap;

/**
 * Body eines CdaDocument. Beeinhaltet Key-Value-Paare.
 * @author Daniel Gehrer
 */
public class CdaBody {
	
	
	//Vitalzeichen
	public static final String KEY_GEHFAEHIGKEIT = "gehfaehigkeit";
	public static final String KEY_RESPIRATION = "respiration";
	public static final String KEY_PERFUSION = "perfusion";
	public static final String KEY_MENTALERSTATUS = "mentalerstatus";
	public static final String KEY_TRIAGEKATEGORIE = "triagekategorie";
	public static final String KEY_BEHANDLUNG = "behandlung";
	//TODO : Section/Component-Unterteilung ?
	
	/*
	//Details
	public static final String KEY_GPS ="gps";
	public static final String KEY_LEBENSPHASE ="gps";
	public static final String KEY_BERGEINFORMATION ="gps";
	public static final String KEY_HILFPLATZPOSITION ="gps";
	public static final String KEY_DRINGLICHKEIT ="gps";
	public static final String KEY_DIAGNOSE ="gps";
	public static final String KEY_BLUTDRUCK ="gps";
	public static final String KEY_PULS ="gps";
	public static final String KEY_BEHANDLUNGSVERLAUF ="gps";
	public static final String KEY_TRANSPORTBEREITSCHAFT ="gps";
	public static final String KEY_ZIELKRANKENHAUS ="gps";
	public static final String KEY_KRANKEKASSE ="gps";
	*/
	
	/*
	//Bodyparts
	public Static final String Vorne_Kopf= ="";
    public static final String Vorne_Hals ="";
    public static final String Vorne_Brust ="";
    public static final String Vorne_Bauch ="";
    public static final String Vorne_ROberarm ="";
    public static final String Vorne_LOberarm ="";
    public static final String Vorne_RUnterarm ="";
    public static final String Vorne_LUnterarm ="";
    public static final String Vorne_RHand ="";
    public static final String Vorne_LHand ="";
    public static final String Vorne_LOberschenkel ="";
    public static final String Vorne_ROberschenkel ="";
    public static final String Vorne_LUnterschenkel ="";
    public static final String Vorne_RUnterschenkel ="";
    public static final String Vorne_LFuss ="";
    public static final String Vorne_RFuss ="";
    public static final String Hinten_Kopf ="";
    public static final String Hinten_Hals ="";
    public static final String Hinten_Brust ="";
    public static final String Hinten_Bauch ="";
    public static final String Hinten_ROberarm ="";
    public static final String Hinten_LOberarm ="";
    public static final String Hinten_RUnterarm ="";
    public static final String Hinten_LUnterarm ="";
    public static final String Hinten_RHand ="";
    public static final String Hinten_LHand ="";
    public static final String Hinten_LOberschenkel ="";
    public static final String Hinten_ROberschenkel ="";
    public static final String Hinten_LUnterschenkel ="";
    public static final String Hinten_RUnterschenkel ="";
    public static final String Hinten_LFuss ="";
    public static final String Hinten_RFuss ="";
	*/
	
	
	
	
	/**
	 * Repraesentiert einen Key im Body.
	 * Es wird die Equals-Methode so ueberschrieben, dass zwei Keys
	 * case-insensitive Keys gleich sind.
	 * @author Daniel Gehrer
	 */
	private class Key {
		
		public Key(String key) { this.key = key; }
		private String key;
		@Override
		public String toString() {
			return key;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Key) {
				Key other = (Key)obj;
				return (this.key.toLowerCase().equals(other.key.toLowerCase()));
			}
			else { return false; }
		}
	}
	
	/**
	 * Keys mit den zugehoerigen Werten
	 */
	private HashMap<Key, String> bodyValues = new HashMap<Key, String>();
	
	private String get(Key key) {
		return bodyValues.get(key);
	}
	
	/**
	 * Gibt den Wert fuer einen Key zurueck
	 * @param key Key
	 * @return Wert
	 */
	public String get(String key) {
		return get(new Key(key));
	}
	
	/**
	 * Speichert einen neuen Wert mit einem bestimmten Key ab.
	 * Wird der Key bereits verwendet, wird der alte Wert ueberschrieben.
	 * @param key Key
	 * @param value Wert
	 */
	public void set(String key, String value) {
		bodyValues.put(new Key(key), value);
	}
	
	/**
	 * Entfernt einen Wert mit einem bestimmten Key
	 * @param key Key
	 */
	public void remove(String key) {
		bodyValues.remove(key);
	}
	
	/**
	 * Erzeugt den CDA-Body mit dem Format:
	 * Key: Value<br />
	 * ...
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Key key : bodyValues.keySet()) {
			s.append(key.toString()+": "+get(key)+"<br />\n");
		}
		return s.toString();
	}
}
