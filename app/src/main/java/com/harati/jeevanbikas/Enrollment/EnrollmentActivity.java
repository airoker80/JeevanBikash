package com.harati.jeevanbikas.Enrollment;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.R;

public class EnrollmentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText enrollment_name, enrollment_age, enrollment_father_name, enrollment_spouse_name, enrollment_grandfather_name, enrollment_address, enrollment_mobile_number;
    RadioGroup enrollment_rg;
    RadioButton enrollment_male_rb, enrollment_female_rb;
    ImageView enrollment_back, enrollment_choose_img, enrollment_tick;
    String checked_string="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        enrollment_name = (EditText) findViewById(R.id.enrollment_name);
        enrollment_age = (EditText) findViewById(R.id.enrollment_age);
        enrollment_father_name = (EditText) findViewById(R.id.enrollment_father_name);
        enrollment_spouse_name = (EditText) findViewById(R.id.enrollment_spouse_name);
        enrollment_grandfather_name = (EditText) findViewById(R.id.enrollment_grandfather_name);
        enrollment_address = (EditText) findViewById(R.id.enrollment_address);
        enrollment_mobile_number = (EditText) findViewById(R.id.enrollment_mobile_number);

        enrollment_rg = (RadioGroup) findViewById(R.id.enrollment_rg);

        enrollment_male_rb = (RadioButton) findViewById(R.id.enrollment_male_rb);
        enrollment_female_rb = (RadioButton) findViewById(R.id.enrollment_female_rb);

        enrollment_back = (ImageView) findViewById(R.id.enrollment_back);
        enrollment_choose_img = (ImageView) findViewById(R.id.enrollment_choose_img);
        enrollment_tick = (ImageView) findViewById(R.id.enrollment_tick);

        enrollment_tick.setOnClickListener(this);
        enrollment_choose_img.setOnClickListener(this);
        enrollment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        enrollment_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.enrollment_female_rb:
                        checked_string=enrollment_female_rb.getText().toString();
                        break;
                    case R.id.enrollment_male_rb:
                        checked_string=enrollment_male_rb.getText().toString();
                        break;
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        int getID = v.getId();
        switch (getID) {
            case R.id.enrollment_tick:
                checkAllField();
                break;
            case R.id.enrollment_choose_img:
                break;
        }
    }

    private void checkAllField() {
        String enrollment_name_txt="", enrollment_age_txt="", enrollment_father_name_txt="", enrollment_spouse_name_txt="",
                enrollment_grandfather_name_txt="",
                enrollment_address_txt="", enrollment_mobile_number_txt="", enrollment_male_rb_txt="", enrollment_female_rb_txt="";

        enrollment_name_txt=enrollment_name.getText().toString();
        enrollment_age_txt=enrollment_age.getText().toString();
        enrollment_father_name_txt=enrollment_father_name.getText().toString();
        enrollment_spouse_name_txt=enrollment_spouse_name.getText().toString();
        enrollment_grandfather_name_txt=enrollment_grandfather_name.getText().toString();
        enrollment_address_txt=enrollment_address.getText().toString();
        enrollment_mobile_number_txt=enrollment_mobile_number.getText().toString();



        if (enrollment_name_txt.equals("")|enrollment_age_txt.equals("")|enrollment_father_name_txt.equals("")|
        enrollment_spouse_name_txt.equals("")|enrollment_grandfather_name_txt.equals("")|enrollment_address_txt.equals("")
                |enrollment_mobile_number_txt.equals("")|checked_string.equals("")){
            /*final LayoutInflater inflater = LayoutInflater.from(EnrollmentActivity.this);
            View dialogView = inflater.inflate(R.layout.dialog_message, null);
            final AlertDialog builder = new AlertDialog.Builder(EnrollmentActivity.this)
                    .create();

            builder.setView(dialogView);
            builder.show();*/
            Intent intent= new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);
            startActivity(intent);
        }else {
            Intent intent= new Intent(EnrollmentActivity.this, DialogActivity.class);
            startActivity(intent);
        }
        /*final LayoutInflater inflater = LayoutInflater.from(EnrollmentActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_message, null);
        final AlertDialog builder = new AlertDialog.Builder(EnrollmentActivity.this)
                .create();

        builder.setView(dialogView);
        builder.show();*/
    }
}