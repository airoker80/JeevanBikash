package com.harati.jeevanbikas.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sameer on 11/10/2017.
 */

public class ApiSessionHandler {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFS_NAME = "ApiPrefs";
    public static final String AGENT_CODE = "agentCode";

    public static  final  String API_JSON ="api_json";
    public static  final  String FIRST_RUN ="firstRun";

    public static  final  String SYSTEM_API ="SYSTEM_API";
    public static  final  String AGENT_LOGIN ="AGENT_LOGIN";
    public static  final  String AGENT_DASHBOARD ="AGENT_DASHBOARD";
    public static  final  String AGENT_OTP ="AGENT_OTP";
    public static  final  String AGENT_PIN_RESET ="AGENT_PIN_RESET";
    public static  final  String AGENT_PASSWORD_RESET ="AGENT_PASSWORD_RESET";
    public static  final  String MEMBER_SEARCH ="MEMBER_SEARCH";
    public static  final  String BALANCE_ENQUIRY ="BALANCE_ENQUIRY";
    public static  final  String CASH_DEPOSIT ="CASH_DEPOSIT";
    public static  final  String CASH_WITHDRAW ="CASH_WITHDRAW";
    public static  final  String FUND_TRANSFER ="FUND_TRANSFER";
    public static  final  String MEMBER_ENROLL ="MEMBER_ENROLL";
    public static  final  String LOAN_DEMAND ="LOAN_DEMAND";
    public static  final  String CASTE_LIST ="CASTE_LIST";
    public static  final  String LOAN_TYPE_LIST ="LOAN_TYPE_LIST";

    public static  final  String DEPOSIT_OTP ="DEPOSIT_OTP";
    public static  final  String WITHDRAW_OTP ="WITHDRAW_OTP";
    public static  final  String FUND_TRANSFER_OTP ="FUND_TRANSFER_OTP";

    public ApiSessionHandler(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void  saveApiJson(String apiJson){
        editor.putString(API_JSON,apiJson);
        editor.commit();
    }

    public void  saveAppFirstState(Boolean firstRun){editor.putBoolean(FIRST_RUN,firstRun);editor.commit();}


    public void  saveAgentCode(String firstRun){editor.putString(AGENT_CODE,firstRun);editor.commit();}


    public void  saveSYSTEM_API(String firstRun){editor.putString(SYSTEM_API,firstRun);editor.commit();}
    public void  saveAGENT_LOGIN(String firstRun){editor.putString(AGENT_LOGIN,firstRun);editor.commit();}
    public void  saveAGENT_DASHBOARD(String firstRun){editor.putString(AGENT_DASHBOARD,firstRun);editor.commit();}
    public void  saveAGENT_OTP(String firstRun){editor.putString(AGENT_OTP,firstRun);editor.commit();}
    public void  saveAGENT_PIN_RESET(String firstRun){editor.putString(AGENT_PIN_RESET,firstRun);editor.commit();}
    public void  saveAGENT_PASSWORD_RESET(String firstRun){editor.putString(AGENT_PASSWORD_RESET,firstRun);editor.commit();}
    public void  saveMEMBER_SEARCH(String firstRun){editor.putString(MEMBER_SEARCH,firstRun);editor.commit();}
    public void  saveBALANCE_ENQUIRY(String firstRun){editor.putString(BALANCE_ENQUIRY,firstRun);editor.commit();}
    public void  saveCASH_DEPOSIT(String firstRun){editor.putString(CASH_DEPOSIT,firstRun);editor.commit();}
    public void  saveCASH_WITHDRAW(String firstRun){editor.putString(CASH_WITHDRAW,firstRun);editor.commit();}
    public void  saveFUND_TRANSFER(String firstRun){editor.putString(FUND_TRANSFER,firstRun);editor.commit();}
    public void  saveMEMBER_ENROLL(String firstRun){editor.putString(MEMBER_ENROLL,firstRun);editor.commit();}
    public void  saveLOAN_DEMAND(String firstRun){editor.putString(LOAN_DEMAND,firstRun);editor.commit();}
    public void  saveCASTE_LIST(String firstRun){editor.putString(CASTE_LIST,firstRun);editor.commit();}
    public void  saveLOAN_TYPE_LIST(String firstRun){editor.putString(LOAN_TYPE_LIST,firstRun);editor.commit();}

    public void  saveDEPOSIT_OTP(String firstRun){editor.putString(DEPOSIT_OTP,firstRun);editor.commit();}
    public void  saveWITHDRAW_OTP(String firstRun){editor.putString(WITHDRAW_OTP,firstRun);editor.commit();}
    public void  saveFUND_TRANSFER_OTP(String firstRun){editor.putString(FUND_TRANSFER_OTP,firstRun);editor.commit();}


    public Boolean getFirstRunStatus(){return  pref.getBoolean(FIRST_RUN,true);}
    public String getAgentCode(){return  pref.getString(AGENT_CODE,"");}

    public String getSYSTEM_API(){return  pref.getString(SYSTEM_API,"");}
    public String getAGENT_LOGIN(){return  pref.getString(AGENT_LOGIN,"");}
    public String getAGENT_DASHBOARD(){return  pref.getString(AGENT_DASHBOARD,"");}
    public String getAGENT_OTP(){return  pref.getString(AGENT_OTP,"");}
    public String getAGENT_PIN_RESET(){return  pref.getString(AGENT_PIN_RESET,"");}
    public String getAGENT_PASSWORD_RESET(){return  pref.getString(AGENT_PASSWORD_RESET,"");}
    public String getMEMBER_SEARCH(){return  pref.getString(MEMBER_SEARCH,"");}
    public String getBALANCE_ENQUIRY(){return  pref.getString(BALANCE_ENQUIRY,"");}
    public String getCASH_DEPOSIT(){return  pref.getString(CASH_DEPOSIT,"");}
    public String getCASH_WITHDRAW(){return  pref.getString(CASH_WITHDRAW,"");}
    public String getFUND_TRANSFER(){return  pref.getString(FUND_TRANSFER,"");}
    public String getMEMBER_ENROLL(){return  pref.getString(MEMBER_ENROLL,"");}
    public String getLOAN_DEMAND(){return  pref.getString(LOAN_DEMAND,"");}
    public String getCASTE_LIST(){return  pref.getString(CASTE_LIST,"");}
    public String getLOAN_TYPE_LIST(){return  pref.getString(LOAN_TYPE_LIST,"");}

    public String getDEPOSIT_OTP(){return  pref.getString(DEPOSIT_OTP,"");}
    public String getWITHDRAW_OTP(){return  pref.getString(WITHDRAW_OTP,"");}
    public String getFUND_TRANSFER_OTP(){return  pref.getString(FUND_TRANSFER_OTP,"");}
    }

