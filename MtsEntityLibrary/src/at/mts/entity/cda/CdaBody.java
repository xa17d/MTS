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
	
	
	//Details
	public static final String KEY_GPS ="gps";
	public static final String KEY_LEBENSPHASE ="lebensphase";
	public static final String KEY_BERGEINFORMATION ="bergeinformation";
	public static final String KEY_HILFPLATZPOSITION ="hilfplatzposition";
	public static final String KEY_DRINGLICHKEIT ="dringlichkeit";
	public static final String KEY_DIAGNOSE ="diagnose";
	public static final String KEY_BLUTDRUCK ="blutdruck";
	public static final String KEY_PULS ="puls";
	public static final String KEY_BEHANDLUNGSVERLAUF ="behandlungsverlauf";
	public static final String KEY_TRANSPORTBEREITSCHAFT ="transportbereitschaft";
	public static final String KEY_ZIELKRANKENHAUS ="zielkrankenhaus";
	public static final String KEY_KRANKEKASSE ="krankenkasse";
	
	
	//Bodyparts
	public static final String KEY_FRONT_HEAD = "Vorne_Kopf";
	public static final String KEY_FRONT_NECK = "Vorne_Hals";
	public static final String KEY_FRONT_CHEST = "Vorne_Brust";
	public static final String KEY_FRONT_ABDOMEN = "Vorne_Bauch";
	public static final String KEY_FRONT_R_UPPERARM = "Vorne_ROberarm";
	public static final String KEY_FRONT_L_UPPERARM = "Vorne_LOberarm";
	public static final String KEY_FRONT_R_FOREARM = "Vorne_RUnterarm";
	public static final String KEY_FRONT_L_FOREARM = "Vorne_LUnterarm";
	public static final String KEY_FRONT_R_HAND = "Vorne_RHand";
	public static final String KEY_FRONT_L_HAND = "Vorne_LHand";
	public static final String KEY_FRONT_L_UPPERLEG = "Vorne_LOberschenkel";
	public static final String KEY_FRONT_R_UPPERLEG = "Vorne_ROberschenkel";
	public static final String KEY_FRONT_L_SHANK = "Vorne_LUnterschenkel";
	public static final String KEY_FRONT_R_SHANK = "Vorne_RUnterschenkel";
	public static final String KEY_FRONT_L_FOOT = "Vorne_LFuss";
	public static final String KEY_FRONT_R_FOOT = "Vorne_RFuss";
	public static final String KEY_BACK_HEAD = "Hinten_Kopf";
	public static final String KEY_BACK_NECK = "Hinten_Hals";
	public static final String KEY_BACK_UPPERBACK = "Hinten_Brust";
	public static final String KEY_BACK_LOWERBACK = "Hinten_Bauch";
	public static final String KEY_BACK_R_UPPERARM = "Hinten_ROberarm";
	public static final String KEY_BACK_L_UPPERARM = "Hinten_LOberarm";
	public static final String KEY_BACK_R_FOREARM = "Hinten_RUnterarm";
	public static final String KEY_BACK_L_FOREARM = "Hinten_LUnterarm";
	public static final String KEY_BACK_R_HAND = "Hinten_RHand";
	public static final String KEY_BACK_L_HAND = "Hinten_LHand";
	public static final String KEY_BACK_L_UPPERLEG = "Hinten_LOberschenkel";
	public static final String KEY_BACK_R_UPPERLEG = "Hinten_ROberschenkel";
	public static final String KEY_BACK_L_SHANK = "Hinten_LUnterschenkel";
	public static final String KEY_BACK_R_SHANK = "Hinten_RUnterschenkel";
	public static final String KEY_BACK_L_FOOT = "Hinten_LFuss";
	public static final String KEY_BACK_R_FOOT = "Hinten_RFuss";

	
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
		@Override
		public int hashCode() {
			return this.key.toLowerCase().hashCode();
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
