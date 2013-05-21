package moco.android.mtsdevice.handler;

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
}