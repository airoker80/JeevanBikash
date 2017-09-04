package com.harati.jeevanbikas.CashWithDrawl;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.VolleyPackage.VolleyRequestHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 8/28/2017.
 */

public class CashwithdrawlFragment extends Fragment {
    ImageView submit;
    EditText withdrawlAmount,agentPin ,withdrawlRemark;
    String withdrawlAmountTxt,agentPinTxt ,withdrawlRemarkTxt;
    List<HelperListModelClass> helperListModelClassList =new ArrayList<HelperListModelClass>();
    public CashwithdrawlFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cashwithdrawl, container, false);
        submit = (ImageView)view.findViewById(R.id.submit);
        withdrawlAmount=(EditText)view.findViewById(R.id.withdrawlAmount);
        agentPin=(EditText)view.findViewById(R.id.agentPin);
        withdrawlRemark=(EditText)view.findViewById(R.id.withdrawlRemark);
        submit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                withdrawlAmountTxt=withdrawlAmount.getText().toString();
                agentPinTxt=agentPin.getText().toString();
                withdrawlRemarkTxt=withdrawlRemark.getText().toString();
                if (withdrawlAmountTxt.equals("")|agentPinTxt.equals("")|withdrawlRemarkTxt.equals("")){
                    getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
                }else {

/*                    VolleyRequestHandler volleyRequestHandler = new VolleyRequestHandler();
                    helperListModelClassList.add(new HelperListModelClass("withdrawlAmount",""));
                    helperListModelClassList.add(new HelperListModelClass("agentPin",""));
                    helperListModelClassList.add(new HelperListModelClass("withdrawlRemark",""));

                    String response="";
                    try {
                         response = volleyRequestHandler.makePostRequest("url",helperListModelClassList);
                        Log.d("respones",response);
                    }catch (Exception e){
                        e.printStackTrace();
                    }*/


                        Intent intent= new Intent(getContext(), DialogActivity.class);
                        getActivity().startActivity(intent);

                }
            }
        });
        return view;
    }
}
