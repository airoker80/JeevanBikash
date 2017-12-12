package com.harati.jeevanbikas.LoanDemand;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.BalanceEnquiry.FingerPrintFragment;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemandDetailsFragment extends Fragment implements View.OnClickListener {
    ImageView demand_tick,demand_cross;
    Bundle bundle;

    CenturyGothicTextView demDtlname,demandDtlCode, demandDtlBranch;
    private View.OnClickListener mClickListener;

    public DemandDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        bundle = getArguments();

        View view =  inflater.inflate(R.layout.fragment_demand_details, container, false);

        demDtlname=(CenturyGothicTextView)view.findViewById(R.id.demDtlname);
        demandDtlCode=(CenturyGothicTextView)view.findViewById(R.id.demandDtlCode);
        demandDtlBranch=(CenturyGothicTextView)view.findViewById(R.id.demandDtlBranch);

        demDtlname.setText(bundle.getString("name"));
        demandDtlCode.setText(bundle.getString("code"));
        demandDtlBranch.setText(bundle.getString("office"));

        demand_tick=(ImageView)view.findViewById(R.id.demand_tick);
        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);
        demand_tick.setOnClickListener(this);
        demand_cross.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case R.id.demand_tick:
                Fragment fragment = new LoanDetailFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.demand_cross:
                Intent intent =new Intent(getContext(), MainActivity.class);
                intent.putExtra("msg","x");
                startActivity(intent);
                break;
        }
    }
}
