package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class CashDepositFormFragment extends Fragment {
    ImageView submit;
    EditText withdrawlAmount,agentPin ,withdrawlRemark,accNo;
    String withdrawlAmountTxt,agentPinTxt ,withdrawlRemarkTxt,accNoTxt;
    public CashDepositFormFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cashdeposit, container, false);

        withdrawlAmount=(EditText)view.findViewById(R.id.withdrawlAmount);
        agentPin=(EditText)view.findViewById(R.id.agentPin);
        withdrawlRemark=(EditText)view.findViewById(R.id.withdrawlRemark);
        accNo=(EditText)view.findViewById(R.id.accNo);

        submit = (ImageView)view.findViewById(R.id.submit);
        submit.setOnClickListener(view1 -> {
            withdrawlAmountTxt=withdrawlAmount.getText().toString();
            agentPinTxt=agentPin.getText().toString();
            withdrawlRemarkTxt=withdrawlRemark.getText().toString();
            accNoTxt=accNo.getText().toString();
            if (withdrawlAmountTxt.equals("")|agentPinTxt.equals("")|withdrawlRemarkTxt.equals("")|accNo.getText().toString().equals("")){
                getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
            }else {
                Intent intent= new Intent(getContext(), DialogActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
