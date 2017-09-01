package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.R;


/**
 * Created by User on 8/28/2017.
 */

public class FundInfoFragment extends Fragment {
    ImageView submit;
    EditText BenificiaryaccNo,confirmAccNo ,deposoitAmt,agentPin ,fundRemarks;
    String BenificiaryaccNoTxt,confirmAccNoTxt ,deposoitAmtTxt,agentPinTxt ,fundRemarksTxt;
    public FundInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fund_info, container, false);
        submit = (ImageView)view.findViewById(R.id.submit);

        BenificiaryaccNo=(EditText)view.findViewById(R.id.BenificiaryaccNo) ;
        confirmAccNo=(EditText)view.findViewById(R.id.confirmAccNo) ;
        deposoitAmt=(EditText)view.findViewById(R.id.deposoitAmt) ;
        agentPin=(EditText)view.findViewById(R.id.agentPin) ;
        fundRemarks=(EditText)view.findViewById(R.id.fundRemarks) ;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      /*          Intent intent= new Intent(getContext(), DialogActivity.class);
                getActivity().startActivity(intent);*/

                BenificiaryaccNoTxt=BenificiaryaccNo.getText().toString();
                confirmAccNoTxt=confirmAccNo.getText().toString();
                deposoitAmtTxt=deposoitAmt.getText().toString();
                agentPinTxt=agentPin.getText().toString();
                fundRemarksTxt=fundRemarks.getText().toString();

                if (BenificiaryaccNoTxt.equals("")|confirmAccNoTxt.equals("")|deposoitAmtTxt.equals("")|
                agentPinTxt.equals("")|fundRemarksTxt.equals("")){
                    getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
                }else {
                    Bundle args = new Bundle();
                    args.putString("goto","ok");
                    Fragment fragment= new FundFingerCheckFragment();
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                }
            }
        });
        return view;
    }
}
