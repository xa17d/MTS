package at.mts.entity;

public enum Gender {
	KeineAngabe, Maennlich, Weiblich;

	public static String[] toStringArray() {
		return new String[] { "f", "m" };
	}

	public static Gender getValueOf(String value) {
		if (value == null) {
			return Gender.KeineAngabe;
		}
		if (value.toLowerCase().equals("f")) {
			return Gender.Weiblich;
		}
		if (value.toLowerCase().equals("m")) {
			return Gender.Maennlich;
		}
		return Gender.KeineAngabe;
	}
}
