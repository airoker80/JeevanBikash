package com.harati.jeevanbikas.RechargeTopup;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.jeevanbikas.Adapter.RechargePagerAdapter;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.RechargeTopup.Adapter.MobileRechargeAdapter;
import com.harati.jeevanbikas.RechargeTopup.Model.MobileTopupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MobileRechargeFragment extends Fragment {

    RecyclerView topupRv;
    List<MobileTopupModel> mobileTopupModels = new ArrayList<>();

    public MobileRechargeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mobile_recharge, container, false);
        topupRv=(RecyclerView)view.findViewById(R.id.topupRv);
        mobileTopupModels.add(new MobileTopupModel("",R.drawable.ntc));
        mobileTopupModels.add(new MobileTopupModel("",R.drawable.ncell));
        mobileTopupModels.add(new MobileTopupModel("",R.drawable.smartcell));
        mobileTopupModels.add(new MobileTopupModel("",R.drawable.utl));
        MobileRechargeAdapter mobileRechargeAdapter = new MobileRechargeAdapter(getContext(),mobileTopupModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        topupRv.setLayoutManager(gridLayoutManager);
        topupRv.setAdapter(mobileRechargeAdapter);


        return  view;


    }

}
