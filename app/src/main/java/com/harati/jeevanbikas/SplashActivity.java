package com.harati.jeevanbikas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.Login.LoginActivity;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    ApiSessionHandler apiSessionHandler;
    SessionHandler sessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionHandler = new SessionHandler(this);
        apiSessionHandler = new ApiSessionHandler(this);
        setContentView(R.layout.activity_splash);
//        apiSessionHandler.saveAppFirstState(true);

    }

    @Override
    protected void onResume() {
        Log.d("resume", "===>" + String.valueOf(apiSessionHandler.getFirstRunStatus()));
        super.onResume();
        if (!apiSessionHandler.getFirstRunStatus()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
//            apiSessionHandler.saveAppFirstState(false);
            setUpUrl();
        }
    }


    private void setUpUrl() {


        View view = getLayoutInflater().inflate(R.layout.dialog_setup_url, null);
        final AlertDialog builder = new AlertDialog.Builder(this)
                .setPositiveButton("Save Url", null)
                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Setting up the Application")
                .create();

        CGEditText baseUrl = (CGEditText) view.findViewById(R.id.baseUrl);
        CGEditText serialNo = (CGEditText) view.findViewById(R.id.serialNo);
        CGEditText api_username = (CGEditText) view.findViewById(R.id.api_username);
        CGEditText api_password = (CGEditText) view.findViewById(R.id.api_password);
        CGEditText api_master_key = (CGEditText) view.findViewById(R.id.api_master_key);

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                builder.dismiss();
                sessionHandler.saveAgentUrlInfo(serialNo.getText().toString());
                try {
                    retrofit = MyApplication.getRetrofitInstance(baseUrl.getText().toString());
                    apiInterface = retrofit.create(ApiInterface.class);
                    sessionHandler.showProgressDialog("Setting Up Application ...");
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username", api_username.getText().toString());
                        jsonObject.put("password", api_password.getText().toString());
                        jsonObject.put("masterkey", api_master_key.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
                    Call<ResponseBody> call = apiInterface.callSystemApi(body, "12345");
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            sessionHandler.hideProgressDialog();
                            if (response.isSuccessful()) {
                                apiSessionHandler.saveAgentCode(serialNo.getText().toString());
                                apiSessionHandler.saveAppFirstState(false);
                                String jsonString = null;
                                try {
                                    jsonString = response.body().string();
                                    JSONArray jsonArray = new JSONArray(jsonString);
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject object = jsonArray.getJSONObject(i);
                                        Log.d("resp" + i, object.toString());
//                                    if (object.getString("apiName").equals("SYSTEM API")){ }
                                        if (object.getString("apiName").equals("SYSTEM API")) {
                                            apiSessionHandler.saveSYSTEM_API(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("AGENT LOGIN")) {
                                            apiSessionHandler.saveAGENT_LOGIN(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("AGENT DASHBOARD")) {
                                            apiSessionHandler.saveAGENT_DASHBOARD(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("AGENT OTP")) {
                                            apiSessionHandler.saveAGENT_OTP(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("AGENT PIN RESET")) {
                                            apiSessionHandler.saveAGENT_PIN_RESET(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("AGENT PASSWORD RESET")) {
                                            apiSessionHandler.saveAGENT_PASSWORD_RESET(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("MEMBER SEARCH")) {
                                            apiSessionHandler.saveMEMBER_SEARCH(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("BALANCE ENQUIRY")) {
                                            apiSessionHandler.saveBALANCE_ENQUIRY(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("CASH DEPOSIT")) {
                                            apiSessionHandler.saveCASH_DEPOSIT(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("CASH WITHDRAW")) {
                                            apiSessionHandler.saveCASH_WITHDRAW(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("FUND TRANSFER")) {
                                            apiSessionHandler.saveFUND_TRANSFER(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("MEMBER ENROLL")) {
                                            apiSessionHandler.saveMEMBER_ENROLL(object.getString("baseUrl"));
                                        }
                                        if (object.getString("apiName").equals("LOAN DEMAND")) {
                                            apiSessionHandler.saveLOAN_DEMAND(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("CASTE LIST")) {
                                            apiSessionHandler.saveCASTE_LIST(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("LOAN TYPE LIST")) {
                                            apiSessionHandler.saveLOAN_TYPE_LIST(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("DEPOSIT OTP")) {
                                            apiSessionHandler.saveDEPOSIT_OTP(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("WITHDRAW OTP")) {
                                            apiSessionHandler.saveWITHDRAW_OTP(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                        if (object.getString("apiName").equals("FUND TRANSFER OTP")) {
                                            apiSessionHandler.saveFUND_TRANSFER_OTP(object.getString("baseUrl"));
                                            Log.d("ok==" + i, "ok");
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.d("JsonString", "===>" + jsonString);
                                apiSessionHandler.saveApiJson(jsonString);
                                apiSessionHandler.saveAppFirstState(false);
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            sessionHandler.hideProgressDialog();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent = new Intent(this, ErrorDialogActivity.class);
                    intent.putExtra("msg", "Please Enter Valid URL");
                    startActivity(intent);
                }

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> {
                        builder.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        startActivity(intent);
                    }
            );
        });
        builder.show();
    }
}
