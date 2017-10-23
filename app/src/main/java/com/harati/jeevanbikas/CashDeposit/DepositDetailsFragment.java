package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.BalanceEnquiry.FingerPrintFragment;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepositDetailsFragment extends Fragment implements View.OnClickListener{
    ImageView demand_tick,demand_cross,depositUserPhoto;
    String code,name,office ,photo;
    TextView memberIdnnumber, branchName, accNoDetails,enquiryUserName,memberId,branch ,accNo,nameTxt;
    public DepositDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();

        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");

        View view= inflater.inflate(R.layout.fragment_deposit_details, container, false);
        demand_tick=(ImageView)view.findViewById(R.id.demand_tick);
        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);

        nameTxt = (TextView) view.findViewById(R.id.name);
        memberIdnnumber = (TextView) view.findViewById(R.id.memberIdnnumber);
        branchName = (TextView) view.findViewById(R.id.branchName);
        accNoDetails = (TextView) view.findViewById(R.id.accNoDetails);

        depositUserPhoto = (ImageView) view.findViewById(R.id.depositUserPhoto);

        nameTxt.setText(name);
        memberIdnnumber.setText(code);
        branchName.setText(office);


        try {
            Picasso.with(getContext()).load(photo).into(depositUserPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }

        demand_tick.setOnClickListener(this);
        demand_cross.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        switch (vId){
            case R.id.demand_tick:
                Fragment fragment = new FingerPrintAuthDepositFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contentFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.demand_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }
}
