package at.mts.entity;

public enum Zustand {
	KeineAngabe, Kritisch, Stabil;

	public static String[] toStringArray() {
		return new String[] { "kritisch", "stabil" };
	}

	public static Zustand getValueOf(String value) {
		if (value == null) {
			return Zustand.KeineAngabe;
		}
		if (value.toLowerCase().equals("kritisch")) {
			return Zustand.Kritisch;
		}
		if (value.toLowerCase().equals("stabil")) {
			return Zustand.Stabil;
		}
		return Zustand.KeineAngabe;
	}
}
