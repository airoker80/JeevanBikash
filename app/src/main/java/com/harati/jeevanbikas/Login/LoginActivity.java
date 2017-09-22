package com.harati.jeevanbikas.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPin.ResetPin;
import com.harati.jeevanbikas.Volley.RequestListener;
import com.harati.jeevanbikas.Volley.VolleyRequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
    TextView reset_pin;
    EditText jb_username,jb_password;
    List<HelperListModelClass> helperListModelClasses=new ArrayList<HelperListModelClass>();
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        jb_username=(EditText)findViewById(R.id.jb_username);
        jb_password=(EditText)findViewById(R.id.jb_password);

        reset_pin=(TextView) findViewById(R.id.reset_pin);

        reset_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPin.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                jeevanLogin();

            }
        });
    }

    private void jeevanLogin(){

        final JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("username", "A0020001");
            jsonObject.put("password", "Aft@12345");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VolleyRequestHandler handler= new VolleyRequestHandler(getApplicationContext());
        handler.makePostRequest("login?serialno=12345", jsonObject, new RequestListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("response-----", response);
            }
        });

    }
}
