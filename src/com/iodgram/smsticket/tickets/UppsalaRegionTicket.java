package com.iodgram.smsticket.tickets;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import android.os.Bundle;

import com.iodgram.smsticket.R;
import com.iodgram.smsticket.Utils;

public class UppsalaRegionTicket extends Ticket {
	private int[] gNumbers = new int[4];

	final char[] aChars = { '+', '-', '/', '*' };

	int iZone;
	boolean bReduced;

	String sSender;

	String sValidDate, sValidTime;
	String sTime, sMonth, sDay;
	String sCode = "";
	String sPriceType, sZoneOut, sZoneType = "UL";
	int mPrice;

	@Override
	public String getMessage() {
		return "" + gNumbers[1] + gNumbers[2]
				+ aChars[(int) Math.floor(Math.random() * 4)] + " "
				+ sPriceType + " " + sZoneType + ". " + "Giltig till "
				+ sValidDate + " kl. " + sValidTime + ". " + "Pris: " + mPrice
				+ " kr (inkl. 6% moms) " + sTime + sMonth + sDay + sCode;
	}

	@Override
	public String getSender() {
		return sSender;
	}

	@Override
	public String getMessageOut() {
		if (iZone == R.id.region_zone_ulsl) {
			return "ULSL" + (bReduced ? "U" : "V");
		} else if (iZone == R.id.region_zone_bike) {
			return "ULCY";
		} else {
			return "UL" + (bReduced ? "U" : "V") + sZoneOut;
		}
	}

	@Override
	public String getNumberOut() {
		return "72472";
	}

	@Override
	public void create(Bundle data) {
		sSender = generateSenderNumber();

		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		Random rand = new Random();

		bReduced = data.getBoolean("price_reduced");
		iZone = data.getInt("zone");

		if (iZone == R.id.region_zone_bike) {
			sPriceType = "CYKEL";
		} else if (bReduced) {
			sPriceType = "UNGDOM";
		} else {
			sPriceType = "VUXEN";
		}

		switch (iZone) {
		case R.id.region_zone_urban:
			sZoneType = "Tätortstrafik";
			mPrice = bReduced ? 12 : 20;
			sZoneOut = "T";
			break;
		case R.id.region_zone_1:
			sZoneType = "UL resa i zon 1";
			mPrice = bReduced ? 27 : 45;
			sZoneOut = "1";
			break;
		case R.id.region_zone_2:
			sZoneType = "UL resa i zon 2";
			mPrice = bReduced ? 27 : 45;
			sZoneOut = "2";
			break;
		case R.id.region_zone_2p:
			sZoneType = "UL resa mellan zon 2 och PLUS";
			mPrice = bReduced ? 54 : 90;
			sZoneOut = "2P";
			break;
		case R.id.region_zone_12:
			sZoneType = "UL resa mellan zon 1 och 2";
			mPrice = bReduced ? 54 : 90;
			sZoneOut = "12";
			break;
		case R.id.region_zone_12p:
			sZoneType = "UL resa mellan zon 1, 2 och PLUS";
			mPrice = bReduced ? 81 : 135;
			sZoneOut = "12P";
			break;
		case R.id.region_zone_p:
			sZoneType = "UL resa i zon PLUS";
			mPrice = bReduced ? 27 : 45;
			sZoneOut = "P";
			break;
		case R.id.region_zone_ulsl:
			sZoneType = "UL/SL";
			mPrice = bReduced ? 60 : 100;
			break;
		case R.id.region_zone_bike:
			sZoneType = "UL";
			mPrice = 45;
			break;
		}

		// Date and time
		sTime = new SimpleDateFormat("HHmm").format(now.getTime());
		sDay = new SimpleDateFormat("dd").format(now.getTime());
		
		String month = Utils.gMonths[cal.get(Calendar.MONTH)];
		int randpos = rand.nextInt(month.length());
		sMonth = month.substring(randpos, randpos+1);
		if (rand.nextInt(2) == 1) // 50%
			sMonth = sMonth.toUpperCase();

		cal.add(Calendar.MINUTE, 90);
		sValidTime = new SimpleDateFormat("HH:mm").format(cal.getTime());
		sValidDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

		for (int i = 0; i < 2; i++) {
			sCode += Utils.gAlphabet[(int) Math.floor(Math.random() * 26)]
					.toLowerCase();
		}
		for (int i = 0; i < 3; i++) {
			sCode += Math.round(Math.random() * 9);
		}
		sCode += Integer.toString((int) (Math.random() * 9))
				+ (char) ((Math.random() * 26) + 65);
		for (int i = 0; i < 6; i++) {
			sCode += Math.round(Math.random() * 9);
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