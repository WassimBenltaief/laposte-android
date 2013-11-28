package com.laposte.tn.business;

import java.util.List;

import com.laposte.tn.activities.FragmentTab1;
import com.laposte.tn.activities.FragmentTab2;
import com.laposte.tn.dialogs.FragmentFactureValidation;
import com.laposte.tn.dialogs.FragmentInfosFacture;
import com.laposte.tn.dialogs.FragmentInfosFacture2;
import com.laposte.tn.dialogs.FragmentInfosFacture3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {


	final int PAGE_COUNT = 4;
	
	
	// On fournit à l'adapter la liste des fragments à afficher
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public Fragment getItem(int arg0) {
		switch (arg0) {

		// Open FragmentTab1.java
		case 0:
			FragmentInfosFacture fragment1 = new FragmentInfosFacture();
			return fragment1;

			// Open FragmentTab2.java
		case 1:
			FragmentInfosFacture2 fragment2 = new FragmentInfosFacture2();
			return fragment2;

			// Open FragmentTab3.java
		case 2:
			FragmentInfosFacture3 fragment3 = new FragmentInfosFacture3();
			return fragment3;

		case 3:
			FragmentFactureValidation fragment4 = new FragmentFactureValidation();
			return fragment4;
		}
		return null;
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

}