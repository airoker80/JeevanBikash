package com.harati.jeevanbikas.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.harati.jeevanbikas.RechargeTopup.MobileRechargeFragment;

/**
 * Created by Sameer on 8/29/2017.
 */

public class RechargePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;

    /*private String titles[] = new String[] { "Recharge Code","Use Recharge Code", "Generate Recharge Code" };*/

    private String titles[]= new String[]{"Mobile", "Landline", "Internet/ISP","TV/DTH"};;
//    private String titles[] = new String[] {"Summary Report"};


    // The key to this is to return a SpannableString,
    // containing your icon in an ImageSpan, from your PagerAdapter's getPageTitle(position) method:


    Context context;

    public RechargePagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Fragment fragment1 = new MobileRechargeFragment();
                return fragment1;
            case 1:
                Fragment fragment2 = new MobileRechargeFragment();
                return fragment2;
            case 2:
                Fragment fragment3 = new MobileRechargeFragment();
                return fragment3;
            case 3:
                Fragment fragment4 = new MobileRechargeFragment();
                return fragment4;
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