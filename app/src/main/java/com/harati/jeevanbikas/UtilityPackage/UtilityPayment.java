package com.harati.jeevanbikas.UtilityPackage;

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

public class UtilityPayment extends BaseActivity implements View.OnClickListener {
    ImageView utility_back;
    Spinner spinner;

    SessionHandler sessionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_payment);
        utility_back=(ImageView)findViewById(R.id.image);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        utility_back.setOnClickListener(this);

        Fragment fragment = new UtilitySlidingFragment();
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.utility_frame, fragment);
        fragmentTransaction.commit();
//        finish();

        sessionHandler = new SessionHandler(this);


    }
    @Override
    public void onBackPressed() {
    }

    public void onbackpress(){
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int getID = v.getId();
        switch (getID){
            case R.id.image:
                onbackpress();
                break;
        }
    }
}
