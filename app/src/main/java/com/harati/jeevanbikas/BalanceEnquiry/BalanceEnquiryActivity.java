package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;


public class BalanceEnquiryActivity extends AppCompatActivity {
    Spinner spinner;
    TextView title;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_enquiry);
        spinner = (Spinner) findViewById(R.id.spinner);
        title = (TextView) findViewById(R.id.title);
        image = (ImageView) findViewById(R.id.image);
        title.setText("Balance Enquiry");
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onbackPressed();
            }
        });
        spinner.setAdapter(adapter);
        setPage("home");
    }
    public void onbackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        startActivity(new Intent(BalanceEnquiryActivity.this, MainActivity.class));
    }

    public void setPage(String name) {
        Fragment fragment = null;
        switch (name) {
            case "home":
                fragment = new BalanceFragment();
                break;
            default:
                fragment = new BalanceFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }

}
