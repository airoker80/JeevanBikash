package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFundDetailsFragment extends Fragment implements View.OnClickListener {


    Fragment fragment;
    CenturyGothicTextView fundName,fundmemberId,fundOffice;
    ImageView fund_tick,fund_cross;
    Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_fund_details, container, false);
        bundle = getArguments();

        fundName =(CenturyGothicTextView)view.findViewById(R.id.fundName);
        fundmemberId =(CenturyGothicTextView)view.findViewById(R.id.fundmemberId);
        fundOffice =(CenturyGothicTextView)view.findViewById(R.id.fundOffice);

        if (bundle.get("goto").equals("beif")){
            fundName.setText(bundle.getString("name"));
            fundmemberId.setText(bundle.getString("code"));
            fundOffice.setText(bundle.getString("office"));
        }else if (bundle.get("goto").equals("info")){
            fundName.setText(bundle.getString("nameBenificiary"));
            fundmemberId.setText(bundle.getString("codeBenificiary"));
            fundOffice.setText(bundle.getString("officeBenificiary"));
        }

        fund_tick=(ImageView)view.findViewById(R.id.fund_tick);
        fund_cross=(ImageView)view.findViewById(R.id.fund_cross);
        fund_tick.setOnClickListener(this);
        fund_cross.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case R.id.fund_tick:
                if (bundle.get("goto").equals("beif")){
                    fragment = new FundBeneficiarySearchFragment();
                }else if (bundle.get("goto").equals("info")){
                    fragment = new FundInfoFragment();
                }

//                Fragment fragment = new FundFingerCheckFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.fund_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }


}
