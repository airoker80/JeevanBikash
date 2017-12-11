package com.harati.jeevanbikas.ActivityLogPackage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.harati.jeevanbikas.BalanceEnquiry.BalanceFragment;
import com.harati.jeevanbikas.BalanceEnquiry.FingerPrintFragment;
import com.harati.jeevanbikas.R;

public class AgentLogActivity extends AppCompatActivity {
    Spinner spinner;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_log);

        spinner = (Spinner) findViewById(R.id.spinner);
        image = (ImageView) findViewById(R.id.image);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        image.setOnClickListener(view -> onbackPressed());
        spinner.setAdapter(adapter);
        setPage("home");
    }

    public void onbackPressed() {
        super.onBackPressed();
    }

    public void setPage(String name) {
        Fragment fragment = null;
        switch (name) {
            case "home":
                fragment = new LogFingerFragment();
                break;
            default:
                fragment = new LogFingerFragment();
                break;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.agentLogFrame, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
    }
}
