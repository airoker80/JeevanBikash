package com.harati.jeevanbikas.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.harati.jeevanbikas.CashDeposit.CashDepositActivity;
import com.harati.jeevanbikas.R;


public class ErrorDialogActivity extends AppCompatActivity {
    TextView msgDetail;
     Button ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_dialog_layout);
        msgDetail=(TextView)findViewById(R.id.msgDetail);
        try {
            Intent intent = getIntent();
            String msg = intent.getExtras().getString("msg");
            String tag = intent.getExtras().getString("tag");
            msgDetail.setText(msg);
            if (tag.equals("ACTF")){
                super.onBackPressed();
            }
    }catch (Exception e){
        e.printStackTrace();
    }

        ok= (Button)findViewById(R.id.ok);
        ok.setOnClickListener(view -> finish());
    }
}
