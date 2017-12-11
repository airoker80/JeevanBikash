package com.harati.jeevanbikas.ResetPin;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPassword.InitialResetPassword;
import com.harati.jeevanbikas.ResetPassword.ResetPassword;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.OTPmodel;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class InitialResetPinActivity extends AppCompatActivity implements View.OnClickListener  {
    Spinner spinner;
    Button initial_pin_reset;


    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;

    SessionHandler sessionHandler;
    EditText pin_enrty_etxt;
    Handler handler;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_reset_pin);

        View parentLayout = findViewById(android.R.id.content);
        String mainmsg = getIntent().getStringExtra("snackmsg");


        apiSessionHandler = new ApiSessionHandler(this);
        sessionHandler = new SessionHandler(this);

        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        pin_enrty_etxt=(EditText)findViewById(R.id.pin_enrty_etxt);

        try {
            if (mainmsg.equals("fromMA")){
                Snackbar.make(parentLayout, "Please change your  pin before proceeding", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initial_pin_reset=(Button)findViewById(R.id.initial_pin_reset);
        initial_pin_reset.setOnClickListener(this);

        sessionHandler = new SessionHandler(this);

        handler=new Handler();
        r=() -> sessionHandler.logoutUser();

        startHandler();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        int getVid = v.getId();
        switch (getVid){
            case R.id.initial_pin_reset:
                startActivity(new Intent(InitialResetPinActivity.this,ResetPin.class));
                break;
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        stopHandler();
        startHandler();
    }

    public void startHandler() {
        handler.postDelayed(r, 2*60*1000); //for 5 minutes
    }
    public void stopHandler() {
        Log.e("Handler","Stoped");
        handler.removeCallbacks(r);
    }


    void sendRetrofitReq(){
        sessionHandler.showProgressDialog("Sending Request ....");
        final JSONObject jsonObject = new JSONObject();
//      startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
        JSONArray jsonArray = new JSONArray();
        try{
            Log.e("agentCode","ac"+sessionHandler.getAgentCode());
            jsonObject.put("agentCode",sessionHandler.getAgentCode());
            jsonObject.put("mobile",pin_enrty_etxt.getText().toString());

            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
//      retrofit2.Call<String> call = apiInterface.sendRetrofitOtprequest(body,"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBMDA1MDAwMSIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTUwODIyMjcyODMzOSwiZXhwIjoxNTA4ODI3NTI4fQ.lAqF1g6Oil2fC8FRfK_ktR2J4oiNZDVsqmLStY855ZQxvH6whWkRI7nkxmeOzXJM912yMXaWgv_Sk4kzJgoRFA");
        retrofit2.Call<OTPmodel> call = apiInterface.sendRetrofitOtprequest(apiSessionHandler.getAGENT_PIN_RESET(),body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());
        call.enqueue(new retrofit2.Callback<OTPmodel>() {
            @Override
            public void onResponse(retrofit2.Call<OTPmodel> call, retrofit2.Response<OTPmodel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")){
                    startActivity(new Intent(InitialResetPinActivity.this,ResetPassword.class));
                    Toast.makeText(InitialResetPinActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(InitialResetPinActivity.this, ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<OTPmodel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });


    }
    @Override
    protected void onDestroy() {
        stopHandler();
        super.onDestroy();
    }
}
