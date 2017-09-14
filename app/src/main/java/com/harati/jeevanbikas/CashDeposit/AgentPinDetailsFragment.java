package com.harati.jeevanbikas.CashDeposit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentPinDetailsFragment extends Fragment {
    ImageView agent_tick;

    public AgentPinDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agent_pin_details, container, false);
        agent_tick=(ImageView)view.findViewById(R.id.agent_tick);
        agent_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new AgentClientTransferFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
            }
        });
        return  view;
    }

}
