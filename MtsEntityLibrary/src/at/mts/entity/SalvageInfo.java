package at.mts.entity;

import java.util.ArrayList;

public enum SalvageInfo {

	Bergeschere, Schaufeltrage, Trage, Sauerstoff, Schmerztherapie;
	
	public static ArrayList<SalvageInfo> getAll() {
		ArrayList<SalvageInfo> result = new ArrayList<SalvageInfo>();
		SalvageInfo[] temp = values();
		
		for(int i = 0; i < temp.length; i++)
			result.add(temp[i]);
		
		return result;
	}
}
