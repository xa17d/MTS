package at.mts.entity.cda;

public class CdaBoolean {
	public static String asCdaValue(Boolean value) {
		if ((value == null)||(value == false)) { return "nein"; }
		else { return "ja"; }
	}
	
	public static Boolean getValueOf(String s) {
		if ("ja".equals(s)) {
			return true;
		} else {
			return false;
		}
	}
}
