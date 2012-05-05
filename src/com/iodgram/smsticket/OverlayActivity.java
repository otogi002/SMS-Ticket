package com.iodgram.smsticket;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OverlayActivity extends Activity {
	int num_overlays = 9;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.overlay);
		
		final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		final Editor editor = prefs.edit();
		
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		
		// Create all radio buttons
		for (int i = 1; i <= num_overlays; i++) {
			RadioButton rb = new RadioButton(this);
			rb.setText("Broken LCD "+ i);
			
			rg.addView(rb);
		}
		
		// Check the radio button
		int imageId = prefs.getInt("imageId", 0);
		((RadioButton) rg.getChildAt(imageId)).setChecked(true);
		
		// Check the check box
		boolean bDisplay = prefs.getBoolean("display", false);
		((CheckBox) findViewById(R.id.toggle_overlay)).setChecked(bDisplay);
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int index = -1;

				for (int i = 0; i < num_overlays; i++) {
					if (group.getChildAt(i).getId() == checkedId) {
						index = i;
						break;
					}
				}
				
				Intent overlayService = new Intent(group.getContext(), OverlayService.class);
				
				overlayService.putExtra("imageId", index);
				
				startService(overlayService);
				
				editor.putInt("imageId", index);
				editor.commit();
			}
		});
		
		((CheckBox) findViewById(R.id.toggle_overlay)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Intent overlayService = new Intent(buttonView.getContext(), OverlayService.class);
				
				overlayService.putExtra("display", isChecked);
				
				startService(overlayService);
				
				editor.putBoolean("display", isChecked);
				editor.commit();
			}
		});
	}
}
