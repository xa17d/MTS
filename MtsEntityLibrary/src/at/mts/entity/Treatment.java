package at.mts.entity;

public enum Treatment {
	sighted, salvaged, transported, notSpecified;

	public static String[] toStringArray() {
		return new String[] { "sighted", "salvaged", "transported", "not specified" };
	}

	public static Treatment getValueOf(String value) {
		if (value == null) {
			return Treatment.notSpecified;
		}
		if (value.toLowerCase().equals("sighted")) {
			return Treatment.sighted;
		}
		if (value.toLowerCase().equals("salvaged")) {
			return Treatment.salvaged;
		}
		if (value.toLowerCase().equals("transported")) {
			return Treatment.transported;
		}
		
		if (value.toLowerCase().equals("gesichtet")) {
			return Treatment.sighted;
		}
		if (value.toLowerCase().equals("geborgen")) {
			return Treatment.salvaged;
		}
		if (value.toLowerCase().equals("abtransportiert")) {
			return Treatment.transported;
		}

		return Treatment.notSpecified;
	}
}
