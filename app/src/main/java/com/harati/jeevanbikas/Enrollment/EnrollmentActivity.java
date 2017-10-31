package com.harati.jeevanbikas.Enrollment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollmentActivity extends AppCompatActivity implements View.OnClickListener {

    Button fileNameButton;
    String filefieldTagName, imgFilefieldTagName, encodedString, compressedFilefieldTagName,fieldTagName;
    TextView testTextview,profileText,docText;
    String imgPath;
    private static int RESULT_LOAD_IMG = 12;
    File imgFile;
    ImageView profileImage;
    Bitmap updatedImageBitmap;
    Bitmap bitmap;

    SessionHandler sessionHandler;
    ApiInterface apiInterface;

    EditText enrollment_name, enrollment_age, enrollment_father_name, enrollment_spouse_name,
            enrollment_grandfather_name, enrollment_address,
            enrollment_mobile_number, enrollment_czn;
    RadioGroup enrollment_rg;
    RadioButton enrollment_male_rb, enrollment_female_rb;
    ImageView enrollment_back, enrollment_choose_img, enrollment_tick, enrollment_cross,req_doc;
    LinearLayout getFile;
    String checked_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        sessionHandler = new SessionHandler(EnrollmentActivity.this);
        apiInterface = RetrofitClient.getApiService();

        profileText=(TextView)findViewById(R.id.profileText);


        getFile=(LinearLayout) findViewById(R.id.getFile);

                docText=(TextView)findViewById(R.id.docText);
        getFile.setOnClickListener(this);


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
        req_doc = (ImageView) findViewById(R.id.req_doc);
        req_doc.setOnClickListener(this);

        enrollment_tick.setOnClickListener(this);
        enrollment_choose_img.setOnClickListener(this);
        enrollment_cross.setOnClickListener(this);
        enrollment_back.setOnClickListener(view -> onBackPressed());
        enrollment_rg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.enrollment_female_rb:
                    checked_string = enrollment_female_rb.getText().toString();
                    break;
                case R.id.enrollment_male_rb:
                    checked_string = enrollment_male_rb.getText().toString();
                    break;
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
                enrollment_choose_img.setTag("profile");
                String file = "";
                TextView testView = new TextView(EnrollmentActivity.this);
                testView.setVisibility(View.GONE);
                profileText.setTag("profile");
                Button button = new Button(EnrollmentActivity.this);
                button.setTag("profile");
                loadImageFromGallery(button,"profile","profile",profileText);
                break;
            case R.id.req_doc:
                req_doc.setTag("doc");
                String file1 = "";
                TextView testView1 = new TextView(EnrollmentActivity.this);
                testView1.setVisibility(View.GONE);
                docText.setTag("doc");
                Button button1 = new Button(EnrollmentActivity.this);
                button1.setTag("doc");
                loadImageFromGallery(button1,"doc","doc",docText);
                break;
            case R.id.enrollment_cross:
                startActivity(new Intent(EnrollmentActivity.this, MainActivity.class));
                break;
        }
    }

    private void checkAllField() {
        String enrollment_name_txt = "", enrollment_age_txt = "", enrollment_father_name_txt = "", enrollment_spouse_name_txt = "",
                enrollment_grandfather_name_txt = "",
                enrollment_address_txt = "", enrollment_mobile_number_txt = "", enrollment_male_rb_txt = "", enrollment_female_rb_txt = "";

        enrollment_name_txt = enrollment_name.getText().toString();
        enrollment_age_txt = enrollment_age.getText().toString();
        enrollment_father_name_txt = enrollment_father_name.getText().toString();
        enrollment_spouse_name_txt = enrollment_spouse_name.getText().toString();
        enrollment_grandfather_name_txt = enrollment_grandfather_name.getText().toString();
        enrollment_address_txt = enrollment_address.getText().toString();
        enrollment_mobile_number_txt = enrollment_mobile_number.getText().toString();


        if (enrollment_name_txt.equals("") | enrollment_age_txt.equals("") | enrollment_father_name_txt.equals("") |
                enrollment_spouse_name_txt.equals("") | enrollment_grandfather_name_txt.equals("") | enrollment_address_txt.equals("")
                | enrollment_mobile_number_txt.equals("") | checked_string.equals("")) {
            /*final LayoutInflater inflater = LayoutInflater.from(EnrollmentActivity.this);
            View dialogView = inflater.inflate(R.layout.dialog_message, null);
            final AlertDialog builder = new AlertDialog.Builder(EnrollmentActivity.this)
                    .create();

            builder.setView(dialogView);
            builder.show();*/
            Intent intent = new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);
            startActivity(intent);
        } else {
/*            Intent intent= new Intent(EnrollmentActivity.this, DialogActivity.class);
            startActivity(intent);*/
            sendEnrollmentPostRequest();
        }
    }


    private void sendEnrollmentPostRequest() {


        TextView profiletext= new TextView(EnrollmentActivity.this);
        TextView imagetext= new TextView(EnrollmentActivity.this);

        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", enrollment_name.getText().toString());
            jsonObject.put("age", enrollment_age.getText().toString());
            jsonObject.put("sex", checked_string);
            jsonObject.put("caste", "muslim");
            jsonObject.put("father", enrollment_father_name.getText().toString());
            jsonObject.put("spouse", enrollment_spouse_name.getText().toString());
            jsonObject.put("grandfather", enrollment_grandfather_name.getText().toString());
            jsonObject.put("address", enrollment_address.getText().toString());
            jsonObject.put("mobile", enrollment_mobile_number.getText().toString());
            jsonObject.put("citizenshipno", enrollment_czn.getText().toString());
            jsonObject.put("finger1", "1234");
            jsonObject.put("finger2", "1234");
            jsonObject.put("finger3", "1234");

/*            jsonObject.put("photo", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");
            jsonObject.put("idProofDoc", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");  */

            jsonObject.put("photo", profileText.getText().toString());
            jsonObject.put("idProofDoc", docText.getText().toString());

            jsonObject.put("agentcode", sessionHandler.getAgentCode());


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
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
        retrofit2.Call<SuccesResponseModel> call = apiInterface.sendEnrollmentRequest(body,
                sessionHandler.getAgentToken(), "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<SuccesResponseModel>() {
            @Override
            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")) {
                    if (response.body().getStatus().equals("Success")) {
                        Intent intent = new Intent(EnrollmentActivity.this, DialogActivity.class);
                        intent.putExtra("msg", response.body().getMessage());
                        startActivity(intent);
                    } else {
                        try {
                            String jsonString = response.errorBody().string();

                            Log.d("here ", "--=>" + jsonString);

                            JSONObject jsonObject = new JSONObject(jsonString);
                            Intent intent = new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);
                            intent.putExtra("msg", jsonObject.getString("message"));
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } else {
                    sessionHandler.hideProgressDialog();
                    Intent intent = new Intent(EnrollmentActivity.this, DialogActivity.class);
                    intent.putExtra("msg", "Error in Fields");
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(EnrollmentActivity.this, DialogActivity.class);
                intent.putExtra("msg", "Connection Error");
                startActivity(intent);
            }
        });
    }

    private void startYourCameraIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        EnrollmentActivity.this.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK
                && null != data) {
            // Get the Image from data

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imgPath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imgView = enrollment_choose_img;
//            imgView.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            int height = bitmap.getHeight(), width = bitmap.getWidth();

            if (height > 1280 && width > 960) {
                imgFile=new File(imgPath);
                Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);
                updatedImageBitmap = bitmap;
//                imgView.setImageBitmap(imgbitmap);
                System.out.println("Need to resize");
            } else {
                imgFile=new File(imgPath);
//                imgView.setImageBitmap(bitmap);
                updatedImageBitmap = bitmap;
                System.out.println("WORKS");
            }

            String filefieldTagNameSegments[] = imgPath.split("/");
            filefieldTagName = filefieldTagNameSegments[filefieldTagNameSegments.length - 1];
            imgFilefieldTagName=filefieldTagName;
            fileNameButton.setText(filefieldTagName);
            Log.i("msg ", "filefieldTagName : " + filefieldTagName + " imgPath : " + imgPath);

            encodeImagetoString();


        } else {
            Toast.makeText(EnrollmentActivity.this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void encodeImagetoString() {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
                sessionHandler.showProgressDialog("Getting Image ..");
            }

            @Override
            protected String doInBackground(Void... params) {
                BitmapFactory.Options options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Must compress the Image to reduce image size to make upload easy
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//				bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Encode Image to String
                encodedString = Base64.encodeToString(byte_arr, 0);
                compressedFilefieldTagName = encodedString;
//                requestParams.put(fieldTagName,encodedString);
                Log.d("adad", compressedFilefieldTagName);
                return "";
            }

            @Override
            protected void onPostExecute(String msg) {
                sessionHandler.hideProgressDialog();
                Log.d("fieldTagName", fieldTagName + "===== \n" + encodedString);
                testTextview.setText("data:image/jpeg;base64,"+encodedString);

//                updateUserProfileImage();
            }
        }.execute(null, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startYourCameraIntent();

                } else {
                    Toast.makeText(EnrollmentActivity.this, "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public void loadImageFromGallery(Button imageView, String file, String imageFilefieldTagName, TextView testView) {
        fileNameButton=imageView;
        imgFilefieldTagName=imageFilefieldTagName;
        testTextview = testView;
//        compressedFilefieldTagName=file;
        fieldTagName=file;
        final int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                startYourCameraIntent();
            }
        }else {
            startYourCameraIntent();
        }

    }
}