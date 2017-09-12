package com.harati.jeevanbikas.KycPackage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.CashDeposit.DepositFragment;
import com.harati.jeevanbikas.R;

public class KycActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView image;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);
        image = (ImageView) findViewById(R.id.topup_back);
        spinner = (Spinner) findViewById(R.id.spinner);
        title = (TextView) findViewById(R.id.title);
//        title.setText("Cash Deposit");
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setPage("home");
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setPage(String name) {
        Fragment fragment = null;
        switch (name) {
            case "home":
                fragment = new KycFormFragment();
                break;
            default:
                fragment = new KycFormFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.kyc_frame, fragment);
        transaction.commit();
    }
}
