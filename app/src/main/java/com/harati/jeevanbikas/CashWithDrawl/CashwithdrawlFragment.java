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
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;
import com.harati.jeevanbikas.VolleyPackage.VolleyRequestHandler;
import com.squareup.picasso.Picasso;

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

    String code,name,office ,photo;


    ImageView submit;
    EditText withdrawlAmount,agentPin ,withdrawlRemark;
    String withdrawlAmountTxt,agentPinTxt ,withdrawlRemarkTxt;
    ImageView imgUser;

    TextView memberId,branch ,accNo,withdrawlTxt,at_pin ,remarks_txt,memberIdnnumber,branchName,nameTxt;
    List<HelperListModelClass> helperListModelClassList =new ArrayList<HelperListModelClass>();
    public CashwithdrawlFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");
        apiInterface= RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());

        View view= inflater.inflate(R.layout.fragment_cashwithdrawl, container, false);
        submit = (ImageView)view.findViewById(R.id.submit);
        withdrawlAmount=(EditText)view.findViewById(R.id.withdrawlAmount);
        agentPin=(EditText)view.findViewById(R.id.agentPin);
        withdrawlRemark=(EditText)view.findViewById(R.id.withdrawlRemark);
        imgUser=(ImageView) view.findViewById(R.id.imgUser);


        memberId=(TextView) view.findViewById(R.id.memberId);
        branch=(TextView) view.findViewById(R.id.branch);
        accNo=(TextView) view.findViewById(R.id.accNo);
        withdrawlTxt=(TextView) view.findViewById(R.id.withdrawlTxt);
        at_pin=(TextView) view.findViewById(R.id.at_pin);
        remarks_txt=(TextView) view.findViewById(R.id.remarks_txt);


        branchName=(TextView) view.findViewById(R.id.branchName);
        nameTxt=(TextView) view.findViewById(R.id.name);
        memberIdnnumber=(TextView) view.findViewById(R.id.memberIdnnumber);


        memberId.setTypeface(MainActivity.centuryGothic);
        branch.setTypeface(MainActivity.centuryGothic);
        accNo.setTypeface(MainActivity.centuryGothic);
        withdrawlTxt.setTypeface(MainActivity.centuryGothic);
        at_pin.setTypeface(MainActivity.centuryGothic);
        remarks_txt.setTypeface(MainActivity.centuryGothic);

        memberIdnnumber.setText(code);
        branchName.setText(office);
        nameTxt.setText(name);

        Picasso.with(getContext()).load(photo).into(imgUser);


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


                        sendWithdrawequest(withdrawlAmount.getText().toString(),agentPin.getText().toString(),withdrawlRemark.getText().toString());
/*                        Intent intent= new Intent(getContext(), DialogActivity.class);
                        getActivity().startActivity(intent);*/

                }
            }
        });
        return view;
    }

    private void sendWithdrawequest(String wa,String ap,String wr){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",code);
            jsonObject.put("finger","1234");
            jsonObject.put("amount",wa);
            jsonObject.put("agentpin",ap);
            jsonObject.put("remark",wr);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (response.isSuccessful()){
                    String message = response.body().getMessage();
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg",message);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<WithDrawlResponse> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });
    }
}
