package com.harati.jeevanbikas.CashWithDrawl;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by User on 8/28/2017.
 */

public class CashwithdrawlFragment extends Fragment {
    ApiSessionHandler apiSessionHandler ;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;

    String code,name,office ,photo;
    Bundle bundle;


    ImageView submit;
    EditText withdrawlAmount,agentPin ,withdrawlRemark,withrwal_mobile,clientPin;
    String withdrawlAmountTxt,agentPinTxt ,withdrawlRemarkTxt;
    ImageView imgUser;

    TextView memberId,branch ,accNo,withdrawlTxt,at_pin ,remarks_txt,memberIdnnumber,branchName,nameTxt;
    List<HelperListModelClass> helperListModelClassList = new ArrayList<>();
    public CashwithdrawlFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();
        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiSessionHandler = new ApiSessionHandler(getContext());
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());

        View view= inflater.inflate(R.layout.fragment_cashwithdrawl, container, false);
        submit = (ImageView)view.findViewById(R.id.submit);
        withdrawlAmount=(EditText)view.findViewById(R.id.withdrawlAmount);
        agentPin=(EditText)view.findViewById(R.id.agentPin);
        clientPin=(EditText)view.findViewById(R.id.clientPin);
        withdrawlRemark=(EditText)view.findViewById(R.id.withdrawlRemark);
        withrwal_mobile=(CGEditText)view.findViewById(R.id.withrwal_mobile);
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

        withrwal_mobile.setText(bundle.getString("phone"));
        Picasso.with(getContext()).load(photo).into(imgUser);


        submit.setOnClickListener(view1 -> {
            withdrawlAmountTxt=withdrawlAmount.getText().toString();
            agentPinTxt=agentPin.getText().toString();
            withdrawlRemarkTxt=withdrawlRemark.getText().toString();
            if (withdrawlAmountTxt.equals("")|agentPinTxt.equals("")|withdrawlRemarkTxt.equals("")|clientPin.getText().toString().equals("")){
                getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
            }else {
                if (Integer.parseInt(withdrawlAmount.getText().toString())==0){
                    Intent intent =new Intent(getContext(), ErrorDialogActivity.class);
                    intent.putExtra("msg","Zero amount cannot be withdrawn");
                    getActivity().startActivity(intent);
                }else {
//                    sendWithdrawequest(withdrawlAmount.getText().toString(),agentPin.getText().toString(),withdrawlRemark.getText().toString());
                    sendOtpForCashDeposit();
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

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(apiSessionHandler.getCASH_WITHDRAW(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    String message = response.body().getMessage();
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg",message);
                    startActivity(intent);
                }else {
                    try {

                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WithDrawlResponse> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });
    }
    private void  sendOtpForCashDeposit(){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",code);
            jsonObject.put("finger",clientPin.getText().toString());
            jsonObject.put("amount",withdrawlAmount.getText().toString());
            jsonObject.put("agentpin",agentPin.getText().toString());
            jsonObject.put("mobile",withrwal_mobile.getText().toString());
            jsonObject.put("remark",withdrawlRemark.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(apiSessionHandler.getWITHDRAW_OTP(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    String message = response.body().getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    bundle.putString("withdraw_amount",withdrawlAmount.getText().toString());
                    bundle.putString("withdraw_pin",agentPin.getText().toString());
                    bundle.putString("withdraw_client_pin",clientPin.getText().toString());
                    bundle.putString("withdraw_remarks",withdrawlRemark.getText().toString());
                    bundle.putString("withdraw_mobile",withrwal_mobile.getText().toString());

                    Fragment fragment = new WithdrawlTransactionFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                }else {
                    try {

                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                        if (jsonObject.getString("message").equals("Sorry Invalid Agent Pincode...")){
                            agentPin.setError("Invalid Pincode");
                        }else if (jsonObject.getString("message").equals("Member Authentication failed...")){
                            clientPin.setError("Invalid Pincode");
                        }else if (jsonObject.getString("message").equals("Member Mobile No. is not registered...")){
                            withrwal_mobile.setError("Invalid Pincode");
                        }
                    } catch (Exception e) {
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",("data mistake"));
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<WithDrawlResponse> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });
    }
}
