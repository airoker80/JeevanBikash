package com.harati.jeevanbikas.CashWithDrawl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.BaseActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;


public class CashWithDrawlActivity extends BaseActivity {
    Spinner spinner;
    ImageView image;
    TextView title;

    SessionHandler sessionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);
        image= (ImageView)findViewById(R.id.image);
        spinner = (Spinner) findViewById(R.id.spinner);
        title = (TextView) findViewById(R.id.title);
        title.setText("Cash Withdrawl");
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
       setPage("home");
        image.setOnClickListener(view -> onBackPressed());

        sessionHandler = new SessionHandler(this);
    }

    @Override
    public void onBackPressed() {
    }

    public void backpressed() {
        super.onBackPressed();
    }
    public void setPage(String name) {
        Fragment fragment = null;
        switch (name) {
            case "home":
                fragment = new CashFragment();
                break;
            default:
                fragment = new CashFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }
}
