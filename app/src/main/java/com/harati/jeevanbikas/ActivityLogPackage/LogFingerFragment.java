package com.harati.jeevanbikas.ActivityLogPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.harati.jeevanbikas.CashDeposit.AgentPinDetailsFragment;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogFingerFragment extends Fragment {

    LinearLayout rt_log_ll,up_log_ll,ft_log_ll,be_log_ll,ne_log_ll,ld_log_ll,cw_log_ll,cd_log_ll,
            kyc_log_ll,dl_log_ll;


    ImageView agent_log_finger;


    public LogFingerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_log_finger, container, false);
        agent_log_finger=(ImageView)view.findViewById(R.id.agent_log_finger);
        agent_log_finger.setOnClickListener(v -> {
            Fragment fragment= new LogListFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.agentLogFrame, fragment);
            transaction.commit();
        });
        return  view;
    }

}
