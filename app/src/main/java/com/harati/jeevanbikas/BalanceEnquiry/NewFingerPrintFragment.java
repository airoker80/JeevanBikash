package com.harati.jeevanbikas.BalanceEnquiry;

import android.Manifest;
import android.app.Fragment;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.harati.jeevanbikas.fingerprint.FingerprintHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyStore;

import javax.crypto.Cipher;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by Sameer on 11/2/2017.
 */

public class NewFingerPrintFragment extends android.support.v4.app.Fragment {
    ImageView fingerPrint;
    SessionHandler sessionHandler;
    private KeyStore keyStore;
    ApiInterface apiInterface;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "Bikash";
    private Cipher cipher;
    private TextView textView,text;
    Bundle bundle;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        bundle = getArguments();
        apiInterface = RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());
        text = (TextView) view.findViewById(R.id.text);
        text.setTypeface(MainActivity.centuryGothic);


        fingerPrint.setOnClickListener(view1 -> {
//                showMessage();

            sendBalanceEnquiryRequest();

/*                Snackbar snackbar = Snackbar
                    .make(view, "Please Place your right finger in the sensor", Snackbar.LENGTH_LONG);
            snackbar.show();
            View snackbarview = snackbar.getView();
            TextView textView = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);*/
        });
        return view;
    }


    public  void  sendBalanceEnquiryRequest(){
        sessionHandler.showProgressDialog("Sending Request ....");
        final JSONObject jsonObject = new JSONObject();
//      startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
        JSONArray jsonArray = new JSONArray();
        try{
            Log.e("agentCode","ac"+sessionHandler.getAgentCode());
            jsonObject.put("membercode",bundle.get("memberId"));
            jsonObject.put("finger","1234");

            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SuccesResponseModel> call = apiInterface.sendBalanceRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");
        call.enqueue(new Callback<SuccesResponseModel>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    if (response.body().getStatus().equals("Success")){
                        Intent intent = new Intent(getContext(),DialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getContext(),ErrorDialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }
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

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
