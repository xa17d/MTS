package at.mts.entity.cda;

import java.util.HashMap;

/**
 * Body eines CdaDocument. Beeinhaltet Key-Value-Paare.
 * @author Daniel Gehrer
 */
public class CdaBody {
	public static final String KEY_GEHFAEHIGKEIT = "gehfaehigkeit";
	public static final String KEY_RESPIRATION = "respiration";
	public static final String KEY_PERFUSION = "perfusion";
	// TODO: restlichen Keys einfuegen
	
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
