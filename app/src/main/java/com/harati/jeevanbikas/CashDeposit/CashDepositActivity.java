package com.harati.jeevanbikas.CashDeposit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.R;


public class CashDepositActivity extends AppCompatActivity {
    Spinner spinner;
    ImageView image;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);
        image = (ImageView) findViewById(R.id.image);
        spinner = (Spinner) findViewById(R.id.spinner);
        title = (TextView) findViewById(R.id.title);
        title.setText("Cash Deposit");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void setPage(String name) {
        Fragment fragment = null;
        switch (name) {
            case "home":
                fragment = new DepositFragment();
                break;
            default:
                fragment = new DepositFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }
}
