package at.mts.entity;

public enum Condition {
	
	stable, critical, notSpecified;

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

		return Condition.notSpecified;
	}
	
	public static String asCdaValue(Condition condition) {
		switch (condition) {
			case stable: return "stabil";
			case critical: return "kritisch";
			default: return "kA";
		}
	}
}
