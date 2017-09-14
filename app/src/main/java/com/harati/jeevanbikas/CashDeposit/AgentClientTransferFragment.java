package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentClientTransferFragment extends Fragment {

    ImageView agent_client_tick;

    public AgentClientTransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);
        agent_client_tick=(ImageView)view.findViewById(R.id.agent_client_tick);
        agent_client_tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DialogActivity.class));
            }
        });
        return  view;
    }

}
