package com.harati.jeevanbikas.ResetPassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResetPassword extends AppCompatActivity {
    ApiSessionHandler apiSessionHandler;
    EditText reset_otp;
    Button resetPass;
    Spinner spinner;
    SessionHandler sessionHandler;
    ApiInterface apiInterface;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        apiSessionHandler = new ApiSessionHandler(this);
        sessionHandler = new SessionHandler(ResetPassword.this);
        setContentView(R.layout.activity_reset_password);
        resetPass = (Button)findViewById(R.id.resetPass);
        reset_otp = (EditText) findViewById(R.id.reset_otp);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        resetPass.setOnClickListener(v -> sendReestReq());
    }

    void sendReestReq(){
        sessionHandler.showProgressDialog("Sending Request ....");
        final JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            Log.e("agentCode","ac"+sessionHandler.getAgentCode());
            jsonObject.put("agentCode",sessionHandler.getAgentCode());
            jsonObject.put("mobile","9802796417");
            jsonObject.put("oldpassword","Aft@12345");
            jsonObject.put("newpassword","Aft@12346");
            jsonObject.put("otp",reset_otp.getText().toString());

            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
//      retrofit2.Call<String> call = apiInterface.sendRetrofitOtprequest(body,"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBMDA1MDAwMSIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTUwODIyMjcyODMzOSwiZXhwIjoxNTA4ODI3NTI4fQ.lAqF1g6Oil2fC8FRfK_ktR2J4oiNZDVsqmLStY855ZQxvH6whWkRI7nkxmeOzXJM912yMXaWgv_Sk4kzJgoRFA");
        retrofit2.Call<String> call = apiInterface.sendResetRequest(apiSessionHandler.getAGENT_PASSWORD_RESET(),body,
                sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                sessionHandler.hideProgressDialog();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            sessionHandler.hideProgressDialog();
            }
        });
    }
}
