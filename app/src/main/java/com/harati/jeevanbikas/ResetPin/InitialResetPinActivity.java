package com.harati.jeevanbikas.ResetPin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.ResetPassword.InitialResetPassword;
import com.harati.jeevanbikas.ResetPassword.ResetPassword;

public class InitialResetPinActivity extends AppCompatActivity implements View.OnClickListener  {
    Spinner spinner;
    Button initial_pin_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_reset_pin);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initial_pin_reset=(Button)findViewById(R.id.initial_pin_reset);
        initial_pin_reset.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int getVid = v.getId();
        switch (getVid){
            case R.id.initial_pin_reset:
                startActivity(new Intent(InitialResetPinActivity.this,ResetPin.class));
                break;
        }
    }
}
