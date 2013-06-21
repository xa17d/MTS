package at.mts.entity;

import java.util.ArrayList;

public enum SalvageInfo {

	Trage, Tragstuhl, Schaufeltrage, Vakuummatratze, HWSSchiene, Bergeschere, Absaugeinheit, Sauerstoff, Schmerztherapie, Feuerwehr, Wasserrettung, Bergrettung, Polizei;
	
	public static ArrayList<SalvageInfo> getAll() {
		ArrayList<SalvageInfo> result = new ArrayList<SalvageInfo>();
		SalvageInfo[] temp = values();
		
		for(int i = 0; i < temp.length; i++)
			result.add(temp[i]);
		
		return result;
	}
}
