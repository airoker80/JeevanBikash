package com.harati.jeevanbikas.FundTransfer;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
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
 * Created by User on 8/28/2017.
 */

public class FundInfoFragment extends Fragment {
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit ;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;

    CenturyGothicTextView fundtransfername,fundMemberCode ,fundBranchName,
            send_mob_id,reciverUn,reciver_mc,reciver_bn,rec_mob_id;

    Bundle bundle;
    ImageView submit,crossIV,image,rec_ff_photo,ff_send_photo;
    EditText BenificiaryaccNo,confirmAccNo ,transferAmt,agentPin ,fundMobile;
    CGEditText fiClienttPin;
    String BenificiaryaccNoTxt,confirmAccNoTxt ,deposoitAmtTxt,agentPinTxt ,fundMobileTxt;
    CenturyGothicTextView gone_ft_txt;
    LinearLayout befor_conf_ll;
    public FundInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fund_info, container, false);
        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        sessionHandler = new SessionHandler(getContext());

        submit = (ImageView)view.findViewById(R.id.submit);
        crossIV = (ImageView)view.findViewById(R.id.crossIV);
        image = (ImageView)view.findViewById(R.id.image);

        rec_ff_photo = (ImageView)view.findViewById(R.id.rec_ff_photo);
        ff_send_photo = (ImageView)view.findViewById(R.id.ff_send_photo);


        befor_conf_ll = (LinearLayout) view.findViewById(R.id.befor_conf_ll);

        fundtransfername = (CenturyGothicTextView) view.findViewById(R.id.fundtransfername);
        gone_ft_txt = (CenturyGothicTextView) view.findViewById(R.id.gone_ft_txt);
        fundMemberCode = (CenturyGothicTextView) view.findViewById(R.id.fundMemberCode);
        fundBranchName = (CenturyGothicTextView) view.findViewById(R.id.fundBranchName);

        send_mob_id = (CenturyGothicTextView) view.findViewById(R.id.send_mob_id);
        reciverUn = (CenturyGothicTextView) view.findViewById(R.id.reciverUn);
        reciver_mc = (CenturyGothicTextView) view.findViewById(R.id.reciver_mc);
        reciver_bn = (CenturyGothicTextView) view.findViewById(R.id.reciver_bn);
        rec_mob_id = (CenturyGothicTextView) view.findViewById(R.id.rec_mob_id);

        bundle = getArguments();


        fundtransfername.setText(bundle.getString("name") +" (Sender)");
        fundMemberCode.setText(bundle.getString("code"));
        fundBranchName.setText(bundle.getString("office"));
        send_mob_id.setText(bundle.getString("phone"));

        reciverUn.setText(bundle.getString("nameBenificiary") + " (Reciver)");
        reciver_mc.setText(bundle.getString("codeBenificiary"));
        reciver_bn.setText(bundle.getString("officeBenificiary"));
        rec_mob_id.setText(bundle.getString("phoneBenificiary"));

        try {
            String[] splitString = bundle.getString("photo").split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ff_send_photo.setImageBitmap(userPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] splitString = bundle.getString("photoBenificiary").split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            rec_ff_photo.setImageBitmap(userPhoto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BenificiaryaccNo=(EditText)view.findViewById(R.id.BenificiaryaccNo) ;
        confirmAccNo=(EditText)view.findViewById(R.id.confirmAccNo) ;
        transferAmt=(EditText)view.findViewById(R.id.transferAmt) ;
        agentPin=(EditText)view.findViewById(R.id.agentPin) ;
        fundMobile=(EditText)view.findViewById(R.id.fundMobile) ;
        fiClienttPin=(CGEditText) view.findViewById(R.id.fiClienttPin) ;

        BenificiaryaccNo.setText(bundle.getString("codeBenificiary"));

        submit.setOnClickListener(view1 -> {
  /*          Intent intent= new Intent(getContext(), DialogActivity.class);
            getActivity().startActivity(intent);*/

            BenificiaryaccNoTxt=BenificiaryaccNo.getText().toString();
            confirmAccNoTxt=confirmAccNo.getText().toString();
            deposoitAmtTxt=transferAmt.getText().toString();
            agentPinTxt=agentPin.getText().toString();
            fundMobileTxt=fundMobile.getText().toString();

            if (BenificiaryaccNoTxt.equals("")|confirmAccNoTxt.equals("")|deposoitAmtTxt.equals("")|
            agentPinTxt.equals("")|fundMobileTxt.equals("")){
                getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
            }
            else {
                if (BenificiaryaccNo.getText().toString().equals(bundle.getString("codeBenificiary"))){
                    if (gone_ft_txt.getVisibility()==View.VISIBLE){
                        sendOtpForFundTransfer();
                    }else {
                        gone_ft_txt.setText("के तपाई आफ्नो खाता बाट रु "+sessionHandler.returnCash(transferAmt.getText().toString())+" रुपया "+ bundle.getString("nameBenificiary")+" को खातामा पठाउना चाहनु हुन्छ ?");
                        gone_ft_txt.setVisibility(View.VISIBLE);
                        befor_conf_ll.setVisibility(View.GONE);
                    }

                }else {
                    Intent intent = new Intent(getContext(),ErrorDialogActivity.class);
                    intent.putExtra("msg","Beneficiary account number is not matched of previous beneficiary number that you searched");
                    startActivity(intent);
                }

//                sendTransferPostRequest();
            }
        });

        crossIV.setOnClickListener(view1 -> {

        });

        image.setOnClickListener(view1 -> confirmBack());
        return view;
    }

    void sendTransferPostRequest(){
        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",transferAmt.getText().toString());
            jsonObject.put("agentpin",agentPin.getText().toString());
            jsonObject.put("beneficiary",BenificiaryaccNo.getText().toString());
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

    private void  sendOtpForFundTransfer(){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.get("code"));
            jsonObject.put("finger",fiClienttPin.getText().toString());
            jsonObject.put("amount",agentPin.getText().toString());
            jsonObject.put("agentpin",agentPin.getText().toString());
            jsonObject.put("mobile",send_mob_id.getText().toString());
            jsonObject.put("beneficiary",BenificiaryaccNo.getText().toString());
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
                    Fragment fragment= new TransferTransactionDetailFragment();
                    bundle.putString("transfer_amount",transferAmt.getText().toString());
                    bundle.putString("transfer_pin",agentPin.getText().toString());
                    bundle.putString("transfer_beneficiary_no",BenificiaryaccNo.getText().toString());
                    bundle.putString("transfer_mobile",send_mob_id.getText().toString());
                    bundle.putString("fiClienttPin",fiClienttPin.getText().toString());

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
                            fiClienttPin.setError("Invalid Pincode");
                        }else if (jsonObject.getString("message").equals("Member Mobile No. is not registered...")){
                            fundMobile.setError("Mobile not Registered");
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
                if (befor_conf_ll.getVisibility()==View.VISIBLE){
                    ((FundTransferActivity)getActivity()).backpress();
                }else {
                    befor_conf_ll.setVisibility(View.VISIBLE);
                    gone_ft_txt.setVisibility(View.GONE);
                }

                Log.e("backpressed","bp");
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
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
