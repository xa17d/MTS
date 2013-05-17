package at.mts.entity;

public enum Condition {
	yes, no, stable, critical, notSpecified;

	public static String[] toStringArray() {
		return new String[] { "critical", "stable" };
	}

	public static Condition getValueOf(String value) {
		if (value == null) {
			return Condition.notSpecified;
		}
		if (value.toLowerCase().equals("critical")) {
			return Condition.critical;
		}
		if (value.toLowerCase().equals("stable")) {
			return Condition.stable;
		}
		if (value.toLowerCase().equals("yes")) {
			return Condition.yes;
		}
		if (value.toLowerCase().equals("no")) {
			return Condition.no;
		}
		return Condition.notSpecified;
	}
}
