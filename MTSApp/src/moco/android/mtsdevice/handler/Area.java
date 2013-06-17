package moco.android.mtsdevice.handler;

import at.mts.entity.TriageCategory;

public enum Area {

	I, II, III, IV, undef;
	
	
	/**
	 * Aktuellen Behandlungsplatz speichern
	 */
	private static Area area;
	public static void setActiveArea(Area a) { area = a; }
	public static Area getActiveArea() { return area; }
	
	public static int getAreaColor() {
		 
		 if(area == undef)
			 return -1;				//WHITE
		 if(area == IV)
			 return -16777216;		//BLACK
		 if(area == I)
			 return -65536;			//RED
		 if(area == II)
			 return -256;			//YELLOW
		 return -16711936;			//GREEN
	}

	public boolean matchesCategory(TriageCategory category) {
		
		if(category == TriageCategory.immediate && area == I)
			return true;
		if(category == TriageCategory.delayed && area == II)
			return true;
		if(category == TriageCategory.minor && area == III)
			return true;
		if(category == TriageCategory.deceased && area == IV)
			return true;
		
		return false;
	}
}