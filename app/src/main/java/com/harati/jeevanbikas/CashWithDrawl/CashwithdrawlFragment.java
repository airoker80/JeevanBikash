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
import android.widget.TextView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.VolleyPackage.VolleyRequestHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/28/2017.
 */

public class CashwithdrawlFragment extends Fragment {

    ApiInterface apiInterface;
    SessionHandler sessionHandler ;

    ImageView submit;
    EditText withdrawlAmount,agentPin ,withdrawlRemark;
    String withdrawlAmountTxt,agentPinTxt ,withdrawlRemarkTxt;

    TextView memberId,branch ,accNo,withdrawlTxt,at_pin ,remarks_txt;
    List<HelperListModelClass> helperListModelClassList =new ArrayList<HelperListModelClass>();
    public CashwithdrawlFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        apiInterface= RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());

        View view= inflater.inflate(R.layout.fragment_cashwithdrawl, container, false);
        submit = (ImageView)view.findViewById(R.id.submit);
        withdrawlAmount=(EditText)view.findViewById(R.id.withdrawlAmount);
        agentPin=(EditText)view.findViewById(R.id.agentPin);
        withdrawlRemark=(EditText)view.findViewById(R.id.withdrawlRemark);


        memberId=(TextView) view.findViewById(R.id.memberId);
        branch=(TextView) view.findViewById(R.id.branch);
        accNo=(TextView) view.findViewById(R.id.accNo);
        withdrawlTxt=(TextView) view.findViewById(R.id.withdrawlTxt);
        at_pin=(TextView) view.findViewById(R.id.at_pin);
        remarks_txt=(TextView) view.findViewById(R.id.remarks_txt);


        memberId.setTypeface(MainActivity.centuryGothic);
        branch.setTypeface(MainActivity.centuryGothic);
        accNo.setTypeface(MainActivity.centuryGothic);
        withdrawlTxt.setTypeface(MainActivity.centuryGothic);
        at_pin.setTypeface(MainActivity.centuryGothic);
        remarks_txt.setTypeface(MainActivity.centuryGothic);


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


                    /*sendWithdrawequest()*/
                        Intent intent= new Intent(getContext(), DialogActivity.class);
                        getActivity().startActivity(intent);

                }
            }
        });
        return view;
    }

    private void sendWithdrawequest(){
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode","M0670001");
            jsonObject.put("finger","1234");
            jsonObject.put("amount","1234");
            jsonObject.put("agentpin","1234");
            jsonObject.put("remark","testing");
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<String> call = apiInterface.sendWithdrawRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
