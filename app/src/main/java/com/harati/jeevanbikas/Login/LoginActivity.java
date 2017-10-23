package com.harati.jeevanbikas.Login;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPin.ResetPin;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoginModel;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    ApiInterface apiInterface;

    SessionHandler sessionHandler;
    TextView reset_pin;
    EditText jb_username, jb_password;
    List<HelperListModelClass> helperListModelClasses = new ArrayList<HelperListModelClass>();
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        jb_username = (EditText) findViewById(R.id.jb_username);
        jb_password = (EditText) findViewById(R.id.jb_password);
        apiInterface = RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(LoginActivity.this);
        if (sessionHandler.isUserLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        reset_pin = (TextView) findViewById(R.id.reset_pin);

        reset_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPin.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                jeevanLogin();
            loginWithRetrofit();
//           startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private void jeevanLogin() {

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", jb_username.getText().toString());
            jsonObject.put("password", jb_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VolleyRequestHandler handler = new VolleyRequestHandler(getApplicationContext());
        handler.makePostRequest("login?serialno=12348", jsonObject, new RequestListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("response-----", response);

                try {
                    JSONObject loginResponse = new JSONObject(response);
                    String token = loginResponse.getString("token");
                    String code = loginResponse.getString("code");
                    String name = loginResponse.getString("name");
                    String branch = loginResponse.getString("branch");
                    String balance = loginResponse.getString("balance");
                    String passwordChangeReqd = loginResponse.getString("passwordChangeReqd");
                    String pinChangeReqd = loginResponse.getString("pinChangeReqd");
                    String agentPin = "12348";

                    boolean passBol = false, pinBol = false;
                    if (passwordChangeReqd.equals("true")) {
                        passBol = true;
                    } else {
                        passBol = false;
                    }

                    if (pinChangeReqd.equals("true")) {
                        pinBol = true;
                    } else {
                        pinBol = false;
                    }

                    sessionHandler.saveLoginInformation(code, name, branch, passBol, pinBol,token,agentPin);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String response) {
             /*   View view = new View(LoginActivity.this);
                Snackbar snackbar = Snackbar
                        .make(view, "Invalid Username or Password", Snackbar.LENGTH_LONG);
                snackbar.show();
                View snackbarview = snackbar.getView();
                TextView textView = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);*/
             Log.e("error",response);
            }
        });

    }

    void loginWithRetrofit(){
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", jb_username.getText().toString());
            jsonObject.put("password", jb_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        Call<LoginModel> call = apiInterface.authenticate(body);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()){
                    try {
                        Log.d("response success","------"+response.raw().toString());
                        Log.d("response success","------"+response.body().getToken());
                        LoginModel loginModel = response.body();
                        String token = loginModel.getToken();
                        String code = loginModel.getCode();
                        String name = loginModel.getName();
                        String branch = loginModel.getBranch();
//                        String balance = loginModel.getBalance().toString();
                        String passwordChangeReqd = loginModel.getPasswordChangeReqd().toString();
                        String pinChangeReqd = loginModel.getPasswordChangeReqd().toString();
                        String agentPin = "12348";

                        boolean passBol = false, pinBol = false;
                        if (passwordChangeReqd.equals("true")) {
                            passBol = true;
                        } else {
                            passBol = false;
                        }

                        if (pinChangeReqd.equals("true")) {
                            pinBol = true;
                        } else {
                            pinBol = false;
                        }

                        sessionHandler.saveLoginInformation(code, name, branch, passBol, pinBol,token,agentPin);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("response failure","------"+t.toString());

            }
        });
    }

}
