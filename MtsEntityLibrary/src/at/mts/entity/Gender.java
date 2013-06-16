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
		return Gender.notSpecified;
	}
}
