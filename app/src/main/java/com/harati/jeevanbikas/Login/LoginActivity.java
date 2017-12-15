package com.harati.jeevanbikas.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CGEditText;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPin.ResetPin;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoginModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SystemApiResponseModel;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {
    ApiSessionHandler apiSessionHandler;
    ApiInterface apiInterface;
    CenturyGothicTextView setupUrl;
    Retrofit retrofit ;
    ImageView image;



    SessionHandler sessionHandler;
    TextView reset_pin;
    EditText jb_username, jb_password;
    List<HelperListModelClass> helperListModelClasses = new ArrayList<>();
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiSessionHandler = new ApiSessionHandler(this);
        setupUrl=(CenturyGothicTextView)findViewById(R.id.setupUrl);

        image = (ImageView) findViewById(R.id.image);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        jb_username = (EditText) findViewById(R.id.jb_username);
        jb_password = (EditText) findViewById(R.id.jb_password);
/*        try {
            JSONArray jsonArray = new JSONArray(sessionHandler.API_JSON);
            for (int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("apiName").equals("AGENT LOGIN")){
                    String loginUrl = jsonObject.getString("baseUrl");

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        Log.d("getAGENT_LOGIN",apiSessionHandler.getAGENT_LOGIN());

        if (getPackageName().equals("com.harati.BLB")){
            image.setVisibility(View.INVISIBLE);
            jb_username.setText("A0262276");
            jb_password.setText("#3c!fqfF");
        }
        retrofit = MyApplication.getRetrofitInstance(apiSessionHandler.getAGENT_LOGIN());
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(LoginActivity.this);
/*        if (sessionHandler.isUserLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/
        reset_pin = (TextView) findViewById(R.id.reset_pin);

        reset_pin.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ResetPin.class)));
//        loginBtn.setOnClickListener(v -> startActivity(new Intent(this,MainActivity.class)));
        loginBtn.setOnClickListener(v -> {
            if (jb_username.getText().toString().equals("")|jb_password.getText().toString().equals("")){
                if (jb_username.getText().toString().equals("")&jb_password.getText().toString().equals("")){
                    jb_username.setError("Please enter the username ");
                    jb_password.setError("Please enter the password");
                }
                if (jb_username.getText().toString().equals("")){
                    jb_username.setError("Please enter the username ");
                }
                if (jb_password.getText().toString().equals("")){
                    jb_password.setError("Please enter the password");
                }
            }else{
                loginWithRetrofit();
            }
        });

//        setupUrl.setOnClickListener(v -> setUpUrl());
    }

        void loginWithRetrofit(){
        sessionHandler.showProgressDialog("Sending Request ...");
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", jb_username.getText().toString());
            jsonObject.put("password", jb_password.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Call<LoginModel> call;
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        if (sessionHandler.getAgentSerialNo().equals(null)){
            Toast.makeText(this, "Plsease Setup URL's First", Toast.LENGTH_LONG).show();
        }else {
            call = apiInterface.authenticate(body,apiSessionHandler.getAGENT_LOGIN(),apiSessionHandler.getAgentCode());
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    sessionHandler.hideProgressDialog();
                    if (response.isSuccessful()){
                        try {
                            Log.d("response success","------"+response.raw().toString());
                            Log.d("response success","------"+response.body().getToken());
                            LoginModel loginModel = response.body();
                            String token = loginModel.getToken();
                            String code = loginModel.getCode();
                            String name = loginModel.getName();
                            String branch = loginModel.getBranch();
                            String photo = loginModel.getPhoto();
//                        String balance = loginModel.getBalance().toString();
                            String passwordChangeReqd = loginModel.getPasswordChangeReqd().toString();
                            String pinChangeReqd = loginModel.getPasswordChangeReqd().toString();
                            String agentPin = "12348";

                            boolean passBol = false, pinBol = false;
                            passBol = passwordChangeReqd.equals("true");

                            pinBol = pinChangeReqd.equals("true");

                            sessionHandler.saveLoginInformation(code, name, branch, passBol, pinBol,token,agentPin,photo);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("msg","y");
//                            intent.putExtra("userphoto",photo);
                            startActivity(intent);
                            finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        try {
                            String jsonString = response.errorBody().string();

                            Log.d("here ","--=>"+jsonString);

                            JSONObject jsonObject = new JSONObject(jsonString);
                            Intent intent = new Intent(LoginActivity.this, ErrorDialogActivity.class);
                            intent.putExtra("msg",jsonObject.getString("message"));
                            startActivity(intent);
                        }catch (Exception e){
                            Intent intent = new Intent(LoginActivity.this, ErrorDialogActivity.class);
                            intent.putExtra("msg","Mistake in URL");
                            startActivity(intent);
                            e.printStackTrace();

                        }

                    }

                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    sessionHandler.hideProgressDialog();
                    Intent intent = new Intent(LoginActivity.this, ErrorDialogActivity.class);
                    intent.putExtra("msg","Network Error");
                    startActivity(intent);
                    Log.d("response failure","------"+t.toString());

                }
            });
        }

    }


    private void setUpUrl(){


        View view = getLayoutInflater().inflate(R.layout.dialog_setup_url,null);
        final AlertDialog builder = new AlertDialog.Builder(LoginActivity.this)
                .setPositiveButton("Save Url", null)
                .setNegativeButton("CANCEL", null)
                .setView(view)
                .setTitle("Select Place")
                .create();

        CGEditText baseUrl = (CGEditText)view.findViewById(R.id.baseUrl);
        CGEditText serialNo = (CGEditText)view.findViewById(R.id.serialNo);

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                builder.dismiss();
                JeevanBikashConfig.BASE_URL="";
                JeevanBikashConfig.BASE_URL=baseUrl.getText().toString();

                Log.d("BaseUrl",JeevanBikashConfig.BASE_URL);
                sessionHandler.saveAgentUrlInfo(serialNo.getText().toString());

                try {
                    retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
                    apiInterface = retrofit.create(ApiInterface.class);
                }catch (Exception e){
                    e.printStackTrace();
                    Intent intent = new Intent(LoginActivity.this, ErrorDialogActivity.class);
                    intent.putExtra("msg","Please Enter Valid URL");
                    startActivity(intent);
                }

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });
        builder.show();
    }
}
