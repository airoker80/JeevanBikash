package com.harati.jeevanbikas.Enrollment;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentActivity extends AppCompatActivity implements View.OnClickListener {
    
    SessionHandler sessionHandler ;
    ApiInterface apiInterface ;

    EditText enrollment_name, enrollment_age, enrollment_father_name, enrollment_spouse_name,
            enrollment_grandfather_name, enrollment_address,
            enrollment_mobile_number,enrollment_czn;
    RadioGroup enrollment_rg;
    RadioButton enrollment_male_rb, enrollment_female_rb;
    ImageView enrollment_back, enrollment_choose_img, enrollment_tick,enrollment_cross;
    String checked_string="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);
        
        sessionHandler = new SessionHandler(EnrollmentActivity.this);
        apiInterface = RetrofitClient.getApiService();
        
        enrollment_name = (EditText) findViewById(R.id.enrollment_name);
        enrollment_age = (EditText) findViewById(R.id.enrollment_age);
        enrollment_father_name = (EditText) findViewById(R.id.enrollment_father_name);
        enrollment_spouse_name = (EditText) findViewById(R.id.enrollment_spouse_name);
        enrollment_grandfather_name = (EditText) findViewById(R.id.enrollment_grandfather_name);
        enrollment_address = (EditText) findViewById(R.id.enrollment_address);
        enrollment_mobile_number = (EditText) findViewById(R.id.enrollment_mobile_number);
        enrollment_czn = (EditText) findViewById(R.id.enrollment_czn);

        enrollment_rg = (RadioGroup) findViewById(R.id.enrollment_rg);

        enrollment_male_rb = (RadioButton) findViewById(R.id.enrollment_male_rb);
        enrollment_female_rb = (RadioButton) findViewById(R.id.enrollment_female_rb);

        enrollment_back = (ImageView) findViewById(R.id.enrollment_back);
        enrollment_choose_img = (ImageView) findViewById(R.id.enrollment_choose_img);
        enrollment_tick = (ImageView) findViewById(R.id.enrollment_tick);
        enrollment_cross = (ImageView) findViewById(R.id.enrollment_cross);

        enrollment_tick.setOnClickListener(this);
        enrollment_choose_img.setOnClickListener(this);
        enrollment_cross.setOnClickListener(this);
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
            case R.id.enrollment_cross:
                startActivity(new Intent(EnrollmentActivity.this, MainActivity.class));
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
/*            Intent intent= new Intent(EnrollmentActivity.this, DialogActivity.class);
            startActivity(intent);*/
            sendEnrollmentPostRequest();
        }
    }


    private  void  sendEnrollmentPostRequest(){


        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name",enrollment_name.getText().toString());
            jsonObject.put("age",enrollment_age.getText().toString());
            jsonObject.put("sex",checked_string);
            jsonObject.put("caste","muslim");
            jsonObject.put("father",enrollment_father_name.getText().toString());
            jsonObject.put("spouse",enrollment_spouse_name.getText().toString());
            jsonObject.put("grandfather",enrollment_grandfather_name.getText().toString());
            jsonObject.put("address",enrollment_address.getText().toString());
            jsonObject.put("mobile",enrollment_mobile_number.getText().toString());
            jsonObject.put("citizenshipno",enrollment_czn.getText().toString());
            jsonObject.put("finger1","1234");
            jsonObject.put("finger2","1234");
            jsonObject.put("finger3","1234");
            jsonObject.put("photo","data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");
            jsonObject.put("idProofDoc","data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");
            jsonObject.put("agentcode",sessionHandler.getAgentCode());



//            jsonObject.put("name","Aftab Alam");
//            jsonObject.put("age",31);
//            jsonObject.put("sex","Male");
//            jsonObject.put("caste","Muslim");
//            jsonObject.put("father","XYZ");
//            jsonObject.put("spouse","ABC");
//            jsonObject.put("grandfather","PQR");
//            jsonObject.put("address","Rani Biratnagar-22");
//            jsonObject.put("mobile","9802796417");
//            jsonObject.put("citizenshipno","Abc123");
//            jsonObject.put("finger1","1234");
//            jsonObject.put("finger2","1234");
//            jsonObject.put("finger3","1234");
//            jsonObject.put("photo","data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");
//            jsonObject.put("idProofDoc","data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");
//            jsonObject.put("agentcode",sessionHandler.getAgentCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SuccesResponseModel> call = apiInterface.sendEnrollmentRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<SuccesResponseModel>() {
            @Override
            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {
                sessionHandler.hideProgressDialog();
                Log.d("code","-->"+response.code());
                if (String.valueOf(response.code()).equals("200")){
                    if (response.body().getStatus().equals("Success")){
                        Intent intent = new Intent(EnrollmentActivity.this,DialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(EnrollmentActivity.this,ErrorDialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }


                }else {
                    sessionHandler.hideProgressDialog();
                    Intent intent = new Intent(EnrollmentActivity.this,DialogActivity.class);
                    intent.putExtra("msg","Error in Fields");
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(EnrollmentActivity.this,DialogActivity.class);
                intent.putExtra("msg","Connection Error");
                startActivity(intent);
            }
        });
    }
}