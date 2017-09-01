package com.harati.jeevanbikas.RechargeTopup;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.jeevanbikas.Adapter.RechargePagerAdapter;
import com.harati.jeevanbikas.R;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeSlidingFragment extends Fragment {

    ViewPager mViewPager;
    RechargePagerAdapter rechargePagerAdapter;
    AppCompatActivity mActivity;
    public RechargeSlidingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recharge_sliding, container, false);
        setHasOptionsMenu(true);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        rechargePagerAdapter=new RechargePagerAdapter(getChildFragmentManager(),getActivity());
        mViewPager.setAdapter(rechargePagerAdapter);
        TabLayout tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
/*
        tab_layout.addTab(tab_layout.newTab().setText("MY PROGRESS"));
        tab_layout.addTab(tab_layout.newTab().setText("GLOBAL RANKING"));
*/

        tab_layout.setTabGravity( TabLayout.GRAVITY_FILL);
        return  view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {

            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {

            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

}
