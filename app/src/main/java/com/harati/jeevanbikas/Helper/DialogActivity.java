package com.harati.jeevanbikas.Helper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;


public class DialogActivity extends AppCompatActivity {
    TextView msgDetail;
     Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String extraMesage = getIntent().getStringExtra("msg");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        msgDetail=(TextView)findViewById(R.id.msgDetail);
        try {
            msgDetail.setText(extraMesage);
        }catch (Exception e){
            e.printStackTrace();
        }
        ok= (Button)findViewById(R.id.ok);
        ok.setOnClickListener(view -> {
//            startActivity(new Intent(DialogActivity.this, MainActivity.class));
            finish();
        });
    }
}
