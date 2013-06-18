package moco.android.mtsdevice.handler;

import at.mts.entity.Bodyparts;


public enum Bodypart {
	
	undef		("undef", null)
				{ @Override public String[] getInjury() { return null; } },
	HEAD		("Kopf", Bodyparts.FRONT_HEAD)
				{ @Override public String[] getInjury() { return new String[]{"Schaedel-Hirn-Trauma", "Schaedebasisfraktur", "Schnittwunde Kopf"}; } },
	CHEST		("Brust", Bodyparts.FRONT_CHEST)
				{ @Override public String[] getInjury() { return new String[]{"Thorax-Trauma vo", "Pneumo-Thorax", "Schluesselbeinfraktur li", "Schluesselbeinfraktur re"}; } },
	ABDOMEN		("Bauch", Bodyparts.FRONT_ABDOMEN)
				{ @Override public String[] getInjury() { return new String[]{"Schmerz Abdomen", "offene Verletzung Abdomen", "Beckenverletzung"}; } },
	LEFT_ARM	("Linker Arm", Bodyparts.FRONT_L_UPPERARM)
				{ @Override public String[] getInjury() { return new String[]{"Fraktur - OA li", "Fraktur offen - OA li", "Fraktur - UA li", "Fraktur offen - UA li","Schulterluxation li", "Amputation Bein li"}; } },
	RIGHT_ARM	("Rechter Arm", Bodyparts.FRONT_R_UPPERARM)
				{ @Override public String[] getInjury() { return new String[]{"Fraktur - OA re", "Fraktur offen - OA re", "Fraktur - UA re", "Fraktur offen - UA re","Schulterluxation re", "Amputation Bein re"}; } },
	LEFT_LEG	("Linkes Bein", Bodyparts.FRONT_L_UPPERLEG)
				{ @Override public String[] getInjury() { return new String[]{"Fraktur - OS li", "Fraktur offen - OS li", "Fraktur - US li", "Fraktur offen - US li","Schnittwunde li", "Amputation Arm li"}; } },
	RIGHT_LEG	("Rechtes Bein", Bodyparts.FRONT_R_UPPERLEG)	
				{ @Override public String[] getInjury() { return new String[]{"Fraktur - OS re", "Fraktur offen - OS re", "Fraktur - US re", "Fraktur offen - US re","Schnittwunde re", "Amputation Arm re"}; } },
	NECK		("Hals", Bodyparts.BACK_NECK)	
				{ @Override public String[] getInjury() { return new String[]{"HWS-Trauma", "Schleudertrauma"}; } },
	BACK		("Ruecken", Bodyparts.BACK_LOWERBACK)
				{ @Override public String[] getInjury() { return new String[]{"WS-Trauma", "Thorax-Trauma hi", "Schnittwunde Ruecken"}; } },
	LEFT_HAND	("Linke Hand", Bodyparts.FRONT_L_HAND)
				{ @Override public String[] getInjury() { return new String[]{"Fraktur - Hand li", "Schnittwunde Hand li"}; } },
	RIGHT_HAND	("Rechte Hand", Bodyparts.FRONT_R_HAND)
				{ @Override public String[] getInjury() { return new String[]{"Fraktur - Hand re", "Schnittwunde Hand re"}; } };
	
	private String name;
	private String part;
	
	private Bodypart(String name, String part) {
		this.name = name;
		this.part = part;
	}
	
	public abstract String[] getInjury();
	
	/**
	 * aktuellen Bodypart speichern
	 */
	private static Bodypart selectedBodypart;
	
	public static void setBodypart(Bodypart b) { selectedBodypart = b; }
	public static Bodypart getBodypart() { return selectedBodypart; }
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getBodypartName() {
		return this.part;
	}
}

