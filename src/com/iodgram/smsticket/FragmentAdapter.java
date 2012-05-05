package com.iodgram.smsticket;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.TitleProvider;

public class FragmentAdapter extends FragmentPagerAdapter implements
			TitleProvider {

		private List<Fragment> mFragments = new ArrayList<Fragment>();
		private List<String> mTitles = new ArrayList<String>();

		public FragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		public void addPage(String title, Fragment fragment) {
			if (fragment == null) {
				throw new NullPointerException("FRAGMENT IS NULL!!");
			}

			mFragments.add(fragment);
			mTitles.add(title);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public String getTitle(int position) {
			return mTitles.get(position % mFragments.size()).toUpperCase();
		}
	}