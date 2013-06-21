package at.mts.entity;

public enum PhaseOfLife {
	child, adult, notSpecified;

	public static String[] toStringArray() {
		return new String[] { "child", "adult", "not specified" };
	}

	public static PhaseOfLife getValueOf(String value) {
		if (value == null) {
			return PhaseOfLife.notSpecified;
		}
		if (value.toLowerCase().equals("child")) {
			return PhaseOfLife.child;
		}
		if (value.toLowerCase().equals("adult")) {
			return PhaseOfLife.adult;
		}
		if (value.toLowerCase().equals("kind")) {
			return PhaseOfLife.child;
		}
		if (value.toLowerCase().equals("erwachsen")) {
			return PhaseOfLife.adult;
		}

		return PhaseOfLife.notSpecified;
	}
	
	public static String asCdaValue(PhaseOfLife phaseOfLife) {
		if (phaseOfLife == null) { phaseOfLife = notSpecified; }
		switch (phaseOfLife) {
			case child: return "Kind";
			case adult: return "Erwachsen";
			default: return "kA";
		}
	}
}
