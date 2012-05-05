package com.iodgram.smsticket.tickets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.os.Bundle;

import com.iodgram.smsticket.Utils;

public class UppsalaTicket extends Ticket {
	private int[] gNumbers = new int[4];

	final char[] aChars = { '+', '-', '/', '*' };

	protected String mSender;

	protected String mPriceType, mValidDate, mValidTime, mTime, mDay, sMonth,
			mCode = "";
	protected int mPrice;

	protected boolean mReduced;

	@Override
	public String getMessage() {
		return "" + gNumbers[1] + gNumbers[2]
				+ aChars[(int) Math.floor(Math.random() * 4)] + " "
				+ mPriceType + " UPPSALA Stadstrafik. " + "Giltig till "
				+ mValidDate + " kl. " + mValidTime + ". " + "Pris: " + mPrice
				+ " kr (inkl. 6% moms) " + mTime + sMonth + mDay + mCode;
	}

	@Override
	public String getSender() {
		return mSender;
	}

	@Override
	public String getMessageOut() {
		return "U" + (mReduced ? "U" : "V");
	}

	@Override
	public String getNumberOut() {
		return "72472";
	}

	@Override
	public void create(Bundle data) {
		mSender = generateSenderNumber();

		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		Random rand = new Random();

		mReduced = data.getBoolean("price_reduced");

		if (mReduced) {
			mPriceType = "UNGDOM";
			mPrice = 15;
		} else {
			mPriceType = "VUXEN";
			mPrice = 25;
		}

		// Date and time
		mTime = new SimpleDateFormat("HHmm").format(now.getTime());
		mDay = new SimpleDateFormat("dd").format(now.getTime());
		
		String month = Utils.gMonths[cal.get(Calendar.MONTH)];
		int randpos = rand.nextInt(month.length());
		sMonth = month.substring(randpos, randpos+1);
		if (rand.nextInt(2) == 1) // 50%
			sMonth = sMonth.toUpperCase();

		cal.add(Calendar.MINUTE, 90);
		mValidTime = new SimpleDateFormat("HH:mm").format(cal.getTime());
		mValidDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

		for (int i = 0; i < 2; i++) {
			mCode += Utils.gAlphabet[(int) Math.floor(Math.random() * 26)]
					.toLowerCase();
		}
		for (int i = 0; i < 4; i++) {
			mCode += Math.round(Math.random() * 9);
		}
		mCode += Utils.gAlphabet[(int) Math.floor(Math.random() * 26)];
		for (int i = 0; i < 6; i++) {
			mCode += Math.round(Math.random() * 9);
		}
	}

	private String generateSenderNumber() {
		String number = getNumberOut();

		for (int i = 0; i < 3; i++) {
			gNumbers[i] = (int) Math.round(Math.random() * 9);
			number += gNumbers[i];
		}

		return number;
	}
}