package com.harati.jeevanbikas.BalanceEnquiry;

import android.Manifest;
import android.app.Fragment;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.harati.jeevanbikas.fingerprint.FingerprintHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.KeyStore;

import javax.crypto.Cipher;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by Sameer on 11/2/2017.
 */

public class NewFingerPrintFragment extends android.support.v4.app.Fragment {
    ApiSessionHandler apiSessionHandler ;
    ImageView fingerPrint;
    SessionHandler sessionHandler;
    private KeyStore keyStore;
    Retrofit retrofit;
    ApiInterface apiInterface;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "Bikash";
    private Cipher cipher;
    private TextView textView,text;
    Bundle bundle;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        apiSessionHandler = new ApiSessionHandler(getContext());
        bundle = getArguments();
        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
        text = (TextView) view.findViewById(R.id.text);
        text.setTypeface(MainActivity.centuryGothic);


        fingerPrint.setOnClickListener(view1 -> {
//                showMessage();

//            sendBalanceEnquiryRequest();

/*                Snackbar snackbar = Snackbar
                    .make(view, "Please Place your right finger in the sensor", Snackbar.LENGTH_LONG);
            snackbar.show();
            View snackbarview = snackbar.getView();
            TextView textView = (TextView) snackbarview.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);*/
        });
        return view;
    }



}
