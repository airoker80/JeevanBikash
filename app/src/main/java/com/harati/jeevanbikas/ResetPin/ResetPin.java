package com.harati.jeevanbikas.ResetPin;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;

public class ResetPin extends AppCompatActivity {
    Spinner spinner;

    SessionHandler sessionHandler;
    Handler handler;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sessionHandler = new SessionHandler(this);

        handler=new Handler();
        r=() -> sessionHandler.logoutUser();

        startHandler();
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
