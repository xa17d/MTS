package moco.android.mtsdevice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TestActivity extends Activity {
	
	private static String testText = "Fehler";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_layout);
		
		TextView text = (TextView)findViewById(R.id.testText);
		text.setText(testText);
	}
	
	public static void setTestText(String text) { testText = text; }
}
