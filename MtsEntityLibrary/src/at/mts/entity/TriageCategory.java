package at.mts.entity;

import java.awt.Color;

public enum TriageCategory {
	 immediate, delayed, minor, deceased, notSpecified;
	 
	 public Color getTriageColor() {
		 
		 if(this == notSpecified)
			 return Color.WHITE;
		 if(this == deceased)
			 return Color.BLACK;
		 if(this == immediate)
			 return Color.RED;
		 if(this == delayed)
			 return Color.YELLOW;
		 return Color.GREEN;
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
}
