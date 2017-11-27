package com.harati.jeevanbikas.CashWithDrawl;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawlTransactionFragment extends Fragment {
    CenturyGothicTextView title;
    ImageView image;
    ImageButton resend_otp;
    public String otpValue="";
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;
    CenturyGothicTextView name,memberIdnnumber,branchName,shownDepositAmt,amountType,sendOtpAgain,cdt_mob_no;
    PinEntryEditText act_otp_tf;
    ImageView agent_client_tick,demand_cross;
    Bundle bundle;
    public WithdrawlTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle =getArguments();
//        new Thread(task1).start();
        Log.d("bundle","==00--+>"+bundle.toString());
        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
        View view= inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);

        act_otp_tf= (PinEntryEditText) view.findViewById(R.id.act_otp_tf);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            Log.e("ad","dasd"+event.toString());
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    confirmBack();
                }
            }
            return false;
        });

        title=(CenturyGothicTextView)view.findViewById(R.id.title);
        name=(CenturyGothicTextView)view.findViewById(R.id.name);
        memberIdnnumber=(CenturyGothicTextView)view.findViewById(R.id.memberIdnnumber);
        branchName=(CenturyGothicTextView)view.findViewById(R.id.branchName);
        shownDepositAmt=(CenturyGothicTextView)view.findViewById(R.id.shownDepositAmt);
        amountType=(CenturyGothicTextView)view.findViewById(R.id.amountType);
        sendOtpAgain=(CenturyGothicTextView)view.findViewById(R.id.sendOtpAgain);
        cdt_mob_no=(CenturyGothicTextView)view.findViewById(R.id.cdt_mob_no);

        image=(ImageView) view.findViewById(R.id.image);

        image.setOnClickListener(view1 -> confirmBack());


        name.setText(bundle.getString("name"));
        memberIdnnumber.setText(bundle.getString("code"));
        branchName.setText(bundle.getString("office"));
        cdt_mob_no.setText(bundle.getString("withdraw_mobile"));
        shownDepositAmt.setText(getResources().getString(R.string.currency_np)+" "+bundle.getString("withdraw_amount")+".00");

        amountType.setText("Withdrawl Amount");

//        getOtpValue();
//        sendOtpForCashDeposit();
        agent_client_tick=(ImageView)view.findViewById(R.id.agent_client_tick);
        resend_otp=(ImageButton) view.findViewById(R.id.resend_otp);
        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);

        resend_otp.setOnClickListener(view1 -> {
            if (JeevanBikashConfig.BASE_URL1.equals("1")){
                sendOtpForCashDeposit();
            }else {
                Toast.makeText(getContext(), "cannot send OTP until 2mins", Toast.LENGTH_SHORT).show();
            }
        });

        title.setText("Cash Withdrawl");

        agent_client_tick.setOnClickListener(v -> {
            if (act_otp_tf.getText().toString().equals("")){
                act_otp_tf.setError("Please Enter Otp First");
            }else {
                sendWithdrawequest(bundle.getString("withdraw_amount"),bundle.getString("withdraw_pin"),bundle.getString("withdraw_remarks"));
            }

//                startActivity(new Intent(getContext(), DialogActivity.class))
        });
        demand_cross.setOnClickListener(view1 -> {startActivity(new Intent(getContext(), MainActivity.class));});

        sendOtpAgain.setOnClickListener(v -> {
            final AlertDialog builder = new AlertDialog.Builder(getContext())
                    .setPositiveButton("OK", null)
                    .setNegativeButton("CANCEL", null)
                    .setTitle("Send Otp request Again?")
                    .create();



            builder.setOnShowListener(dialog -> {

                final Button btnAccept = builder.getButton(
                        AlertDialog.BUTTON_POSITIVE);

                btnAccept.setOnClickListener(v1 -> {
                    if (JeevanBikashConfig.BASE_URL1.equals("1")){
                        sendOtpForCashDeposit();
                    }else {
                        Toast.makeText(getContext(), "cannot send OTP until 2mins", Toast.LENGTH_SHORT).show();
                    }

                    builder.dismiss();

                });

                final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

                btnDecline.setOnClickListener(v1 -> builder.dismiss()
                );
            });
            builder.show();
        });

        return  view;



    }

    private void sendWithdrawequest(String wa,String ap,String wr){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.get("code"));
            jsonObject.put("finger",bundle.get("withdraw_client_pin"));
            jsonObject.put("amount",wa);
            jsonObject.put("agentpin",ap);
            jsonObject.put("remark",wr);
            jsonObject.put("otp",act_otp_tf.getText().toString());
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
            jsonObject.put("membercode",bundle.get("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",bundle.getString("withdraw_amount"));
            jsonObject.put("agentpin",bundle.getString("withdraw_pin"));
            jsonObject.put("mobile",bundle.get("withdraw_mobile"));
            jsonObject.put("remark",bundle.getString("withdraw_remarks"));
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
                    JeevanBikashConfig.BASE_URL1="2";
                    new Thread(task1).start();
                    String message = response.body().getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                    getOtpValue();
                }else {
                    try {
                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
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
    Runnable task1 = () -> {
        try {
            sleep(2*60*1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            JeevanBikashConfig.BASE_URL1="1";
            Log.e("baeUrl","dad"+JeevanBikashConfig.BASE_URL1);
        }
    };

    void confirmBack(){
        Log.e("backpressed","bp");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ask_permission,null);
        TextView askPermission = (TextView)view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to go back??");
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want to go back?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                getActivity().onBackPressed();
                Log.e("backpressed","bp");
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }
}
