package com.harati.jeevanbikas.LoanDemand;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Enrollment.EnrollmentActivity;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.CenturyGothicTextView;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.LoanDemand.Adapter.SpinnerAdapter;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoanDetailsModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by User on 8/28/2017.
 */

public class LoanDetailFragment extends Fragment {
    Bitmap cameraBitmap;
    Button fileNameButton;
    String filefieldTagName, imgFilefieldTagName, encodedString, compressedFilefieldTagName, fieldTagName;
    TextView testTextview, profileText, docText;
    String imgPath;
    Spinner castSpinner;
    private static int RESULT_LOAD_IMG = 12;
    File imgFile;
    ImageView profileImage;
    Bitmap updatedImageBitmap;
    Bitmap bitmap;
    private static final int CAMERA_REQUEST = 1888;
    ApiSessionHandler apiSessionHandler;
    ImageView setInImage;
    
    Bundle bundle;
    List<LoanDetailsModel> loanDetailsModels = new ArrayList<>();
    ImageView loanPhoto2,loanPhoto1;
    SessionHandler sessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    List<String> stringList = new ArrayList<>();
    ImageView submit;
    Spinner spinner;
    EditText loanAmt,ld_clients_pin;
    CenturyGothicTextView loanNameDetails ,loanDtCode ,ldOffice;
    public LoanDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loan_detail, container, false);
        bundle = getArguments();
        sessionHandler = new SessionHandler(getContext());
        apiSessionHandler = new ApiSessionHandler(getContext());
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        Log.d("loantype", "=-");
        getLoanTypeList();
        Log.d("loantype", "=-" + loanDetailsModels.size());
        submit = (ImageView) view.findViewById(R.id.submit);
        loanAmt = (EditText) view.findViewById(R.id.loanAmt);
        ld_clients_pin = (EditText) view.findViewById(R.id.ld_clients_pin);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        docText = new TextView(getContext());
        profileText = new TextView(getContext());
        loanPhoto2=(ImageView) view.findViewById(R.id.loanPhoto2);
        loanPhoto1=(ImageView) view.findViewById(R.id.loanPhoto1);

        loanNameDetails=(CenturyGothicTextView)view.findViewById(R.id.loanNameDetails);
        loanDtCode=(CenturyGothicTextView)view.findViewById(R.id.loanDtCode);
        ldOffice=(CenturyGothicTextView)view.findViewById(R.id.ldOffice);

        loanNameDetails.setText(bundle.getString("name"));
                loanDtCode.setText(bundle.getString("code"));
        ldOffice.setText(bundle.getString("office"));

        loanPhoto2.setOnClickListener(v -> {
            loanPhoto2.setTag("profile");
            String file = "";
            TextView testView = new TextView(getContext());
            testView.setVisibility(View.GONE);
            profileText.setTag("profile");
            Button button = new Button(getContext());
            button.setTag("profile");
            imageOptionDialog(button, "profile", "profile", profileText,loanPhoto2);
        });
                loanPhoto1.setOnClickListener(v -> {
                    loanPhoto1.setTag("doc");
                    String file1 = "";
                    TextView testView1 = new TextView(getContext());
                    testView1.setVisibility(View.GONE);
                    docText.setTag("doc");
                    Button button1 = new Button(getContext());
                    button1.setTag("doc");
                    imageOptionDialog(button1, "doc", "doc", docText,loanPhoto1);
                });

        submit.setOnClickListener(view1 -> {
            if (loanAmt.getText().toString().equals("") | spinner.getSelectedItem().toString().equals("")| ld_clients_pin.getText().toString().equals("")) {
                    Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                    getActivity().startActivity(intent);
            }else if (docText.getText().toString().equals("")|profileText.getText().toString().equals("")){
                Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                intent.putExtra("msg","Please fill all images too");
                getActivity().startActivity(intent);
            } else {
//                sendLoanDemandRequest();
                final AlertDialog builder = new AlertDialog.Builder(getContext())
                        .setPositiveButton("Yes", null)
                        .setNegativeButton("CANCEL", null)
                        .setTitle("Are you Sure you want to make loan deand request?")
                        .create();

                builder.setOnShowListener(dialog -> {

                    final Button btnAccept = builder.getButton(
                            AlertDialog.BUTTON_POSITIVE);

                    btnAccept.setOnClickListener(v -> {
                        sendLoanDemandRequest();
                        Log.e("backpressed","bp");
                        builder.dismiss();

                    });

                    final Button btnDecline = builder.getButton(DialogInterface.BUTTON_NEGATIVE);

                    btnDecline.setOnClickListener(v -> builder.dismiss());
                });

                builder.show();
            }

        });
        return view;
    }

    private void getLoanTypeList() {
        sessionHandler.showProgressDialog("Sending Request ...");
        retrofit2.Call<List<LoanDetailsModel>> call = apiInterface.getLoanTypeList(apiSessionHandler.getLOAN_TYPE_LIST(),sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<List<LoanDetailsModel>>() {
            @Override
            public void onResponse(Call<List<LoanDetailsModel>> call, Response<List<LoanDetailsModel>> response) {
                sessionHandler.hideProgressDialog();
                loanDetailsModels = response.body();
                try {
                    for (int i = 0; i < loanDetailsModels.size(); i++) {
                        Log.d("ok lets test", "===" + loanDetailsModels.get(i).getLoanType());
                        stringList.add(loanDetailsModels.get(i).getLoanType());
                    }
                    setupSpinner();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<LoanDetailsModel>> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });

    }

    private void sendLoanDemandRequest() {

        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("membercode", bundle.getString("code"));
            jsonObject.put("finger", ld_clients_pin.getText().toString());
            jsonObject.put("amount", loanAmt.getText().toString());
            jsonObject.put("loantype", spinner.getSelectedItem().toString());
            jsonObject.put("aform", docText.getText().toString());
            jsonObject.put("bform", profileText.getText().toString());
            jsonObject.put("remark", "testing");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("body jsonBody", "=()-" + jsonObject.toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (jsonObject.toString()));
        retrofit2.Call<SuccesResponseModel> call = apiInterface.sendPostLoanDemand(apiSessionHandler.getLOAN_DEMAND(),body,
                sessionHandler.getAgentToken(), "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());

        call.enqueue(new Callback<SuccesResponseModel>() {
            @Override
            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {
                sessionHandler.hideProgressDialog();
                if (String.valueOf(response.code()).equals("200")) {
                    if (response.body().getStatus().equals("Success")) {
                        Intent intent = new Intent(getContext(), DialogActivity.class);
                        intent.putExtra("msg", response.body().getMessage());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg", response.body().getMessage());
                        startActivity(intent);
                    }


                } else {
                    sessionHandler.hideProgressDialog();
                    try {

                        String jsonString = response.errorBody().string();

                        Log.d("here ", "--=>" + jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg", jsonObject.getString("message"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(getContext(), DialogActivity.class);
                intent.putExtra("msg", "Connection Error");
                startActivity(intent);
            }
        });
    }

    void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.text_layout, stringList);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private void startYourCameraIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            cameraBitmap = (Bitmap) data.getExtras().get("data");//this is your bitmap image and now you can do whatever you want with this
            setInImage.setImageBitmap(cameraBitmap);
            encodeImagetoString(cameraBitmap);
        }
        if (resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();

//            imgView.setVisibility(View.VISIBLE);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                setInImage.setImageBitmap(bitmap);
                int height = bitmap.getHeight(), width = bitmap.getWidth();

                if (height > 1280 && width > 960) {
                    imgFile = new File(imgPath);
                    Bitmap imgbitmap = BitmapFactory.decodeFile(imgPath, options);
                    updatedImageBitmap = bitmap;
//                imgView.setImageBitmap(imgbitmap);
                    System.out.println("Need to resize");
                } else {
                    imgFile = new File(imgPath);
//                imgView.setImageBitmap(bitmap);
                    updatedImageBitmap = bitmap;
                    System.out.println("WORKS");
                }

                String filefieldTagNameSegments[] = imgPath.split("/");
                filefieldTagName = filefieldTagNameSegments[filefieldTagNameSegments.length - 1];
                imgFilefieldTagName = filefieldTagName;
                fileNameButton.setText(filefieldTagName);
                Log.i("msg ", "filefieldTagName : " + filefieldTagName + " imgPath : " + imgPath);
                options = null;
                options = new BitmapFactory.Options();
                options.inSampleSize = 3;
                bitmap = BitmapFactory.decodeFile(imgPath,
                        options);
                encodeImagetoString(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }


    }

    public void encodeImagetoString(Bitmap bitmap) {
        new AsyncTask<Void, Void, String>() {

            protected void onPreExecute() {
                sessionHandler.showProgressDialog("Getting Image ..");
            }

            @Override
            protected String doInBackground(Void... params) {

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
                testTextview.setText("data:image/jpeg;base64," + encodedString);

//                updateUserProfileImage();
            }
        }.execute(null, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startYourCameraIntent();

                } else {
                    Toast.makeText(getContext(), "Please give your permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public void loadImageFromGallery(Button imageView, String file, String imageFilefieldTagName, TextView testView, ImageView setImg) {
        fileNameButton = imageView;
        imgFilefieldTagName = imageFilefieldTagName;
        testTextview = testView;
        setInImage=setImg;
//        compressedFilefieldTagName=file;
        fieldTagName = file;

        try {
            final int MyVersion = Build.VERSION.SDK_INT;
            if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!checkIfAlreadyhavePermission()) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    startYourCameraIntent();
                }
            } else {
                startYourCameraIntent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void takePicture(Button imageView, String file, String imageFilefieldTagName, TextView testView,ImageView setImg) {
        //you can call this every 5 seconds using a timer or whenever you want
        fileNameButton = imageView;
        imgFilefieldTagName = imageFilefieldTagName;
        testTextview = testView;
        setInImage = setImg;
//        compressedFilefieldTagName=file;
        fieldTagName = file;
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    private void imageOptionDialog(Button imageView, String file, String imageFilefieldTagName, TextView testView,ImageView setImg) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_get_image, null);
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Select Photo")
                .create();

        LinearLayout galleryLL = (LinearLayout) view.findViewById(R.id.galleryLL);
        LinearLayout cameraLL = (LinearLayout) view.findViewById(R.id.cameraLL);
        cameraLL.setOnClickListener(v -> {
            takePicture(imageView, file, imageFilefieldTagName, testView,setImg);
            builder.dismiss();
        });
        galleryLL.setOnClickListener(v -> {
            loadImageFromGallery(imageView, file, imageFilefieldTagName, testView,setImg);
            builder.dismiss();
        });
        builder.show();
    }
}
