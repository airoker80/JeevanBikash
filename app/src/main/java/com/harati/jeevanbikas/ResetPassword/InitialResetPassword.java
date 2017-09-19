package com.harati.jeevanbikas.ResetPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.harati.jeevanbikas.R;

public class InitialResetPassword extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    Button initial_password_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_reset_password);
        initial_password_reset=(Button)findViewById(R.id.initial_password_reset);
        initial_password_reset.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.spinner);
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
                startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
                break;
        }
    }
}
