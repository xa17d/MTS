package moco.android.mtsdevice.handler;

import moco.android.mtsdevice.ModeActivity;
import android.app.Activity;
import android.content.Intent;

public class DeviceButtons {

	public static boolean getToModeSelection(Activity parent) {
		
		Intent intent = new Intent(parent, ModeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		parent.startActivity(intent);
		
		return true;
	}
	
	public static void onBackPressed(Activity parent) {
		
		parent.finish();
	}
}
