package at.mts.entity;

public enum Gender {
	notSpecified, male, female;

	public static String[] toStringArray() {
		return new String[] { "f", "m" };
	}

	public static Gender getValueOf(String value) {
		if (value == null) {
			return Gender.notSpecified;
		}
		if (value.toLowerCase().equals("f")) {
			return Gender.female;
		}
		if (value.toLowerCase().equals("m")) {
			return Gender.male;
		}
		if (value.toLowerCase().equals("female")) {
			return Gender.female;
		}
		if (value.toLowerCase().equals("male")) {
			return Gender.male;
		}
		return Gender.notSpecified;
	}
	
	public static String asCdaValue(Gender gender) {
		if (gender == null) { gender = notSpecified; }
		switch (gender) {
			case male: return "M";
			case female: return "F";
			default: return "UN";
		}
	}
}
