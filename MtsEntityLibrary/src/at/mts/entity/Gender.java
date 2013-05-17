package at.mts.entity;

public enum Gender {
	KeineAngabe, Maennlich, Weiblich;

	public static String[] toStringArray() {
		return new String[] { "männlich", "weiblich" };
	}

	public static Gender getValueOf(String value) {
		if (value == null) {
			return Gender.KeineAngabe;
		}
		if (value.toLowerCase().equals("weiblich")) {
			return Gender.Weiblich;
		}
		if (value.toLowerCase().equals("männlich")) {
			return Gender.Maennlich;
		}
		return Gender.KeineAngabe;
	}
}
