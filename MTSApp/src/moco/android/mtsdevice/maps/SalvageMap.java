package moco.android.mtsdevice.maps;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.DeviceButtons;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SalvageMap extends FragmentActivity {

	private GoogleMap map;

	//TODO Fehlerbehebung
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salvage_map);
		
		setUpMapIfNeeded();
		
		
	}
	
	private void setUpMapIfNeeded() {
		
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.salvage_map)).getMap();
            
            if (map != null) {
                setUpMap();
            }
        }
	}
	
	private void setUpMap() {
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
	
	
	@Override
	public void onBackPressed() {
		
		DeviceButtons.getToModeSelection(this);
	}
}
