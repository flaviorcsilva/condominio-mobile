package br.org.iupi.condominio.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import br.org.iupi.condominio.view.LeituraConsumoFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new LeituraConsumoFragment();
		case 1:
			// Games fragment activity
			return new LeituraConsumoFragment();
		case 2:
			// Movies fragment activity
			return new LeituraConsumoFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
