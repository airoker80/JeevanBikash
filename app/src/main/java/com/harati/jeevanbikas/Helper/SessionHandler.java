package com.harati.jeevanbikas.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.harati.jeevanbikas.Login.LoginActivity;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SystemApiResponseModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 9/22/2017.
 */

public class SessionHandler {
    ProgressDialog progress;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFS_NAME = "LoginPrefs";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    private static final String AGENT_CODE = "agent_code";
    private static final String USERNAME = "user_name";
    private static final String BRANCH_OFFICE = "branch_office";
    private static final String PASSWORD_CHANGE_RQD = "password_change_rqd";
    private static final String PIN_CHANGE_RQD = "pin_change_rqd";
    private static final String AGENT_TOKEN = "agent_token";
    private static final String AGENT_PIN = "agent_pin";
    private static final String AGENT_URL = "agent_url";
    private static final String AGENT_SERIAL_NO = "agent_serial_no";


    public static final String API_JSON = "api_json";

    public void saveApiJson(String apiJson) {
        editor.putString(API_JSON, apiJson);
        editor.commit();
    }

    public void saveUrl(String agentUrl) {
        editor.putString("agent_url", agentUrl);
        editor.commit();
    }

    public void saveAgentUrlInfo(String agentSerialNo) {
//        editor.putString(AGENT_URL,agentUrl);
        editor.putString(AGENT_SERIAL_NO, agentSerialNo);
        editor.commit();
        Log.d("save", "saved");
    }

    public SessionHandler(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean changePasswordReqd(){return pref.getBoolean(PASSWORD_CHANGE_RQD,false);}
    public boolean changePinReqd(){return pref.getBoolean(PIN_CHANGE_RQD,false);}

    public void saveLoginInformation(String agentCode, String username,
                                     String branchoffice, boolean passwordChangeReqd,
                                     boolean pinChangeReqd, String token, String agentPin) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putBoolean(PASSWORD_CHANGE_RQD, passwordChangeReqd);
        editor.putBoolean(PIN_CHANGE_RQD, pinChangeReqd);
        editor.putString(AGENT_CODE, agentCode);
        editor.putString(USERNAME, username);
        editor.putString(BRANCH_OFFICE, branchoffice);
        editor.putString(AGENT_TOKEN, token);
        editor.putString(AGENT_PIN, agentPin);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public String getInfo() {
        Log.d("info", "------>" + pref.getString(USERNAME, "") + "<--------------");
        return pref.getString(USERNAME, "");
    }

    public String getAgentCode() {
        return pref.getString(AGENT_CODE, "");
    }

    public String getAgentUrl() {
        return pref.getString(AGENT_URL, "") + "api/v1/";
    }

    public String getAgentSerialNo() {
        return pref.getString(AGENT_SERIAL_NO, "");
    }


    public String getAgentToken() {
        return pref.getString(AGENT_TOKEN, "");
    }

    public String getBranchOffice() {
        return pref.getString(BRANCH_OFFICE, "");
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        _context.sendBroadcast(broadcastIntent);
        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
    }

    public void showProgressDialog(String message) {

        Log.i("", "showProgressDialog");
        progress = new ProgressDialog(_context);
        if (!progress.isShowing()) {
            SpannableString titleMsg = new SpannableString("Processing");
            titleMsg.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleMsg.length(), 0);
            progress.setTitle(titleMsg);
            progress.setMessage(message);
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            progress.show();
        }
    }

    public void hideProgressDialog() {
        Log.i("", "hideProgressDialog");
        if (progress != null) {
            if (progress.isShowing()) {
                progress.dismiss();
            }
        }

    }


    public String returnCash(String cash) {
        String returningCash = "";
        StringBuilder stringBuilder = new StringBuilder(cash);
        switch (cash.length()) {
            case 1:
                returningCash = cash;
                break;
            case 2:
                returningCash = cash;
                break;
            case 3:
                stringBuilder.insert(1,",");
                returningCash = stringBuilder.toString();
                break;
            case 4:
                stringBuilder.insert(1,",");
                returningCash = stringBuilder.toString();
                break;
            case 5:
                stringBuilder.insert(2,",");
                returningCash = stringBuilder.toString();
                break;
            case 6:
                stringBuilder.insert(1,",");
                stringBuilder.insert(4,",");
                returningCash = stringBuilder.toString();
                break;

        }
        return returningCash;
    }
    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }
}
