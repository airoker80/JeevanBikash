package com.harati.jeevanbikas.fingerprint;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.R;

import static java.security.AccessController.getContext;

/**
 * Created by Sameer on 9/5/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;


    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Fingerprint Authentication error\n" + errString, false);
    }


    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Fingerprint Authentication help\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        this.update("Fingerprint Authentication failed.", false);
    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Fingerprint Authentication succeeded.", true);
    }


    public void update(String e, Boolean success) {
//        TextView textView = (TextView) ((Activity)context).findViewById(R.id.errorText);
//        textView.setText(e);
        if (success) {
//            textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
            Log.d("success", "--->" + e + "<---");
            showDialog();
        } else {
            Log.d("failed", "--->" + e + "<---");
            showErrorDialog();
        }
    }

    public void showDialog() {
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//                .create();
//
//        final LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
//        Button ok = (Button) dialogView.findViewById(R.id.ok);
//        alertDialog.setView(dialogView);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//
//        alertDialog.show();
        Intent intent = new Intent(context, DialogActivity.class);
        context.startActivity(intent);

    }

    public void showErrorDialog() {
//        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
//                .create();
//
//        final LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
//        Button ok = (Button) dialogView.findViewById(R.id.ok);
//        alertDialog.setView(dialogView);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//
//
//        alertDialog.show();
        Intent intent = new Intent(context, ErrorDialogActivity.class);
        intent.putExtra("msg", "Finger Authentication Failed");
        context.startActivity(intent);

    }
}