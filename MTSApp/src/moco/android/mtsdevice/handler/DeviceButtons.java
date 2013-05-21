package moco.android.mtsdevice.handler;

import moco.android.mtsdevice.ModeActivity;
import moco.android.mtsdevice.R;
import moco.android.mtsdevice.therapy.TherapyAreaActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class DeviceButtons {
	
	private static DeviceButtons b = new DeviceButtons();
	
	public static boolean getToModeSelection(Activity parent) {
		
		if(Mode.getActiveMode() == Mode.therapy) {
			new AlertDialog.Builder(parent)
				.setMessage(R.string.question_gotoareaormode)
				.setNegativeButton(R.string.nothing, null)
				.setNeutralButton(R.string.area, b.new GoAreaListener(parent))
				.setPositiveButton(R.string.mode, b.new GoModeListener(parent))
				.show();
		}
		else {
			new AlertDialog.Builder(parent)
	    		.setMessage(R.string.question_gotomode)
	    		.setNegativeButton(R.string.no, null)
	    		.setPositiveButton(R.string.yes, b.new GoModeListener(parent))
	    		.show();
		}

		return false;
	}
	
	public static void onBackPressed(Activity parent) {
		
		//parent.finish();
	}
	
	private class GoAreaListener implements android.content.DialogInterface.OnClickListener {
		
		Activity parent;
		
		public GoAreaListener(Activity parent) {
			this.parent = parent;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Intent intent = new Intent(parent, TherapyAreaActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			parent.startActivity(intent);
		}
	}
	
	private class GoModeListener implements android.content.DialogInterface.OnClickListener {
		
		Activity parent;
		
		public GoModeListener(Activity parent) {
			this.parent = parent;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			Intent intent = new Intent(parent, ModeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			parent.startActivity(intent);
		}
	}
}
