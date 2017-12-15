package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.harati.jeevanbikas.BaseActivity;
import com.harati.jeevanbikas.Enrollment.EnrollmentActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;


public class BalanceEnquiryActivity extends BaseActivity {
    Spinner spinner;
    TextView title;
    ImageView image;


    SessionHandler sessionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopHandler();
        setContentView(R.layout.activity_balance_enquiry);
        spinner = (Spinner) findViewById(R.id.spinner);

        title = (TextView) findViewById(R.id.title);

        title.setTypeface(MainActivity.centuryGothic);
        image = (ImageView) findViewById(R.id.image);
        title.setText("Balance Enquiry");
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.language, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        image.setOnClickListener(view -> onbackPressed());
        spinner.setAdapter(adapter);
        setPage("home");

        sessionHandler = new SessionHandler(this);

    }
    public void onbackPressed() {
        confirmBack();
    }
    @Override
    public void onBackPressed() {
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
//        transaction.addToBackStack(null);
        transaction.commit();
    }


    void confirmBack(){
        Log.e("backpressed","bp");
        View view = getLayoutInflater().inflate(R.layout.dialog_ask_permission,null);
        TextView askPermission = (TextView)view.findViewById(R.id.askPermission);
        askPermission.setText("Are you Sure you want to go back??");
        final AlertDialog builder = new AlertDialog.Builder(BalanceEnquiryActivity.this)
                .setPositiveButton("Yes", null)
                .setNegativeButton("CANCEL", null)
                .setTitle("Are you Sure you want to go back?")
                .create();

        builder.setOnShowListener(dialog -> {

            final Button btnAccept = builder.getButton(
                    AlertDialog.BUTTON_POSITIVE);

            btnAccept.setOnClickListener(v -> {
                super.onBackPressed();
                Log.e("backpressed","bp");
                builder.dismiss();

            });

            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

            btnDecline.setOnClickListener(v -> builder.dismiss());
        });

        builder.show();
    }
}
