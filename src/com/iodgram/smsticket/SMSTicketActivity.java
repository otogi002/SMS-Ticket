package com.iodgram.smsticket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.iodgram.smsticket.generators.GoteborgActivity;
import com.iodgram.smsticket.generators.StockholmActivity;
import com.iodgram.smsticket.generators.UmeaActivity;
import com.iodgram.smsticket.generators.UppsalaActivity;

public class SMSTicketActivity extends SherlockListActivity {
	@SuppressWarnings("rawtypes")
	Class[] classes = {
		StockholmActivity.class,
		UppsalaActivity.class,
		GoteborgActivity.class,
		// JonkopingActivity.class,
		UmeaActivity.class,
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ActionBar ab = getSupportActionBar();
		ab.setTitle(getString(R.string.app_name) + " " + Utils.getVersion(this));

		// Cities
		Integer[] images = new Integer[] {
			R.drawable.stockholm,
			R.drawable.uppsala,
			R.drawable.goteborg,
			// R.drawable.jonkoping,
			R.drawable.umea,
		};

		ImageAdapter adapter = new ImageAdapter(this, images);
		setListAdapter(adapter);

		// Non-strict time expiration
		java.util.Date time = new java.util.Date();
		if (time.getTime() / 1000 > 1336168800) { // 5 maj 2012
			final Activity activity = this;

			new AlertDialog.Builder(this)
					.setTitle("Out of Date")
					.setMessage(
							"This application is out of date - please update. Terminating.")
					.setNeutralButton("OK!",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									activity.finish();
								}
							}).show();
			return;
		} else if (time.getTime() / 1000 > 1335823200) { // 1 maj 2012
			new AlertDialog.Builder(this)
					.setTitle("Out of Date")
					.setMessage(
							"This application is getting outdated and will stop working in a few days. Please update.")
					.setNeutralButton("OK!",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									init();
								}
							}).show();
		} else {
			init();
		}
	}

	public void init() {
		int selected_city = getPreferences(0).getInt("selected_city", -1);
		if (selected_city >= 0 && selected_city < classes.length) {
			Intent intent = new Intent(this, classes[selected_city]);
			startActivityForResult(intent, 0);
		}
	}

	public void onListItemClick(ListView lv, View v, int pos, long id) {
		if (pos >= classes.length) {
			return;
		}

		Editor prefs = getPreferences(0).edit();
		prefs.putInt("selected_city", pos);
		prefs.commit();

		Intent intent = new Intent(this, classes[pos]);
		// startActivityForResult(intent, 0);
		startActivity(intent);
	}

	public void onActivityResult(int request, int result, Intent data) {
		switch (request) {
		case 0:
			switch (result) {
			case 1:
				break;
			default:
				finish();
			}
			break;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuitem_overlay:
			Intent overlayIntent = new Intent(this, OverlayActivity.class);
			startActivity(overlayIntent);
			return true;
		case R.id.menuitem_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}
}