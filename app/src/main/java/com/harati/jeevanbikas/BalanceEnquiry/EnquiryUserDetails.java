package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnquiryUserDetails extends Fragment implements View.OnClickListener{
    ImageView enquiry_submit,enquiry_cross;

    public EnquiryUserDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_enquiry_user_details, container, false);
        enquiry_submit=(ImageView)view.findViewById(R.id.enquiry_submit);
        enquiry_cross=(ImageView)view.findViewById(R.id.enquiry_cross);
        enquiry_submit.setOnClickListener(this);
        enquiry_cross.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case R.id.enquiry_submit:
                Fragment fragment = new FingerPrintFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
                break;
            case R.id.enquiry_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }
}
