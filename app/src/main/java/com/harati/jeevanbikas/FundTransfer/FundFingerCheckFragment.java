package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.CashDeposit.CashDepositFormFragment;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundFingerCheckFragment extends Fragment {
    Bundle bundle;

    ImageView fingerPrint;
    public FundFingerCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle =getArguments();
        View view= inflater.inflate(R.layout.fragment_fund_finger_check, container, false);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        final String value = getArguments().getString("goto");
        fingerPrint.setOnClickListener(view1 -> {
            assert value != null;
            if (value.equals("info")){
                Fragment fragment= new FundInfoFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
            }else if (value.equals("ok")){
                Intent intent= new Intent(getContext(), DialogActivity.class);
                getActivity().startActivity(intent);
            }

        });
        return  view;
    }

}
