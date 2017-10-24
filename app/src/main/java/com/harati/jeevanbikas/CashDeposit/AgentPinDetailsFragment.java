package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentPinDetailsFragment extends Fragment implements View.OnClickListener {
    ImageView agent_tick,demand_cross;
    String code,name,office ,photo,clientPin,clientCode;
    PinEntryEditText agentPin;
    CGEditText deposoitAmt,deposoitRemarks;
    Bundle bundle;

    public AgentPinDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_agent_pin_details, container, false);

        bundle = getArguments();

        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");
        clientPin = bundle.getString("clientPin");
        clientCode = bundle.getString("clientCode");

        agent_tick=(ImageView)view.findViewById(R.id.agent_tick);

        agentPin=(PinEntryEditText)view.findViewById(R.id.agentPin);
        deposoitAmt=(CGEditText) view.findViewById(R.id.deposoitAmt);
        deposoitRemarks=(CGEditText)view.findViewById(R.id.deposoitRemarks);

        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);
        agent_tick.setOnClickListener(this);
        demand_cross.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case  R.id.agent_tick:
                Fragment fragment= new AgentClientTransferFragment();
/*                Bundle args = new Bundle();
                args.putString("code",code);
                args.putString("name",name);
                args.putString("office",office);
//                    args.putString("office",response.body().getCode());
                args.putString("photo",photo);
                args.putString("agentPin",agentPin.getText().toString());
                args.putString("clientCode",clientCode);*/
                bundle.putString("agentPin",agentPin.getText().toString());
                bundle.putString("deposoitAmt",deposoitAmt.getText().toString());
                bundle.putString("deposoitRemarks",deposoitRemarks.getText().toString());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
                break;
            case R.id.demand_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }
}
