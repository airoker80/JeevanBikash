package com.harati.jeevanbikas.UtilityPackage;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.jeevanbikas.Adapter.UtilityPagerAdapter;
import com.harati.jeevanbikas.R;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 */
public class UtilitySlidingFragment extends Fragment {

    ViewPager mViewPager;
    UtilityPagerAdapter utilityPagerAdapter;
    AppCompatActivity mActivity;
    public UtilitySlidingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("ad","ok");
        View view = inflater.inflate(R.layout.fragment_utility_sliding, container, false);
        setHasOptionsMenu(true);


        mViewPager = (ViewPager) view.findViewById(R.id.utilityViewPager);
        utilityPagerAdapter=new UtilityPagerAdapter(getChildFragmentManager(),getActivity());
        mViewPager.setAdapter(utilityPagerAdapter);
        TabLayout tab_layout = (TabLayout) view.findViewById(R.id.utility_tab_layout);
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
