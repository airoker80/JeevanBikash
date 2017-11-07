package com.harati.jeevanbikas.ActivityLogPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.harati.jeevanbikas.ActivityLogPackage.dailyLogs.DailyLogDetailsFragment;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogListFragment extends Fragment implements View.OnClickListener {


    LinearLayout rt_log_ll,up_log_ll,ft_log_ll,be_log_ll,ne_log_ll,ld_log_ll,cw_log_ll,cd_log_ll,
            kyc_log_ll,dl_log_ll;



    public LogListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_log_list, container, false);

        rt_log_ll=(LinearLayout)view.findViewById(R.id.rt_log_ll);
        up_log_ll=(LinearLayout)view.findViewById(R.id.up_log_ll);
        ft_log_ll=(LinearLayout)view.findViewById(R.id.ft_log_ll);
        be_log_ll=(LinearLayout)view.findViewById(R.id.be_log_ll);
        ne_log_ll=(LinearLayout)view.findViewById(R.id.ne_log_ll);
        ld_log_ll=(LinearLayout)view.findViewById(R.id.ld_log_ll);
        cw_log_ll=(LinearLayout)view.findViewById(R.id.cw_log_ll);
        cd_log_ll=(LinearLayout)view.findViewById(R.id.cd_log_ll);
        kyc_log_ll=(LinearLayout)view.findViewById(R.id.kyc_log_ll);
        dl_log_ll=(LinearLayout)view.findViewById(R.id.dl_log_ll);


        rt_log_ll.setOnClickListener(this);
        up_log_ll.setOnClickListener(this);
        ft_log_ll.setOnClickListener(this);
        be_log_ll.setOnClickListener(this);
        ne_log_ll.setOnClickListener(this);
        ld_log_ll.setOnClickListener(this);
        cw_log_ll.setOnClickListener(this);
        cd_log_ll.setOnClickListener(this);
        kyc_log_ll.setOnClickListener(this);
        dl_log_ll.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        int vId = v.getId();
        switch (vId){
            case R.id.rt_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.up_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.ft_log_ll:
                break;
            case R.id.be_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.ne_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.ld_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.cw_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.cd_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.kyc_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
            case R.id.dl_log_ll:
                fragment = new DailyLogDetailsFragment();
                break;
        }

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.agentLogFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
