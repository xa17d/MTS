package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import moco.android.mtsdevice.handler.Bodypart;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import at.mts.entity.Bodyparts;

public class BodyViewFrontActivity extends Activity {

	private final static String BODYFRONTPATH = "/storage/sdcard0/MTS/body_front.png";
	
	private ImageView bodyFrontView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.body_view_front);
		
		Bodypart.setBodypart(null);
		
		initComponent();
	}
	
	public void openHead(View v) {
		
		Bodypart.setBodypart(Bodypart.HEAD);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openChest(View v) {
		
		Bodypart.setBodypart(Bodypart.CHEST);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openAbdomen(View v) {
		
		Bodypart.setBodypart(Bodypart.ABDOMEN);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openLeftArm(View v) {
		
		Bodypart.setBodypart(Bodypart.LEFT_ARM);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openRightArm(View v) {
		
		Bodypart.setBodypart(Bodypart.RIGHT_ARM);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openLeftLeg(View v) {
		
		Bodypart.setBodypart(Bodypart.LEFT_LEG);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	public void openRightLeg(View v) {
		
		Bodypart.setBodypart(Bodypart.RIGHT_LEG);
		
		Intent intent = new Intent(this, BodySelectionList.class);
		startActivity(intent);
	}
	
	
	private void initComponent() {
		
		bodyFrontView = (ImageView)findViewById(R.id.imageViewBodyFront);		
		Bitmap bodyFrontBitmap = BitmapFactory.decodeFile(BODYFRONTPATH);
		
		if(bodyFrontBitmap != null)
			bodyFrontView.setImageBitmap(bodyFrontBitmap);
	}
}
