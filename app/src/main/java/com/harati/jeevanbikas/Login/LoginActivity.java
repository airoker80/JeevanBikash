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
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPin.ResetPin;
import com.harati.jeevanbikas.VolleyPackage.VolleyRequestHandler;

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
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                jeevanLogin();
            }
        });
    }

    private void jeevanLogin(){
        VolleyRequestHandler volleyRequestHandler = new VolleyRequestHandler(LoginActivity.this);
        helperListModelClasses.add(new HelperListModelClass("username",jb_username.getText().toString()));
        helperListModelClasses.add(new HelperListModelClass("password",jb_password.getText().toString()));
        String response = volleyRequestHandler.makePostRequest(JeevanBikashConfig.BASE_URL,helperListModelClasses);

        Log.d("response-","--->"+response+"<---");
    }
}
