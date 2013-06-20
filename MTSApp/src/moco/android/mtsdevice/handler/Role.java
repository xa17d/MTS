package moco.android.mtsdevice.handler;

public enum Role {

	MD, PARAMEDIC, FIREFIGHTER, NATIONALGUARD, loggedout;
	
	
	private static Role activeRole;
	
	public static Role getActiveRole() { return activeRole; }
	public static void setActiveRole(Role role) { activeRole = role; }
}
