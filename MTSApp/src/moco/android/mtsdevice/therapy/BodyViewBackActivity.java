package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Bodypart;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BodyViewBackActivity extends Activity {

	private final static String BODYBACKPATH = "/storage/sdcard0/MTS/body_back.png";
	
	private ImageView bodyBackView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.body_view_back);
		
		Bodypart.setBodypart(null);
		
		initComponent();
	}
	
	public void openNeck(View v) {
		
		Bodypart.setBodypart(Bodypart.NECK);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openBack(View v) {
		
		Bodypart.setBodypart(Bodypart.BACK);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openLeftHand(View v) {
		
		Bodypart.setBodypart(Bodypart.LEFT_HAND);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openRightHand(View v) {
		
		Bodypart.setBodypart(Bodypart.RIGHT_HAND);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}	
	
	private void initComponent() {
		
		bodyBackView = (ImageView)findViewById(R.id.imageViewBodyFront);
		Bitmap bodyBackBitmap = BitmapFactory.decodeFile(BODYBACKPATH);
		
		if(bodyBackBitmap != null)
			bodyBackView.setImageBitmap(bodyBackBitmap);
	}
}
