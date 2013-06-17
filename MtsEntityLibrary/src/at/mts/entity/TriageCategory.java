package at.mts.entity;

public enum TriageCategory {
	 immediate, delayed, minor, deceased, notSpecified;
	 
	 public int getTriageColor() {
		 
		 if(this == notSpecified)
			 return -1;				//WHITE
		 if(this == deceased)
			 return -16777216;		//BLACK
		 if(this == immediate)
			 return -65536;			//RED
		 if(this == delayed)
			 return -256;			//YELLOW
		 return -16711936;			//GREEN
	 }
	 
	 public static TriageCategory getValueOf(String value) {
		if (value == null) {
			return TriageCategory.notSpecified;
		}
		if (value.toLowerCase().equals("immediate")) {
			return TriageCategory.immediate;
		}
		if (value.toLowerCase().equals("delayed")) {
			return TriageCategory.delayed;
		}
		if (value.toLowerCase().equals("minor")) {
			return TriageCategory.minor;
		}
		if (value.toLowerCase().equals("deceased")) {
			return TriageCategory.deceased;
		}
		return TriageCategory.notSpecified;
	}
	 
	public static String asCdaValue(TriageCategory triageCategory) {
		if (triageCategory == null) { triageCategory = notSpecified; }
		switch (triageCategory) {
			case immediate: return "immediate";
			case delayed: return "delayed";
			case minor: return "minor";
			case deceased: return "deceased";
			default: return "kA";
		}
	}
}
