package com.harati.jeevanbikas.AgentDashboard;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPassword.InitialResetPassword;
import com.harati.jeevanbikas.ResetPassword.ResetPassword;
import com.harati.jeevanbikas.ResetPin.InitialResetPinActivity;
import com.harati.jeevanbikas.ResetPin.ResetPin;

public class AgentDashboardActivity extends AppCompatActivity  implements View.OnClickListener{
    ImageView ad_back;

    Button resetPin,resetPassword;

    SessionHandler sessionHandler;

    Handler handler;
    Runnable r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_dashboard);

        resetPin=(Button)findViewById(R.id.resetPin);
        resetPassword=(Button)findViewById(R.id.resetPassword);

        ad_back=(ImageView) findViewById(R.id.ad_back);
        ad_back.setOnClickListener(v -> onBackPressed());

        sessionHandler = new SessionHandler(this);

        resetPassword.setOnClickListener(this);
        resetPin.setOnClickListener(this);

        handler=new Handler();
        r=() -> sessionHandler.logoutUser();

        startHandler();
    }

    @Override
    public void onClick(View v) {
        int vID = v.getId();
        switch (vID){
            case R.id.resetPin:
                startActivity(new Intent(AgentDashboardActivity.this, InitialResetPinActivity.class));
                break;
            case R.id.resetPassword:
                startActivity(new Intent(AgentDashboardActivity.this, InitialResetPassword.class));
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
        handler.postDelayed(r, 60*1000); //for 5 minutes
    }
    public void stopHandler() {
        Log.e("Handler","Stoped");
        handler.removeCallbacks(r);
    }

    @Override
    protected void onDestroy() {
        stopHandler();
        super.onDestroy();
    }
}
