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
    TextView memberIdnnumber, branchName, accNoDetails,enquiryUserName,memberId,branch ,accNo;
    String code,name,office ,photo;

    public EnquiryUserDetails() {
        // Required empty public constructor
    }
//.setTypeface(MainActivity.centuryGothic);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enquiry_user_details, container, false);

        Bundle bundle = getArguments();

        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");

        enquiry_submit = (ImageView) view.findViewById(R.id.enquiry_submit);
        enquiryUserPhoto = (ImageView) view.findViewById(R.id.enquiryUserPhoto);
        enquiry_cross = (ImageView) view.findViewById(R.id.enquiry_cross);
        enquiry_submit.setOnClickListener(this);
        enquiry_cross.setOnClickListener(this);


        memberId = (TextView) view.findViewById(R.id.memberId);
        branch = (TextView) view.findViewById(R.id.branch);
        accNo = (TextView) view.findViewById(R.id.accNo);
//        enquiryUserName = (TextView) view.findViewById(R.id.enquiryUserName);

        memberIdnnumber = (TextView) view.findViewById(R.id.memberIdnnumber);
        branchName = (TextView) view.findViewById(R.id.branchName);
        accNoDetails = (TextView) view.findViewById(R.id.accNoDetails);
        enquiryUserName = (TextView) view.findViewById(R.id.enquiryUserName);

        memberId.setTypeface(MainActivity.centuryGothic);
        branch.setTypeface(MainActivity.centuryGothic);
        accNo.setTypeface(MainActivity.centuryGothic);
        memberIdnnumber.setTypeface(MainActivity.centuryGothic);
        branchName.setTypeface(MainActivity.centuryGothic);
        accNoDetails.setTypeface(MainActivity.centuryGothic);
        enquiryUserName.setTypeface(MainActivity.centuryGothic);

        enquiryUserName.setText(name);
        memberIdnnumber.setText(code);
        branchName.setText(office);
        accNoDetails.setText(name);

        try {
            Picasso.with(getContext()).load(photo).into(enquiryUserPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId) {
            case R.id.enquiry_submit:
                Fragment fragment = new NewFingerPrintFragment();
                Bundle bundle = new Bundle();
                bundle.putString("memberId",code);
                fragment.setArguments(bundle);
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


}
