package com.harati.jeevanbikas.KycPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KycFormFragment extends Fragment {


    public KycFormFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kyc_form, container, false);
    }

}
