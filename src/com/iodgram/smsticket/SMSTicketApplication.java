package com.iodgram.smsticket;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

//@ReportsCrashes(formKey = "")
public class SMSTicketApplication extends Application {
	public void onCreate() {
		ACRA.init(this);
		super.onCreate();
	}
}
