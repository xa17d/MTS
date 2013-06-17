package moco.android.mtsdevice.therapy;

import moco.android.mtsdevice.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class BodyViewActivity extends Activity {

	private final static String BODYFRONTPATH = "/storage/sdcard0/MTS/body_front.png";
	private final static String BODYBACKPATH = "/storage/sdcard0/MTS/body_back.png";
	
	private ImageView bodyFrontView;
	private ImageView bodyBackView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.body_view);
		
		initComponent();
	}
	
	
	private void initComponent() {
		
		bodyFrontView = (ImageView)findViewById(R.id.imageViewBodyFront);
		//bodyBackView = (ImageView)findViewById(R.id.imageViewBodyBack);
		
		Bitmap bodyFrontBitmap = BitmapFactory.decodeFile(BODYFRONTPATH);
		Bitmap bodyBackBitmap = BitmapFactory.decodeFile(BODYBACKPATH);
		
		if(bodyFrontBitmap != null)
			bodyFrontView.setImageBitmap(bodyFrontBitmap);
		
		if(bodyBackBitmap != null)
			bodyBackView.setImageBitmap(bodyBackBitmap);
	}
}
