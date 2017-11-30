package com.harati.jeevanbikas.CashDeposit;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
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
import com.harati.jeevanbikas.Login.LoginActivity;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.WithDrawlResponse;
import com.harati.jeevanbikas.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Thread.sleep;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentClientTransferFragment extends Fragment {

    public String otpValue="";
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;
    CenturyGothicTextView name,memberIdnnumber,branchName,shownDepositAmt,sendOtpAgain,cdt_mob_no,title;
    PinEntryEditText act_otp_tf;
    ImageView agent_client_tick,demand_cross,act_mem_photo,image;
    Bundle bundle;
    ImageButton resend_otp;
    public AgentClientTransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle =getArguments();
        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
        View view= inflater.inflate(R.layout.fragment_agent_client_transfer, container, false);



        name=(CenturyGothicTextView)view.findViewById(R.id.name);
        memberIdnnumber=(CenturyGothicTextView)view.findViewById(R.id.memberIdnnumber);
        branchName=(CenturyGothicTextView)view.findViewById(R.id.branchName);
        shownDepositAmt=(CenturyGothicTextView)view.findViewById(R.id.shownDepositAmt);
        sendOtpAgain=(CenturyGothicTextView)view.findViewById(R.id.sendOtpAgain);
        cdt_mob_no=(CenturyGothicTextView)view.findViewById(R.id.cdt_mob_no);
        title=(CenturyGothicTextView)view.findViewById(R.id.title);

        act_otp_tf=(PinEntryEditText) view.findViewById(R.id.act_otp_tf);

        title.setText("Cash Deposit");

        name.setText(bundle.getString("name"));
        memberIdnnumber.setText(bundle.getString("code"));
        cdt_mob_no.setText(bundle.getString("code"));
        branchName.setText(bundle.getString("phone"));
        shownDepositAmt.setText(getResources().getString(R.string.currency_np)+" "+bundle.getString("deposoitAmt"));

//        getOtpValue();
//        sendOtpForCashDeposit();

        agent_client_tick=(ImageView)view.findViewById(R.id.agent_client_tick);
        demand_cross=(ImageView)view.findViewById(R.id.demand_cross);
        act_mem_photo=(ImageView)view.findViewById(R.id.act_mem_photo);
        image=(ImageView)view.findViewById(R.id.image);

        resend_otp=(ImageButton) view.findViewById(R.id.resend_otp);



//        new Thread(task1).start();

        Observable.fromCallable(callable).
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                doOnSubscribe(disposable ->
                        resend_otp.setEnabled(false)
                ).
                subscribe(getDisposableObserver());
        try {
            String[] splitString = bundle.getString("photo").split(",");
            String base64Photo = splitString[1];
            byte[] decodedString = Base64.decode(base64Photo, Base64.DEFAULT);
            Bitmap userPhoto = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            act_mem_photo.setImageBitmap(userPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }


        image.setOnClickListener(view1 -> confirmBack());
        resend_otp.setOnClickListener(view1 -> {
            final AlertDialog builder = new AlertDialog.Builder(getContext())
                    .setPositiveButton("OK", null)
                    .setNegativeButton("CANCEL", null)
                    .setTitle("Send Otp request Again?")
                    .create();



            builder.setOnShowListener(dialog -> {

                final Button btnAccept = builder.getButton(
                        AlertDialog.BUTTON_POSITIVE);

                btnAccept.setOnClickListener(v1 -> {
                        sendOtpForCashDeposit();
                    builder.dismiss();

                });

                final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

                btnDecline.setOnClickListener(v1 -> builder.dismiss()
                );
            });
            builder.show();
        });
        agent_client_tick.setOnClickListener(v -> {

            if (!act_otp_tf.getText().equals("")){
                sendDepositRequest();
            }else {
                act_otp_tf.setError("Enter OTP first");
            }
//                startActivity(new Intent(getContext(), DialogActivity.class));
        });
        demand_cross.setOnClickListener(view1 -> confirmBackCross());

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
                    if (JeevanBikashConfig.BASE_URL1.equals("1")) {
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

    private void sendDepositRequest(){
        sessionHandler.showProgressDialog("Sending Request");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger",bundle.getString("clientsPin"));
            jsonObject.put("amount",bundle.getString("deposoitAmt"));
            jsonObject.put("agentpin",bundle.getString("agentPin"));
            jsonObject.put("remark",bundle.getString("deposoitRemarks"));
            jsonObject.put("mobile",bundle.getString("cd_mobile_no"));
            jsonObject.put("otp",act_otp_tf.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<TransferModel> call = apiInterface.sendDepositRequest(apiSessionHandler.getCASH_DEPOSIT(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());
        call.enqueue(new Callback<TransferModel>() {
            @Override
            public void onResponse(Call<TransferModel> call, Response<TransferModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    Intent intent = new Intent( getContext(),DialogActivity.class);
                    intent.putExtra("msg",response.body().getMessage());
                    startActivity(intent);
                    getActivity().finish();
                }else {
                    try {
                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
//                        ((CashDepositActivity)getActivity()).backpressed();
                        startActivity(intent);
                        act_otp_tf.setText("");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TransferModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent( getContext(),ErrorDialogActivity.class);
                intent.putExtra("msg","connection Error");
                startActivity(intent);
            }
        });

    }

    private void  sendOtpForCashDeposit(){
        sessionHandler.showProgressDialog("Sending Request .... ");
        final JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.get("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",bundle.getString("deposoitAmt"));
            jsonObject.put("agentpin",bundle.getString("agentPin"));
            jsonObject.put("mobile",bundle.get("cd_mobile_no"));
            jsonObject.put("remark",bundle.getString("deposoitRemarks"));
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));

        retrofit2.Call<WithDrawlResponse> call = apiInterface.sendWithdrawRequest(apiSessionHandler.getDEPOSIT_OTP(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<WithDrawlResponse>() {
            @Override
            public void onResponse(Call<WithDrawlResponse> call, Response<WithDrawlResponse> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
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

    void confirmBackCross(){
        Log.e("backpressed","bp");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ask_permission,null);
        TextView askPermission = (TextView)view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to go back to dashboard?");
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want Cancel?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                startActivity(new Intent(getContext(),MainActivity.class));
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }
    void confirmBack(){
        Log.e("backpressed","bp");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_ask_permission,null);
        TextView askPermission = (TextView)view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to go back to dashboard?");
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want Cancel?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                ((CashDepositActivity)getActivity()).backpressed();
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }

    Callable callable =() -> {
        SystemClock.sleep(60000);
        return  null;
    };

    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onComplete() {
                resend_otp.setEnabled(true);
            }

            @Override
            public void onError(Throwable e) {
                resend_otp.setEnabled(true);
            }

            @Override
            public void onNext(String message) {
            }
        };
    }
}
