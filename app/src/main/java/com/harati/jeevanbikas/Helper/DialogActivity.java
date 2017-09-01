package com.harati.jeevanbikas.Helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;


public class DialogActivity extends AppCompatActivity {
     Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        ok= (Button)findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DialogActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
