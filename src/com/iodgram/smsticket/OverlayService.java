package com.iodgram.smsticket;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

public class OverlayService extends Service {
	ImageView image;
	WindowManager wm;
	WindowManager.LayoutParams params;
	
	boolean isDisplayed = false;
	
	int[] images = {
		R.drawable.lcdbroke01,
		R.drawable.lcdbroke02,
		R.drawable.lcdbroke03,
		R.drawable.lcdbroke04,
		R.drawable.lcdbroke05,
		R.drawable.lcdbroke06,
		R.drawable.lcdbroke07,
		R.drawable.lcdbroke08,
		R.drawable.lcdbroke09,
	};
	int imageId = 0;
	
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public void onCreate() {
		image = new ImageView(this);
		image.setImageResource(images[imageId]);
		image.setScaleType(ImageView.ScaleType.FIT_XY);
		
		params = new WindowManager.LayoutParams(
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.WRAP_CONTENT,
			WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
			0,
			PixelFormat.TRANSLUCENT
		);
		params.gravity = Gravity.RIGHT | Gravity.TOP;
		params.screenOrientation = 0x01;
		
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean bShow = isDisplayed;
		
		if (intent.hasExtra("imageId")) {
			int imageId = intent.getExtras().getInt("imageId");
			
			if (imageId >= 0 && imageId <= images.length) {
				setImage(imageId);
			}
		}
		
		if (intent.hasExtra("display")) {
			boolean bDisplay = intent.getExtras().getBoolean("display");
			
			bShow = bDisplay;
		}
		
		if (isDisplayed) {
			hideOverlay();
		}
		
		if (bShow) {	
			showOverlay();
		}
		
		return 0;
	}
	
	private void setImage(int imageId) {
		this.imageId = imageId;
		
		image.setImageResource(images[imageId]);
	}
	
	private void showOverlay() {
		Log.v("OVERLAY", "SHOW!");
		wm.addView(image, params);
		
		isDisplayed = true;
	}
	
	private void hideOverlay() {
		wm.removeView(image);
		
		isDisplayed = false;
	}
}
