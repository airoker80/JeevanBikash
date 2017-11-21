package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.CashDeposit.FingerPrintAuthDepositFragment;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundDetailsFragment extends Fragment implements View.OnClickListener {
    CenturyGothicTextView fundName,fundmemberId,fundOffice;
    ImageView fund_tick,fund_cross;
    Bundle bundle;
    public FundDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fund_details, container, false);
        bundle = getArguments();

        fundName =(CenturyGothicTextView)view.findViewById(R.id.fundName);
        fundmemberId =(CenturyGothicTextView)view.findViewById(R.id.fundmemberId);
        fundOffice =(CenturyGothicTextView)view.findViewById(R.id.fundOffice);

        fundName.setText(bundle.getString("name"));
        fundmemberId.setText(bundle.getString("code"));
        fundOffice.setText(bundle.getString("office"));

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
                Fragment fragment = new FundInfoFragment();
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
