package com.harati.jeevanbikas.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.harati.jeevanbikas.UtilityPackage.Electricity;

/**
 * Created by Sameer on 8/29/2017.
 */

public class UtilityPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    /*private String titles[] = new String[] { "Recharge Code","Use Recharge Code", "Generate Recharge Code" };*/

    private String titles[]= new String[]{"Electricity", "Water"};
//    private String titles[] = new String[] {"Summary Report"};


    // The key to this is to return a SpannableString,
    // containing your icon in an ImageSpan, from your PagerAdapter's getPageTitle(position) method:


    Context context;

    public UtilityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Fragment fragment1 = new Electricity();
                return fragment1;
            case 1:
                Fragment fragment2 = new Electricity();
                return fragment2;
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        return titles[position];

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
