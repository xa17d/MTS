package at.mts.entity;

public enum Geschlecht {
	KeineAngabe, Maennlich, Weiblich;

	public static String[] toStringArray() {
		return new String[] { "männlich", "weiblich" };
	}

	public static Geschlecht getValueOf(String value) {
		if (value == null) {
			return Geschlecht.KeineAngabe;
		}
		if (value.toLowerCase().equals("weiblich")) {
			return Geschlecht.Weiblich;
		}
		if (value.toLowerCase().equals("männlich")) {
			return Geschlecht.Maennlich;
		}
		return Geschlecht.KeineAngabe;
	}
}
