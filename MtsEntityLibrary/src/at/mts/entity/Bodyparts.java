package at.mts.entity;

import java.util.HashMap;

import at.mts.entity.cda.CdaBody;

public class Bodyparts {
	public static final String FRONT_HEAD = CdaBody.KEY_FRONT_HEAD;
	public static final String FRONT_NECK = CdaBody.KEY_FRONT_NECK;
	public static final String FRONT_CHEST = CdaBody.KEY_FRONT_CHEST;
	public static final String FRONT_ABDOMEN = CdaBody.KEY_FRONT_ABDOMEN;
	public static final String FRONT_R_UPPERARM = CdaBody.KEY_FRONT_R_UPPERARM;
	public static final String FRONT_L_UPPERARM = CdaBody.KEY_FRONT_L_UPPERARM;
	public static final String FRONT_R_FOREARM = CdaBody.KEY_FRONT_R_FOREARM;
	public static final String FRONT_L_FOREARM = CdaBody.KEY_FRONT_L_FOREARM;
	public static final String FRONT_R_HAND = CdaBody.KEY_FRONT_R_HAND;
	public static final String FRONT_L_HAND = CdaBody.KEY_FRONT_L_HAND;
	public static final String FRONT_L_UPPERLEG = CdaBody.KEY_FRONT_L_UPPERLEG;
	public static final String FRONT_R_UPPERLEG = CdaBody.KEY_FRONT_R_UPPERLEG;
	public static final String FRONT_L_SHANK = CdaBody.KEY_FRONT_L_SHANK;
	public static final String FRONT_R_SHANK = CdaBody.KEY_FRONT_R_SHANK;
	public static final String FRONT_L_FOOT = CdaBody.KEY_FRONT_L_FOOT;
	public static final String FRONT_R_FOOT = CdaBody.KEY_FRONT_R_FOOT;
	public static final String BACK_HEAD = CdaBody.KEY_BACK_HEAD;
	public static final String BACK_NECK = CdaBody.KEY_BACK_NECK;
	public static final String BACK_UPPERBACK = CdaBody.KEY_BACK_UPPERBACK;
	public static final String BACK_LOWERBACK = CdaBody.KEY_BACK_LOWERBACK;
	public static final String BACK_R_UPPERARM = CdaBody.KEY_BACK_R_UPPERARM;
	public static final String BACK_L_UPPERARM = CdaBody.KEY_BACK_L_UPPERARM;
	public static final String BACK_R_FOREARM = CdaBody.KEY_BACK_R_FOREARM;
	public static final String BACK_L_FOREARM = CdaBody.KEY_BACK_L_FOREARM;
	public static final String BACK_R_HAND = CdaBody.KEY_BACK_R_HAND;
	public static final String BACK_L_HAND = CdaBody.KEY_BACK_L_HAND;
	public static final String BACK_L_UPPERLEG = CdaBody.KEY_BACK_L_UPPERLEG;
	public static final String BACK_R_UPPERLEG = CdaBody.KEY_BACK_R_UPPERLEG;
	public static final String BACK_L_SHANK = CdaBody.KEY_BACK_L_SHANK;
	public static final String BACK_R_SHANK = CdaBody.KEY_BACK_R_SHANK;
	public static final String BACK_L_FOOT = CdaBody.KEY_BACK_L_FOOT;
	public static final String BACK_R_FOOT = CdaBody.KEY_BACK_R_FOOT;
	
	private HashMap<String, String> map = new HashMap<String, String>();
	
	public boolean isSet(String part) { return map.containsKey(part); }
	public void set(String part, String information) { map.put(part, information); }
	public void remove(String part) { map.remove(part); }
	public String get(String part) { return map.get(part); }

}
