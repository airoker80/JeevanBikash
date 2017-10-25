package com.harati.jeevanbikas.LoanDemand;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.R;


/**
 * Created by User on 8/28/2017.
 */

public class FingerPrintLoanFragment extends Fragment {
    ImageView fingerPrint;
    Bundle bundle;

    public FingerPrintLoanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_fingerprint_loan, container, false);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        fingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= new LoanDetailFragment();
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
            }
        });
        return view;
    }



}
