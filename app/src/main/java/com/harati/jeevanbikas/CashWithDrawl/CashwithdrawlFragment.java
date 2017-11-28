package com.harati.jeevanbikas.CashWithDrawl;


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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.CashDeposit.CashDepositActivity;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.Login.LoginActivity;
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

import static java.lang.Thread.sleep;


/**
 * Created by User on 8/28/2017.
 */

public class CashwithdrawlFragment extends Fragment {

    LinearLayout beforConfirmation;
    ApiSessionHandler apiSessionHandler ;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;
    ImageView image,crossIV;
    CenturyGothicTextView title;
    String code,name,office ,photo;
    Bundle bundle;


    ImageView submit;
    EditText withdrawlAmount,agentPin ,withdrawlRemark,withrwal_mobile,clientPin;
    String withdrawlAmountTxt,agentPinTxt ,withdrawlRemarkTxt;
    ImageView imgUser;

    TextView memberId,branch ,accNo,withdrawlTxt,at_pin ,remarks_txt,memberIdnnumber,branchName,nameTxt,dw_mob_no,gone_cw_txt;
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

        view.setFocusableInTouchMode(true);
/*        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (view.hasFocusable()){

                Log.e("focus","==="+String.valueOf(view.findFocus()));
                Log.e("ad","dasd"+event.toString());
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        confirmBack();
                    }
                }
            }else {
                view.setFocusableInTouchMode(true);
                view.requestFocus();
            }
            return false;
        });*/

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        image= (ImageView)view.findViewById(R.id.image);
        crossIV= (ImageView)view.findViewById(R.id.crossIV);

        beforConfirmation= (LinearLayout) view.findViewById(R.id.beforConfirmation);

        image.setOnClickListener(view1 -> confirmBack());
        crossIV.setOnClickListener(view1 -> {
            confirmBackCross();
/*            if (beforConfirmation.getVisibility()==View.VISIBLE){
                getActivity().onBackPressed();
            }else {
                beforConfirmation.setVisibility(View.VISIBLE);
                gone_cw_txt.setVisibility(View.GONE);
            }*/
//            startActivity(new Intent(getContext(),MainActivity.class));
        });
        submit = (ImageView)view.findViewById(R.id.submit);
        withdrawlAmount=(EditText)view.findViewById(R.id.withdrawlAmount);
        agentPin=(EditText)view.findViewById(R.id.agentPin);
        title=(CenturyGothicTextView) view.findViewById(R.id.title);
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
        dw_mob_no=(TextView) view.findViewById(R.id.dw_mob_no);
        gone_cw_txt=(TextView) view.findViewById(R.id.gone_cw_txt);
        memberIdnnumber=(TextView) view.findViewById(R.id.memberIdnnumber);


        memberId.setTypeface(MainActivity.centuryGothic);
        branch.setTypeface(MainActivity.centuryGothic);
        accNo.setTypeface(MainActivity.centuryGothic);
        withdrawlTxt.setTypeface(MainActivity.centuryGothic);
        at_pin.setTypeface(MainActivity.centuryGothic);
        remarks_txt.setTypeface(MainActivity.centuryGothic);

        title.setText("Cash Withdrawl");
        memberIdnnumber.setText(code);
        branchName.setText(office);
        nameTxt.setText(name);
        dw_mob_no.setText(bundle.getString("phone"));

        withrwal_mobile.setText(bundle.getString("phone"));


        Log.e("currency",
                sessionHandler.returnCash("1000")+
                sessionHandler.returnCash("10000")+
                sessionHandler.returnCash("100000")
        );
//        Picasso.with(getContext()).load(photo).into(imgUser);

        try {
            String[] splitString = photo.split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgUser.setImageBitmap(userPhoto);

            Log.e("photo","--->"+photo);
        }catch (Exception e){
            e.printStackTrace();
        }


        submit.setOnClickListener(view1 -> {
            withdrawlAmountTxt=withdrawlAmount.getText().toString();
            agentPinTxt=agentPin.getText().toString();
            withdrawlRemarkTxt=withdrawlRemark.getText().toString();
            if (withdrawlAmountTxt.equals("")|agentPinTxt.equals("")|clientPin.getText().toString().equals("")){
                getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
            }else {
                if (Integer.parseInt(withdrawlAmount.getText().toString())==0){
                    Intent intent =new Intent(getContext(), ErrorDialogActivity.class);
                    intent.putExtra("msg","Zero amount cannot be withdrawn");
                    getActivity().startActivity(intent);
                }else {
                    if (beforConfirmation.getVisibility() == View.VISIBLE){
                        beforConfirmation.setVisibility(View.GONE);
                        gone_cw_txt.setVisibility(View.VISIBLE);
                        gone_cw_txt.setText("के तपाई रु "+sessionHandler.returnCash(withdrawlAmount.getText().toString()) + " झिक्न   चाहनुहुन्छ ?");
                    }else {
                            sendOtpForCashDeposit();
                    }
//                    sendWithdrawequest(withdrawlAmount.getText().toString(),agentPin.getText().toString(),withdrawlRemark.getText().toString());

                }
            }
        });
        return view;
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
                            withrwal_mobile.setError("Mobile No. is not registered");
                        }else if (jsonObject.getString("message").equals("Sorry,  Withdraw Amount must be greater than Rs. 1000.00")){
                            withdrawlAmount.setError("Must be greater than 1000.00");
                        }else if (jsonObject.getString("message").equals("Failed to send OTP")){
                            withdrawlAmount.setError("Must be greater than 1000.00");
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
                if (beforConfirmation.getVisibility()==View.VISIBLE){
                    ((CashWithDrawlActivity)getActivity()).backpressed();
                }else {
                    beforConfirmation.setVisibility(View.VISIBLE);
                    clientPin.setText("");
                    agentPin.setText("");
                    withdrawlAmount.setText("");
                    gone_cw_txt.setVisibility(View.GONE);
                }

                Log.e("backpressed","bp");
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }
    void confirmBackCross(){
        Log.e("backpressed","bp");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ask_permission,null);
        TextView askPermission = (TextView)view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to Cancel??");
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want Cancel?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                if (beforConfirmation.getVisibility()==View.VISIBLE){
                    ((CashWithDrawlActivity)getActivity()).backpressed();
                }else {
                    beforConfirmation.setVisibility(View.VISIBLE);
                    gone_cw_txt.setVisibility(View.GONE);
                    withdrawlAmount.setText("");
                    agentPin.setText("");
                    clientPin.setText("");
                }
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }

    @Override
    public void onResume() {
        withdrawlAmount.setText("");
        agentPin.setText("");
        clientPin.setText("");
        withdrawlRemark.setText("");
        super.onResume();
    }
}
