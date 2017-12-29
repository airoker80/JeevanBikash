package com.harati.jeevanbikas.ResetPin;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.harati.jeevanbikas.BaseActivity;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPassword.ResetPassword;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPin extends BaseActivity {
    Spinner spinner;

    SessionHandler sessionHandler;
    ApiSessionHandler apiSessionHandler;

    Retrofit retrofit;
    ApiInterface apiInterface;
    Button change_pin;

    EditText old_pin, new_pin, re_new_pin, otp_pin;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);
        spinner = (Spinner) findViewById(R.id.spinner);
        sessionHandler = new SessionHandler(this);
        apiSessionHandler = new ApiSessionHandler(this);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);


        old_pin = (EditText) findViewById(R.id.old_pin);
        new_pin = (EditText) findViewById(R.id.new_pin);
        re_new_pin = (EditText) findViewById(R.id.re_new_pin);
        otp_pin = (EditText) findViewById(R.id.otp_pin);

        change_pin = (Button) findViewById(R.id.change_pin);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        sessionHandler = new SessionHandler(this);

        change_pin.setOnClickListener(v -> {
            if (otp_pin.getText().toString().equals("") |old_pin.getText().toString().equals("") |new_pin.getText().toString().equals("") |
                            re_new_pin.getText().toString().equals("")){
                if (otp_pin.getText().toString().equals("")){otp_pin.setError("this field is empty");}
                if (old_pin.getText().toString().equals("")){old_pin.setError("this field is empty");}
                if (new_pin.getText().toString().equals("")){new_pin.setError("this field is empty");}
                if (re_new_pin.getText().toString().equals("")){re_new_pin.setError("this field is empty");}
                if (!re_new_pin.getText().toString().equals(new_pin.getText().toString())){
                    re_new_pin.setError("PIN not same");
                    new_pin.setError("PIN not same");
                }
            }else {
                sendReestReq();
            }
        });

    }

    void sendReestReq() {
        sessionHandler.showProgressDialog("Sending Request ....");
        final JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            Log.e("agentCode", "ac" + sessionHandler.getAgentCode());
            jsonObject.put("agentCode", sessionHandler.getAgentCode());
            jsonObject.put("mobile", getIntent().getStringExtra("mobile"));
            jsonObject.put("oldpassword", old_pin.getText().toString());
            jsonObject.put("newpassword", re_new_pin.getText().toString());
            jsonObject.put("otp", otp_pin.getText().toString());

            jsonArray.put(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
//      retrofit2.Call<String> call = apiInterface.sendRetrofitOtprequest(body,"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBMDA1MDAwMSIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTUwODIyMjcyODMzOSwiZXhwIjoxNTA4ODI3NTI4fQ.lAqF1g6Oil2fC8FRfK_ktR2J4oiNZDVsqmLStY855ZQxvH6whWkRI7nkxmeOzXJM912yMXaWgv_Sk4kzJgoRFA");
        retrofit2.Call<ResponseBody> call = apiInterface.sendResetRequest(apiSessionHandler.getAGENT_PASSWORD_RESET(), body,
                sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json", apiSessionHandler.getAgentCode());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")) {
                    sessionHandler.setPinStatus(false);
                    Intent intent = new Intent(ResetPin.this, MainActivity.class);
                    startActivity(intent);
//                    Toast.makeText(this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String jsonString = response.errorBody().string();

                        Log.d("here ", "--=>" + jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(ResetPin.this, ErrorDialogActivity.class);
                        intent.putExtra("msg", jsonObject.getString("message"));
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });
    }
}
