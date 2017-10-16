package com.harati.jeevanbikas.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.harati.jeevanbikas.Login.LoginActivity;

/**
 * Created by Sameer on 9/22/2017.
 */

public class SessionHandler {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFS_NAME = "LoginPrefs";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    private static final String AGENT_CODE ="agent_code";
    private static final String USERNAME ="user_name";
    private static final String BRANCH_OFFICE ="branch_office";
    private static final String PASSWORD_CHANGE_RQD ="password_change_rqd";
    private static final String PIN_CHANGE_RQD ="pin_change_rqd";
    private static final String AGENT_TOKEN ="agent_token";
    private static final String AGENT_PIN ="agent_pin";


    public SessionHandler(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveLoginInformation(String agentCode, String username,
                                     String branchoffice,boolean passwordChangeReqd,
                                     boolean pinChangeReqd,String token,String agentPin){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putBoolean(PASSWORD_CHANGE_RQD, passwordChangeReqd);
        editor.putBoolean(PIN_CHANGE_RQD, pinChangeReqd);
        editor.putString(AGENT_CODE, agentCode);
        editor.putString(USERNAME,username);
        editor.putString(BRANCH_OFFICE,branchoffice);
        editor.putString(AGENT_TOKEN,token);
        editor.putString(AGENT_PIN,agentPin);
        editor.commit();
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public String  getInfo(){
        Log.d("info","------>"+ pref.getString(USERNAME,"") +"<--------------");
        return  pref.getString(USERNAME,"");
    }

    public String getAgentCode(){
        return  pref.getString(AGENT_CODE,"");
    }
    public String getAgentToken(){
        return  pref.getString(AGENT_TOKEN,"");
    }
    public String getBranchOffice(){
        return  pref.getString(BRANCH_OFFICE,"");
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
}
