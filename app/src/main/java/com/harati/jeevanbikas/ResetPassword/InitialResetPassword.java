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
                break;
        }
    }

    public void sendOtpRequest(){
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("agentCode",sessionHandler.getAgentCode());
            jsonObject.put("mobile","9802796417");
        }catch (Exception e){
            e.printStackTrace();
        }
        VolleyRequestHandler handler= new VolleyRequestHandler(getApplicationContext());
        handler.makePostRequest("requestotp?serialno=12345", jsonObject, new RequestListener() {
            @Override
            public void onSuccess(String response) {
                Log.d("response-----", response);
            }
        });

    }
}
