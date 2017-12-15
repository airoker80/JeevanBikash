package com.harati.jeevanbikas.RechargeTopup;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.harati.jeevanbikas.BaseActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;

public class RechargeTopup extends BaseActivity implements View.OnClickListener{

    Spinner spinner;
    ImageView topup_back;

    SessionHandler sessionHandler;
    Handler handler;
    Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_topup);
        topup_back=(ImageView)findViewById(R.id.topup_back);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        topup_back.setOnClickListener(this);

        Fragment fragment = new RechargeSlidingFragment();
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.recharge_frame, fragment);
//        finish();
        fragmentTransaction.commit();

        sessionHandler = new SessionHandler(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        int getID = v.getId();
        switch (getID){
            case R.id.topup_back:
                Intent intent =new Intent(this, MainActivity.class);
                intent.putExtra("msg","x");
                startActivity(intent);
                break;
        }
    }
}
