package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnquiryUserDetails extends Fragment implements View.OnClickListener {
    ImageView enquiry_submit, enquiry_cross, enquiryUserPhoto;
    TextView memberIdnnumber, branchName, accNoDetails,enquiryUserName;
    String bundleRespone;

    public EnquiryUserDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquiry_user_details, container, false);

        Bundle bundle = getArguments();
//        bundleRespone = bundle.getString("response");
        enquiry_submit = (ImageView) view.findViewById(R.id.enquiry_submit);
        enquiryUserPhoto = (ImageView) view.findViewById(R.id.enquiryUserPhoto);
        enquiry_cross = (ImageView) view.findViewById(R.id.enquiry_cross);
        enquiry_submit.setOnClickListener(this);
        enquiry_cross.setOnClickListener(this);


        memberIdnnumber = (TextView) view.findViewById(R.id.memberIdnnumber);
        branchName = (TextView) view.findViewById(R.id.branchName);
        accNoDetails = (TextView) view.findViewById(R.id.accNoDetails);
        enquiryUserName = (TextView) view.findViewById(R.id.enquiryUserName);


        return view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.enquiry_submit:
                Fragment fragment = new FingerPrintFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.enquiry_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }

    private void setResponse() {
        try {
            JSONObject jsonObject = new JSONObject(bundleRespone);
            String name = jsonObject.getString("name");
            String code = jsonObject.getString("code");
            String branch = jsonObject.getString("branch");

            String photoUrl = jsonObject.getString("photo");

            branchName.setText(branch);
            accNoDetails.setText(code);
            enquiryUserName.setText(name);

            Picasso.with(getContext())
                    .load(photoUrl)
                    .centerCrop()
                    .into(enquiryUserPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
