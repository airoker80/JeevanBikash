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
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundDetailsFragment extends Fragment implements View.OnClickListener {
    ImageView fund_tick,fund_cross;

    public FundDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fund_details, container, false);
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
                Fragment fragment = new FundFingerCheckFragment();
                Bundle args = new Bundle();
                args.putString("goto","info");
                fragment.setArguments(args);
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
