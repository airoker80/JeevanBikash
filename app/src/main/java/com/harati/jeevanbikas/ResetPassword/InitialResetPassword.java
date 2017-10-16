package com.harati.jeevanbikas.ResetPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InitialResetPassword extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    Button initial_password_reset;
    EditText agent_mobile_id;
    SessionHandler sessionHandler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_reset_password);
        initial_password_reset=(Button)findViewById(R.id.initial_password_reset);
        initial_password_reset.setOnClickListener(this);

        sessionHandler = new SessionHandler(InitialResetPassword.this);
        spinner = (Spinner) findViewById(R.id.spinner);
        agent_mobile_id = (EditText) findViewById(R.id.agent_mobile_id);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int getVid = v.getId();
        switch (getVid){
            case R.id.initial_password_reset:
//                startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
                sendOtpRequest();
           /*     try {
                    sendOkHttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
        }
    }

    public void sendOtpRequest(){
        JSONObject jsonObject = new JSONObject();
        try{
            Log.e("agentCode","ac"+sessionHandler.getAgentCode());
            jsonObject.put("agentCode",sessionHandler.getAgentCode());
            jsonObject.put("mobile","9813297782");
        }catch (Exception e){
            e.printStackTrace();
        }
        VolleyRequestHandler handler= new VolleyRequestHandler(getApplicationContext());
        handler.makePostRequest("requestotp?serialno=12348", jsonObject, new RequestListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("response-----", response);
            }

            @Override
            public void onFailure(String response) {

            }
        });

    }

    void sendOkHttp() throws IOException {
/*        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"agentCode\":\"A0050001\",\"mobile\":\"9813297782\"}");
        String stringBody = body.toString();
        Request request = new Request.Builder()
                .url("http://103.1.94.77:9001/agentbank/api/v1/agent/requestotp?serialno=12348")
                .post(body)
                .addHeader("x-authorization", sessionHandler.getAgentCode())
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "dcc7bb7c-264e-bf0c-0726-9c0488a31c01")
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.e("response",response.toString());
        }catch (Exception e){
            e.printStackTrace();
        }*/
/*        String json = "";
        post(json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("callresponse",response.toString());
            }
        });*/

    }
  /*  OkHttpClient client = new OkHttpClient();
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Call post( String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://103.1.94.77:9001/agentbank/api/v1/agent/requestotp?serialno=12348")
                .post(body)
                .addHeader("x-authorization", sessionHandler.getAgentCode())
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "dcc7bb7c-264e-bf0c-0726-9c0488a31c01")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }*/
}
