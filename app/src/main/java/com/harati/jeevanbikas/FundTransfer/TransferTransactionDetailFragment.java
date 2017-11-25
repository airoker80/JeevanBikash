package com.harati.jeevanbikas.FundTransfer;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;
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
public class TransferTransactionDetailFragment extends Fragment {

    public String otpValue="";
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;
    LinearLayout topPanel2;
    CenturyGothicTextView name,memberIdnnumber,branchName,shownDepositAmt,amountType,sendOtpAgain;
    CenturyGothicTextView nameBeneficiary,memberIdnnumberBeneficiary,branchNameBeneficiary;
    CGEditText act_otp_tf;
    ImageView agent_client_tick,demand_cross;
    Bundle bundle;

    public TransferTransactionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle =getArguments();

        Log.d("bundle","==00--+>"+bundle.toString());
        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
        View view= inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);


        topPanel2=(LinearLayout) view.findViewById(R.id.topPanel2);
        topPanel2.setVisibility(View.VISIBLE);

        name=(CenturyGothicTextView)view.findViewById(R.id.name);
        memberIdnnumber=(CenturyGothicTextView)view.findViewById(R.id.memberIdnnumber);
        branchName=(CenturyGothicTextView)view.findViewById(R.id.branchName);

        nameBeneficiary=(CenturyGothicTextView)view.findViewById(R.id.nameBeneficiary);
        memberIdnnumberBeneficiary=(CenturyGothicTextView)view.findViewById(R.id.memberIdnnumberBeneficiary);
        branchNameBeneficiary=(CenturyGothicTextView)view.findViewById(R.id.branchNameBeneficiary);

        act_otp_tf=(CGEditText) view.findViewById(R.id.act_otp_tf);

        shownDepositAmt=(CenturyGothicTextView)view.findViewById(R.id.shownDepositAmt);

        amountType=(CenturyGothicTextView)view.findViewById(R.id.amountType);
        sendOtpAgain=(CenturyGothicTextView)view.findViewById(R.id.sendOtpAgain);

        amountType.setText("Transfer amount");
        shownDepositAmt.setText(getResources().getString(R.string.currency_np)+" "+bundle.getString("transfer_amount")+".00");

        name.setText(bundle.getString("name"));
        memberIdnnumber.setText(bundle.getString("code"));
        branchName.setText(bundle.getString("office"));

        nameBeneficiary.setText(bundle.getString("nameBenificiary") + " Ben");
        memberIdnnumberBeneficiary.setText(bundle.getString("codeBenificiary"));
        branchNameBeneficiary.setText(bundle.getString("officeBenificiary"));

//        sendOtpForFundTransfer();
        agent_client_tick=(ImageView)view.findViewById(R.id.agent_client_tick);
        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);
        agent_client_tick.setOnClickListener(v -> {

            if (!act_otp_tf.getText().toString().equals("")){
                sendTransferPostRequest();
            }else {
                act_otp_tf.setError("Enter OTP first");
            }
//                startActivity(new Intent(getContext(), DialogActivity.class));
        });
        demand_cross.setOnClickListener(view1 -> startActivity(new Intent(getContext(), MainActivity.class)));

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
//                    sendOtpForFundTransfer();
                    if (JeevanBikashConfig.BASE_URL1.equals("1")){
                        sendOtpForFundTransfer();
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
        return view;
    }


    private void  sendOtpForFundTransfer(){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.get("code"));
            jsonObject.put("finger",bundle.get("fiClienttPin"));
            jsonObject.put("amount",bundle.getString("transfer_amount"));
            jsonObject.put("agentpin",bundle.getString("transfer_pin"));
            jsonObject.put("mobile",bundle.get("transfer_mobile"));
            jsonObject.put("beneficiary",bundle.getString("transfer_beneficiary_no"));
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(apiSessionHandler.getFUND_TRANSFER_OTP(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    String message = response.body().getMessage();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    getOtpValue();
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


    private void getOtpValue(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_get_otp, null);
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Enter Otp")
                .create();

        CGEditText getOpt = (CGEditText) view.findViewById(R.id.getOpt);

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                otpValue = getOpt.getText().toString();
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> {
                        builder.dismiss();
                    }
            );
        });
        builder.show();
    }
    void sendTransferPostRequest(){
        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",bundle.getString("transfer_amount"));
            jsonObject.put("agentpin",bundle.getString("transfer_pin"));
            jsonObject.put("beneficiary",bundle.getString("transfer_beneficiary_no"));
            jsonObject.put("otp",act_otp_tf.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<TransferModel> call = apiInterface.sendFundTransferRequest(apiSessionHandler.getFUND_TRANSFER(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<TransferModel>() {
            @Override
            public void onResponse(Call<TransferModel> call, Response<TransferModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    JeevanBikashConfig.BASE_URL1="2";
                    new Thread(task1).start();
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg",response.body().getMessage());
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
            public void onFailure(Call<TransferModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(getContext(),DialogActivity.class);
                intent.putExtra("msg","Connection Error");
                startActivity(intent);
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
}
