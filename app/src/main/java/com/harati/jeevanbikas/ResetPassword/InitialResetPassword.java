package com.harati.jeevanbikas.ResetPassword;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.OTPmodel;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

public class InitialResetPassword extends AppCompatActivity implements View.OnClickListener {
    ApiInterface apiInterface;
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
        apiInterface = RetrofitClient.getApiService();

        sessionHandler = new SessionHandler(InitialResetPassword.this);
        spinner = (Spinner) findViewById(R.id.spinner);
        agent_mobile_id = (EditText) findViewById(R.id.agent_mobile_id);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        int getVid = v.getId();
        switch (getVid){
            case R.id.initial_password_reset:
//                startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
//                sendOtpRequest();

//                sendSakarRequest();
                sendRetrofitReq();
           /*     try {
                    sendOkHttp();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendOtpRequest(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            Log.e("agentCode","ac"+sessionHandler.getAgentCode());
            jsonObject.put("agentCode",sessionHandler.getAgentCode());
            jsonObject.put("mobile","9813297782");

            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        VolleyRequestHandler handler= new VolleyRequestHandler(getApplicationContext());
       /* handler.makePostRequest("requestotp?serialno=12348", jsonObject, new RequestListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("response-----", response);
            }

            @Override
            public void onFailure(String response) {
                Log.d("response-----", response);
            }
        });*/

        try {
            handler.makeArrayRequest("requestotp?serialno=12348", jsonArray, new RequestListener() {
                @Override
                public void onSuccess(String response) {

                }

                @Override
                public void onFailure(String response) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*void sendOkHttp() throws IOException {
*//*        OkHttpClient client = new OkHttpClient();

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
        }*//*
*//*        String json = "";
        post(json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("callresponse",response.toString());
            }
        });*//*

    }*/
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


  void sendSakarRequest(){
      final RequestQueue requestQueue = Volley.newRequestQueue(InitialResetPassword.this);
      final JSONObject jsonObject = new JSONObject();
      JSONArray jsonArray = new JSONArray();
      try{
          Log.e("agentCode","ac"+sessionHandler.getAgentCode());
          jsonObject.put("agentCode",sessionHandler.getAgentCode());
          jsonObject.put("mobile","9813297782");

          jsonArray.put(jsonObject);
      }catch (Exception e){
          e.printStackTrace();
      }
      String REQUESTURL = JeevanBikashConfig.REQUEST_URL + "requestotp?serialno=12348";
      StringRequest req = new StringRequest(Request.Method.POST, REQUESTURL,
              responseObject -> {


              }, error -> {

              }) {
          @Override
          public byte[] getBody() throws AuthFailureError {
              return jsonObject.toString().getBytes();
          }

          @Override
          public Map<String, String> getHeaders() throws AuthFailureError {
                 HashMap<String, String> headers = new HashMap<>();
              headers.put("X-Authorization", sessionHandler.getAgentToken());
              headers.put("Content-Type", "application/json");
              headers.put("Authorization", "Basic dXNlcjpqQiQjYUJAMjA1NA==");
              return headers;
          }
      };
      requestQueue.add(req);
  }

  void sendRetrofitReq(){
      sessionHandler.showProgressDialog("Sending Request ....");
      final JSONObject jsonObject = new JSONObject();
//      startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
      JSONArray jsonArray = new JSONArray();
      try{
          Log.e("agentCode","ac"+sessionHandler.getAgentCode());
          jsonObject.put("agentCode",sessionHandler.getAgentCode());
          jsonObject.put("mobile","9813297782");

          jsonArray.put(jsonObject);
      }catch (Exception e){
          e.printStackTrace();
      }
      RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
//      retrofit2.Call<String> call = apiInterface.sendRetrofitOtprequest(body,"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBMDA1MDAwMSIsImF1ZGllbmNlIjoid2ViIiwiY3JlYXRlZCI6MTUwODIyMjcyODMzOSwiZXhwIjoxNTA4ODI3NTI4fQ.lAqF1g6Oil2fC8FRfK_ktR2J4oiNZDVsqmLStY855ZQxvH6whWkRI7nkxmeOzXJM912yMXaWgv_Sk4kzJgoRFA");
      retrofit2.Call<OTPmodel> call = apiInterface.sendRetrofitOtprequest(body,
              sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
              "application/json");
      call.enqueue(new retrofit2.Callback<OTPmodel>() {
          @Override
          public void onResponse(retrofit2.Call<OTPmodel> call, retrofit2.Response<OTPmodel> response) {
              sessionHandler.hideProgressDialog();
              if (String.valueOf(response.code()).equals("200")){
                  startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
                  Toast.makeText(InitialResetPassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
              }else {
                  try {
                      String jsonString = response.errorBody().string();

                      Log.d("here ","--=>"+jsonString);

                      JSONObject jsonObject = new JSONObject(jsonString);
                      Intent intent = new Intent(InitialResetPassword.this, ErrorDialogActivity.class);
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
}
