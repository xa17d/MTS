package moco.android.mtsdevice.handler;

public enum Bodypart {
	
	undef, HEAD, CHEST, ABDOMEN, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG, NECK, BACK, LEFT_HAND, RIGHT_HAND;

	
	/**
	 * aktuellen Bodypart speichern
	 */
	private static Bodypart selectedBodypart;
	
	public static void setBodypart(Bodypart b) { selectedBodypart = b; }
	public static Bodypart getBodypart() { return selectedBodypart; }
}

