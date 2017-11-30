package com.harati.jeevanbikas.Enrollment;import android.Manifest;import android.app.Activity;import android.app.Dialog;import android.app.ProgressDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.content.pm.PackageManager;import android.database.Cursor;import android.graphics.Bitmap;import android.graphics.BitmapFactory;import android.graphics.Color;import android.graphics.Matrix;import android.net.Uri;import android.os.AsyncTask;import android.os.Build;import android.provider.MediaStore;import android.support.annotation.IdRes;import android.support.annotation.NonNull;import android.support.v4.app.ActivityCompat;import android.support.v4.content.ContextCompat;import android.support.v7.app.AlertDialog;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.util.Base64;import android.util.Log;import android.view.View;import android.view.Window;import android.widget.ArrayAdapter;import android.widget.Button;import android.widget.EditText;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.RadioButton;import android.widget.RadioGroup;import android.widget.Spinner;import android.widget.TextView;import android.widget.Toast;import com.harati.jeevanbikas.Helper.ApiSessionHandler;import com.harati.jeevanbikas.Helper.CenturyGothicTextView;import com.harati.jeevanbikas.Helper.DialogActivity;import com.harati.jeevanbikas.Helper.ErrorDialogActivity;import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;import com.harati.jeevanbikas.Helper.SessionHandler;import com.harati.jeevanbikas.MainPackage.MainActivity;import com.harati.jeevanbikas.MyApplication;import com.harati.jeevanbikas.R;import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;import com.harati.jeevanbikas.Retrofit.RetrofitModel.CastModel;import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoanDetailsModel;import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;import org.json.JSONObject;import java.io.ByteArrayOutputStream;import java.io.File;import java.io.IOException;import java.util.ArrayList;import java.util.List;import okhttp3.RequestBody;import retrofit2.Call;import retrofit2.Callback;import retrofit2.Response;import retrofit2.Retrofit;import static java.security.AccessController.getContext;public class EnrollmentActivity extends AppCompatActivity implements View.OnClickListener {    List<CastModel> castModelList = new ArrayList<>();    List<String> castStringList = new ArrayList<>();    private static final int CAMERA_REQUEST = 1888;    ApiSessionHandler apiSessionHandler;    ImageView setInImage;    Retrofit retrofit;    Bitmap cameraBitmap;    Button fileNameButton;    String filefieldTagName, imgFilefieldTagName, encodedString, compressedFilefieldTagName, fieldTagName;    TextView testTextview, profileText, docText;    String imgPath;    Spinner castSpinner;    private static int RESULT_LOAD_IMG = 12;    File imgFile;    ImageView profileImage;    Bitmap updatedImageBitmap;    Bitmap bitmap;    SessionHandler sessionHandler;    ApiInterface apiInterface;    EditText enrollment_name, enrollment_age, enrollment_father_name, enrollment_spouse_name,            enrollment_grandfather_name, enrollment_address,            enrollment_mobile_number, enrollment_czn, enrollment_caste;    RadioGroup enrollment_rg;    RadioButton enrollment_male_rb, enrollment_female_rb;    ImageView enrollment_back, enrollment_choose_img, enrollment_tick, enrollment_cross, req_doc;    LinearLayout getFile,red_img_bag_ll;    CenturyGothicTextView ctizen_txt;    String checked_string = "";    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_enrollment);        sessionHandler = new SessionHandler(EnrollmentActivity.this);        apiSessionHandler = new ApiSessionHandler(EnrollmentActivity.this);        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);        apiInterface = retrofit.create(ApiInterface.class);        profileText = (TextView) findViewById(R.id.profileText);        getFile = (LinearLayout) findViewById(R.id.getFile);        red_img_bag_ll = (LinearLayout) findViewById(R.id.red_img_bag_ll);        docText = (TextView) findViewById(R.id.docText);        getFile.setOnClickListener(this);        castSpinner = (Spinner) findViewById(R.id.castSpinner);        enrollment_name = (EditText) findViewById(R.id.enrollment_name);        enrollment_caste = (EditText) findViewById(R.id.enrollment_caste);        enrollment_age = (EditText) findViewById(R.id.enrollment_age);        enrollment_father_name = (EditText) findViewById(R.id.enrollment_father_name);        enrollment_spouse_name = (EditText) findViewById(R.id.enrollment_spouse_name);        enrollment_grandfather_name = (EditText) findViewById(R.id.enrollment_grandfather_name);        enrollment_address = (EditText) findViewById(R.id.enrollment_address);        enrollment_mobile_number = (EditText) findViewById(R.id.enrollment_mobile_number);        enrollment_czn = (EditText) findViewById(R.id.enrollment_czn);        enrollment_rg = (RadioGroup) findViewById(R.id.enrollment_rg);        enrollment_male_rb = (RadioButton) findViewById(R.id.enrollment_male_rb);        enrollment_female_rb = (RadioButton) findViewById(R.id.enrollment_female_rb);        ctizen_txt = (CenturyGothicTextView) findViewById(R.id.ctizen_txt);        enrollment_back = (ImageView) findViewById(R.id.enrollment_back);        enrollment_choose_img = (ImageView) findViewById(R.id.enrollment_choose_img);        enrollment_tick = (ImageView) findViewById(R.id.enrollment_tick);        enrollment_cross = (ImageView) findViewById(R.id.enrollment_cross);        req_doc = (ImageView) findViewById(R.id.req_doc);        getCastList();        req_doc.setOnClickListener(this);        enrollment_tick.setOnClickListener(this);        enrollment_choose_img.setOnClickListener(this);        enrollment_cross.setOnClickListener(this);        enrollment_back.setOnClickListener(view -> confirmBack());        enrollment_rg.setOnCheckedChangeListener((group, checkedId) -> {            switch (checkedId) {                case R.id.enrollment_female_rb:                    checked_string = enrollment_female_rb.getText().toString();                    break;                case R.id.enrollment_male_rb:                    checked_string = enrollment_male_rb.getText().toString();                    break;            }        });    }    @Override    public void onClick(View v) {        int getID = v.getId();        switch (getID) {            case R.id.enrollment_tick://                checkAllField();                final AlertDialog builder = new AlertDialog.Builder(this)                        .setPositiveButton("Yes", null)                        .setNegativeButton("CANCEL", null)                        .setTitle("Are you Sure you want to make Enrollment request?")                        .create();                builder.setOnShowListener(dialog -> {                    final Button btnAccept = builder.getButton(                            AlertDialog.BUTTON_POSITIVE);                    btnAccept.setOnClickListener(v1 -> {                        checkAllField();                        Log.e("backpressed","bp");                        builder.dismiss();                    });                    final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);                    btnDecline.setOnClickListener(v1-> builder.dismiss());                });                builder.show();                break;            case R.id.enrollment_choose_img:                enrollment_choose_img.setTag("profile");                String file = "";                TextView testView = new TextView(EnrollmentActivity.this);                testView.setVisibility(View.GONE);                profileText.setTag("profile");                Button button = new Button(EnrollmentActivity.this);                button.setTag("profile");                imageOptionDialog(button, "profile", "profile", profileText,enrollment_choose_img);//                imageOptionDialog();                break;            case R.id.req_doc:                req_doc.setTag("doc");                String file1 = "";                TextView testView1 = new TextView(EnrollmentActivity.this);                testView1.setVisibility(View.GONE);                docText.setTag("doc");                Button button1 = new Button(EnrollmentActivity.this);                button1.setTag("doc");                imageOptionDialog(button1, "doc", "doc", docText,req_doc);//                loadImageFromGallery(button1, "doc", "doc", docText);                break;            case R.id.enrollment_cross:                startActivity(new Intent(EnrollmentActivity.this, MainActivity.class));                break;        }    }    private void checkAllField() {        String enrollment_name_txt = "", enrollment_age_txt = "", enrollment_father_name_txt = "", enrollment_spouse_name_txt = "",                enrollment_grandfather_name_txt = "",                enrollment_address_txt = "", enrollment_mobile_number_txt = "", enrollment_male_rb_txt = "", enrollment_female_rb_txt = "";        enrollment_name_txt = enrollment_name.getText().toString();        enrollment_age_txt = enrollment_age.getText().toString();        enrollment_father_name_txt = enrollment_father_name.getText().toString();        enrollment_spouse_name_txt = enrollment_spouse_name.getText().toString();        enrollment_grandfather_name_txt = enrollment_grandfather_name.getText().toString();        enrollment_address_txt = enrollment_address.getText().toString();        enrollment_mobile_number_txt = enrollment_mobile_number.getText().toString();        if (enrollment_name_txt.equals("") | enrollment_age_txt.equals("") | enrollment_father_name_txt.equals("") |                enrollment_spouse_name_txt.equals("") | enrollment_grandfather_name_txt.equals("") | enrollment_address_txt.equals("")                | enrollment_mobile_number_txt.equals("") | checked_string.equals("")) {            Log.e("error","error");            if (enrollment_name.getText().toString().equals("")){                Log.e("error","name");                enrollment_name.setError("Field Required");            }            if (enrollment_age.getText().toString().equals("")){enrollment_age.setError("Field Required");}            if (enrollment_spouse_name.getText().toString().equals("")){enrollment_spouse_name.setError("Field Required");}            if (enrollment_grandfather_name.getText().toString().equals("")){enrollment_grandfather_name.setError("Field Required");}            if (enrollment_address.getText().toString().equals("")){enrollment_address.setError("Field Required");}            if (enrollment_mobile_number.getText().toString().equals("")){enrollment_mobile_number.setError("Field Required");}            if (enrollment_czn.getText().toString().equals("")){enrollment_czn.setError("Field Required");}            if (docText.getText().toString().equals("")){ctizen_txt.setTextColor(Color.RED);}            if (profileText.getText().toString().equals("")){red_img_bag_ll.setBackgroundColor(Color.RED);}            Intent intent = new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);            startActivity(intent);        } else {/*            Intent intent= new Intent(EnrollmentActivity.this, DialogActivity.class);            startActivity(intent);*/            sendEnrollmentPostRequest();        }    }    private void sendEnrollmentPostRequest() {        TextView profiletext = new TextView(EnrollmentActivity.this);        TextView imagetext = new TextView(EnrollmentActivity.this);        sessionHandler.showProgressDialog("sending Request ... ");        JSONObject jsonObject = new JSONObject();        try {            jsonObject.put("name", enrollment_name.getText().toString());            jsonObject.put("age", enrollment_age.getText().toString());            jsonObject.put("sex", checked_string);            jsonObject.put("caste", enrollment_caste.getText().toString());            jsonObject.put("father", enrollment_father_name.getText().toString());            jsonObject.put("spouse", enrollment_spouse_name.getText().toString());            jsonObject.put("grandfather", enrollment_grandfather_name.getText().toString());            jsonObject.put("address", enrollment_address.getText().toString());            jsonObject.put("mobile", enrollment_mobile_number.getText().toString());            jsonObject.put("citizenshipno", enrollment_czn.getText().toString());            jsonObject.put("finger1", "1234");            jsonObject.put("finger2", "1234");            jsonObject.put("finger3", "1234");/*            jsonObject.put("photo", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");            jsonObject.put("idProofDoc", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAgEBLAEsAAD/ 9k=…………");  */            jsonObject.put("photo", profileText.getText().toString());            jsonObject.put("idProofDoc", docText.getText().toString());            jsonObject.put("agentcode", sessionHandler.getAgentCode());        } catch (Exception e) {            e.printStackTrace();        }        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));        retrofit2.Call<SuccesResponseModel> call = apiInterface.sendEnrollmentRequest(apiSessionHandler.getMEMBER_ENROLL(), body,                sessionHandler.getAgentToken(), "Basic dXNlcjpqQiQjYUJAMjA1NA==",                "application/json", apiSessionHandler.getAgentCode());        call.enqueue(new Callback<SuccesResponseModel>() {            @Override            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {                sessionHandler.hideProgressDialog();                if (String.valueOf(response.code()).equals("200")) {                    if (response.body().getStatus().equals("Success")) {                        Intent intent = new Intent(EnrollmentActivity.this, DialogActivity.class);                        intent.putExtra("msg", response.body().getMessage());                        startActivity(intent);                        finish();                    } else {                        try {                            String jsonString = response.errorBody().string();                            Log.d("here ", "--=>" + jsonString);                            JSONObject jsonObject = new JSONObject(jsonString);                            Intent intent = new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);                            intent.putExtra("msg", jsonObject.getString("message"));                            startActivity(intent);                        } catch (Exception e) {                            e.printStackTrace();                        }                    }                } else {                    sessionHandler.hideProgressDialog();                    JSONObject jsonObject = null;                    String jsonString;                    try {                        jsonString = response.errorBody().string();                        Log.d("here ", "--=>" + jsonString);                        jsonObject = new JSONObject(jsonString);                        Intent intent = new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);                        if (jsonObject.getString("message").equals("1")) {                            intent.putExtra("msg", "Error in Fields");                        } else {                            intent.putExtra("msg", jsonObject.getString("message"));                        }                        startActivity(intent);                    } catch (Exception e) {                        e.printStackTrace();                    }                }            }            @Override            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {                sessionHandler.hideProgressDialog();                Intent intent = new Intent(EnrollmentActivity.this, DialogActivity.class);                intent.putExtra("msg", "Connection Error");                startActivity(intent);            }        });    }    private void startYourCameraIntent() {        Intent galleryIntent = new Intent(Intent.ACTION_PICK,                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);        EnrollmentActivity.this.startActivityForResult(galleryIntent, RESULT_LOAD_IMG);    }    @Override    public void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {            cameraBitmap = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this            Matrix matrix = new Matrix();            matrix.postRotate(90);            Bitmap rotatedBitmap = Bitmap.createBitmap(cameraBitmap , 0, 0, cameraBitmap .getWidth(), cameraBitmap .getHeight(), matrix, true);            setInImage.setImageBitmap(rotatedBitmap);            BitmapFactory.Options options = new BitmapFactory.Options();            options.inSampleSize = 4;            int height = rotatedBitmap.getHeight(), width = rotatedBitmap.getWidth();            if (height > 1280 && width > 960) {                Uri tempUri = getImageUri(this, rotatedBitmap);                imgFile = new File(getRealPathFromURI(tempUri));                imgPath = imgFile.getAbsolutePath();                Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);                updatedImageBitmap = imgbitmap;//                imgView.setImageBitmap(imgbitmap);                System.out.println("Need to resize");            } else {//                imgView.setImageBitmap(bitmap);                updatedImageBitmap = rotatedBitmap;                System.out.println("WORKS");            }            encodeImagetoString(updatedImageBitmap);        }        if (resultCode == Activity.RESULT_OK                && null != data) {            try {                // Get the Image from data                Uri selectedImage = data.getData();                String[] filePathColumn = {MediaStore.Images.Media.DATA};                // Get the cursor                Cursor cursor = getContentResolver().query(selectedImage,                        filePathColumn, null, null, null);                // Move to first row                assert cursor != null;                cursor.moveToFirst();                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);                imgPath = cursor.getString(columnIndex);                cursor.close();//            imgView.setVisibility(View.VISIBLE);                BitmapFactory.Options options = new BitmapFactory.Options();                options.inSampleSize = 4;                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);                Matrix matrix = new Matrix();                matrix.postRotate(90);                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);                setInImage.setImageBitmap(rotatedBitmap);                int height = rotatedBitmap.getHeight(), width = rotatedBitmap.getWidth();                if (height > 1280 && width > 960) {                    imgFile = new File(imgPath);                    Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);                    updatedImageBitmap = rotatedBitmap;//                imgView.setImageBitmap(imgbitmap);                    System.out.println("Need to resize");                } else {                    imgFile = new File(imgPath);//                imgView.setImageBitmap(bitmap);                    updatedImageBitmap = rotatedBitmap;                    System.out.println("WORKS");                }                String filefieldTagNameSegments[] = imgPath.split("/");                filefieldTagName = filefieldTagNameSegments[filefieldTagNameSegments.length - 1];                imgFilefieldTagName = filefieldTagName;                fileNameButton.setText(filefieldTagName);                Log.i("msg ", "filefieldTagName : " + filefieldTagName + " imgPath : " + imgPath);                options = null;                options = new BitmapFactory.Options();                options.inSampleSize = 3;                bitmap = BitmapFactory.decodeFile(imgPath,                        options);                encodeImagetoString(bitmap);            } catch (Exception e) {                e.printStackTrace();            }        } else {            Toast.makeText(EnrollmentActivity.this, "You haven't picked Image",                    Toast.LENGTH_LONG).show();        }    }    public void encodeImagetoString(Bitmap bitmap) {        new AsyncTask<Void, Void, String>() {            protected void onPreExecute() {                sessionHandler.showProgressDialog("Getting Image ..");            }            @Override            protected String doInBackground(Void... params) {                ByteArrayOutputStream stream = new ByteArrayOutputStream();                // Must compress the Image to reduce image size to make upload easy                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);//				bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);                byte[] byte_arr = stream.toByteArray();                // Encode Image to String                encodedString = Base64.encodeToString(byte_arr, 0);                compressedFilefieldTagName = encodedString;//                requestParams.put(fieldTagName,encodedString);                Log.d("adad", compressedFilefieldTagName);                return "";            }            @Override            protected void onPostExecute(String msg) {                sessionHandler.hideProgressDialog();                Log.d("fieldTagName", fieldTagName + "===== \n" + encodedString);                testTextview.setText("data:image/jpeg;base64," + encodedString);//                updateUserProfileImage();            }        }.execute(null, null, null);    }    @Override    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {        super.onRequestPermissionsResult(requestCode, permissions, grantResults);        switch (requestCode) {            case 1: {                if (grantResults.length > 0                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {                    startYourCameraIntent();                } else {                    Toast.makeText(EnrollmentActivity.this, "Please give your permission.", Toast.LENGTH_LONG).show();                }                break;            }        }    }    private boolean checkIfAlreadyhavePermission() {        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);//        return result == PackageManager.PERMISSION_GRANTED;        return result == PackageManager.PERMISSION_GRANTED;    }    public void loadImageFromGallery(Button imageView, String file, String imageFilefieldTagName, TextView testView,ImageView setImg) {        fileNameButton = imageView;        imgFilefieldTagName = imageFilefieldTagName;        testTextview = testView;        setInImage=setImg;//        compressedFilefieldTagName=file;        fieldTagName = file;        try {            final int MyVersion = Build.VERSION.SDK_INT;            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {                if (!checkIfAlreadyhavePermission()) {                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);                } else {                    startYourCameraIntent();                }            } else {                startYourCameraIntent();            }        } catch (Exception e) {            e.printStackTrace();        }    }    private void takePicture(Button imageView, String file, String imageFilefieldTagName, TextView testView,ImageView setImg) {        //you can call this every 5 seconds using a timer or whenever you want        fileNameButton = imageView;        imgFilefieldTagName = imageFilefieldTagName;        testTextview = testView;        setInImage = setImg;//        compressedFilefieldTagName=file;        fieldTagName = file;        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);        startActivityForResult(cameraIntent, CAMERA_REQUEST);    }    private void imageOptionDialog(Button imageView, String file, String imageFilefieldTagName, TextView testView,ImageView setImg) {        View view = getLayoutInflater().inflate(R.layout.dialog_get_image, null);        final AlertDialog builder = new AlertDialog.Builder(this)                .setView(view)                .setTitle("Select Photo")                .create();        LinearLayout galleryLL = (LinearLayout) view.findViewById(R.id.galleryLL);        LinearLayout cameraLL = (LinearLayout) view.findViewById(R.id.cameraLL);        cameraLL.setOnClickListener(v -> {            takePicture(imageView, file, imageFilefieldTagName, testView,setImg);            builder.dismiss();        });        galleryLL.setOnClickListener(v -> {            loadImageFromGallery(imageView, file, imageFilefieldTagName, testView,setImg);            builder.dismiss();        });        builder.show();    }    private void getCastList() {        sessionHandler.showProgressDialog("Sending Request ...");        retrofit2.Call<List<CastModel>> call =apiInterface.getCasteList(apiSessionHandler.getCASTE_LIST(),sessionHandler.getAgentToken(),                "Basic dXNlcjpqQiQjYUJAMjA1NA==",                "application/json",apiSessionHandler.getAgentCode());        call.enqueue(new Callback<List<CastModel>>() {            @Override            public void onResponse(Call<List<CastModel>> call, Response<List<CastModel>> response) {                sessionHandler.hideProgressDialog();                if (response.isSuccessful()){                    castModelList = response.body();                    for (int i =0;i<castModelList.size();i++){                        castStringList.add(castModelList.get(i).getMemberCast());                    }                    setupCaste();                }else {                    try {                        String jsonString = response.errorBody().string();                        Log.d("here ", "--=>" + jsonString);                        JSONObject jsonObject = new JSONObject(jsonString);                        Intent intent = new Intent(EnrollmentActivity.this, ErrorDialogActivity.class);                        intent.putExtra("msg", jsonObject.getString("message"));                        startActivity(intent);                    } catch (Exception e) {                        e.printStackTrace();                    }                }            }            @Override            public void onFailure(Call<List<CastModel>> call, Throwable t) {                sessionHandler.hideProgressDialog();            }        });    }    void setupCaste(){        ArrayAdapter<String> adapter = new ArrayAdapter(EnrollmentActivity.this, R.layout.text_layout, castStringList);        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);        castSpinner.setAdapter(adapter);    }    @Override    public void onBackPressed() {    }    void confirmBack(){        Log.e("backpressed","bp");        View view = getLayoutInflater().inflate(R.layout.dialog_ask_permission,null);        TextView askPermission = (TextView)view.findViewById(R.id.askPermission);        askPermission.setText("Are you Sure you want to go back??");        final AlertDialog builder = new AlertDialog.Builder(EnrollmentActivity.this)                .setPositiveButton("Yes", null)                .setNegativeButton("CANCEL", null)                .setTitle("Are you Sure you want to go back?")                .create();        builder.setOnShowListener(dialog -> {            final Button btnAccept = builder.getButton(                    AlertDialog.BUTTON_POSITIVE);            btnAccept.setOnClickListener(v -> {                super.onBackPressed();                Log.e("backpressed","bp");                builder.dismiss();            });            final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);            btnDecline.setOnClickListener(v -> builder.dismiss());        });        builder.show();    }    public String getRealPathFromURI(Uri uri) {        Cursor cursor = getContentResolver().query(uri, null, null, null, null);        cursor.moveToFirst();        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);        return cursor.getString(idx);    }    public Uri getImageUri(Context inContext, Bitmap inImage) {        ByteArrayOutputStream bytes = new ByteArrayOutputStream();        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);        return Uri.parse(path);    }    void showImage(int resource){        final Dialog nagDialog = new Dialog(getApplicationContext(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);        nagDialog.setCancelable(false);        nagDialog.setContentView(R.layout.preview_image);        Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);        ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);        ivPreview.setBackgroundResource(resource);        btnClose.setOnClickListener(arg0 -> nagDialog.dismiss());        nagDialog.show();    }}