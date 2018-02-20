package com.harati.jeevanbikas.PabyByOnlineService.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.PabyByOnlineService.Helper.ShowMyAlertProgressDialog;
import com.harati.jeevanbikas.PabyByOnlineService.config.PayByOnlineConfig;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Sameer on 1/15/2018.
 */

public class BuyPageConfirmFragment extends Fragment implements View.OnClickListener, DialogInterface.OnClickListener {

    Retrofit retrofit;
    ApiInterface apiInterface;

    LinearLayout dynamicLayout;
    // LinkedHashMap<String,String> labelVal = new LinkedHashMap<>();
    //LinkedHashMap<String,String> labelType = new LinkedHashMap<>();
    Button payNow;
    ImageView imgView;
    CoordinatorLayout coordinatorLayout;
    JSONObject strVal = null;
    JSONObject strType = null;
    //    RequestParams params = new RequestParams();
//    RequestParams sendParams = new RequestParams();
    Map<String, String> params = new HashMap<>();
    Map<String, String> sendParams = new HashMap<>();
    Intent intent;
    String merchantName;
    String confirmationPin;
    EditText confirmationPinEditTxt;
    ShowMyAlertProgressDialog showMyAlertProgressDialog;
    SessionHandler sessionHandler;
    TextView pinRequest;
    TextView pinCodeHeading;
    String pinCodePresent;
    AlertDialog dialog;
    EditText pinReenterEditText;
    EditText enterPasswordEditText;
    EditText pinEnterEditText;

    public BuyPageConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_page_confirm, container, false);

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);

        showMyAlertProgressDialog = new ShowMyAlertProgressDialog(getActivity());
        dynamicLayout = (LinearLayout) view.findViewById(R.id.dynamicLayout);
        imgView = (ImageView) view.findViewById(R.id.imgView);
        payNow = (Button) view.findViewById(R.id.payNow);
        payNow.setVisibility(View.GONE);
        payNow.setOnClickListener(this);
        String scstId = "";
        String formCountryId = "";

        retrofit = MyApplication.getRetrofitInstance(PayByOnlineConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        Bundle bundle = getArguments();
        sessionHandler = new SessionHandler(getActivity());
        formCountryId = bundle.getString("formCountryId");
        merchantName = bundle.getString("merchantName");
        scstId = bundle.getString("scstId");
        // params.put("confirmationPin",intent.getStringExtra("confirmationPin"));
        params.put("merchantName", merchantName);
        params.put("scstId", scstId);
        Log.d("sc", scstId);
        params.put("formCountryId", formCountryId);


        try {

            strVal = new JSONObject(bundle.getString("valList"));
            Log.d("valAmount", strVal.toString());
            strType = new JSONObject(bundle.getString("typeList"));
            Log.i("keys: ", strVal.toString());
            Log.i("keysVal: ", "" + strType.toString());

            Iterator itt = strVal.keys();
            // Iterator ittt = strType.keys();
            while (itt.hasNext()) {

                String me = (String) itt.next();
                Log.i("msgsss", "key : " + me);
                params.put(me, strVal.get(me).toString());
                //  TextView textView = new TextView(this);
                //  textView.setText(me + ":" + strVal.get(me));
                //  dynamicLayout.addView(textView);
                // dynamicLayout.setPadding(5, 5, 5, 10);

            }

            Iterator ittt = strType.keys();
            while (ittt.hasNext()) {

                String you = (String) ittt.next();
                Log.i("msgsss", "key : " + you);
                //strVal.get(you);
                // TextView textView3 = new TextView(this);
                //  textView3.setText(you +":"+strType.get(you));
                params.put(you, strType.get(you).toString());
                //  dynamicLayout.addView(textView3);
                //  dynamicLayout.setPadding(5, 5, 5, 10);
            }

            params.put("isTemplate", "yes");

           /* TextView scstIdView = new TextView(this);
            TextView formCountryIdView= new TextView(this);

            scstIdView.setText("ScstId :" + serviceCategory);
            formCountryIdView.setText("FormCountryId :" + formCountryId);*/

            Log.i("serviceCategory", scstId + "");
            Log.i("formCountryId", formCountryId + "");

            // dynamicLayout.addView(scstIdView);
            //  dynamicLayout.addView(formCountryIdView);
            //  dynamicLayout.setPadding(5, 5, 5, 10);

            getConfirmFieldContent();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("exp", "" + e);
        }


        return view;
    }

    public void getConfirmFieldContent() {

        // Make Http call
/*        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
               getActivity());*/
        params.put("parentTask", "rechargeApp");
        params.put("childTask", "showDynamicData");
        params.put("userCode", sessionHandler.getUserCode());
        params.put("authenticationCode", sessionHandler.getAuthCode());
/*        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Processing...", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                          Log.d("asdasdasda", response.toString());
                        try {

                            Log.i("ListresponseT", "response");
                            Log.i("params", ""+params.toString());

                            handleConfirmFieldContentResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exp",e+"");

                        }

                    }
                });*/



//        retrofitHelper.setOnResponseListener(new RetrofitHelper.OnRetrofitResponse() {
//            @Override
//            public void onRetrofitSuccess(JSONObject jsonObject, int apiCode) {
//                Log.d("asdasdasda", jsonObject.toString());
//                try {
//                    Log.i("ListresponseT", "response");
//                    handleConfirmFieldContentResponse(jsonObject);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("exp", e + "");
//                }
//            }
//
//            @Override
//            public void onRetrofitFailure(String errorMessage, int apiCode) {
//                Log.e("BuyPageConfirmFragment", "error");
//            }
//        });
//        retrofitHelper.sendRequest(ApiIndex.POST_ON_AUTH_APP_USER_ENDPOINT, null, null, params);

        Call<ResponseBody> call = apiInterface.getRechargeServiceDetails(PayByOnlineConfig.SERVER_URL+"authenticateAppUser",params);
        sessionHandler.showProgressDialog("please wait ...");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                sessionHandler.hideProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.i("ListresponseT", "response");
                    handleConfirmFieldContentResponse(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exp", e + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                t.printStackTrace();
            }
        });
    }

    public void handleConfirmFieldContentResponse(JSONObject response) throws JSONException {

        Log.i("Listresponse2", "" + response);
        if (response.getString("msgTitle").equals("Success")) {

            payNow.setVisibility(View.VISIBLE);
            sendParams.put("platformName", "Embedded by PBO");
            sendParams.put("purchasedValue", response.getString("purchasedValue"));
            sendParams.put("paidAmount", response.getString("netAmount"));
            pinCodePresent = response.getString("pinCodePresent");
            sendParams.put("dynamicMerchantSave", "true");
            JSONObject sCatSType = response.getJSONObject("sCatSType");
            JSONObject sCategory = sCatSType.getJSONObject("serviceCategory");
            JSONObject serviceType = sCatSType.getJSONObject("serviceType");

            sendParams.put("sCategory", sCategory.getString("id"));
            sendParams.put("serviceType", serviceType.getString("id"));
            String logoName = PayByOnlineConfig.BASE_URL + "serviceCategoryServiceTypeLogo/" + sCatSType.getString("logoName");

            Picasso.with(getContext())
                    .load(logoName)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgView);
            JSONObject webFrontFormName = response.getJSONObject("webFrontFormName");
            sendParams.put("webFrontFormNameId", webFrontFormName.getString("id"));
            sendParams.put("dealName", "");

            JSONObject showData = new JSONObject(response.getString("showData"));
            JSONObject showDiscount = new JSONObject(response.getString("showDiscount"));
            Log.i("showdata", showData + "");
            Log.i("showDiscount", showDiscount + "");
            try {

                Iterator iterator = showData.keys();
                JSONObject obj = new JSONObject();
                String key = "";
                //  dynamicFormFieldList = new ArrayList<DynamicFormField>();
                while (iterator.hasNext()) {

                    key = (String) iterator.next();
                    obj = showData.getJSONObject(key);
                    Log.i("object", key + " : " + obj);

                    TextView textView = new TextView(getActivity());

                    textView.setText(obj.getString("fieldName") + ":" + obj.get("fieldValue").toString());
                    Log.i("msg", obj.getString("fieldName") + ":" + obj.get("fieldValue").toString());
//                params.add(object.get("fieldName").toString(),object.get("fieldValue").toString());
                    String fieldValues = obj.get("fieldName") + "," + obj.get("fieldValue");
                    sendParams.put(key, obj.get("fieldValue").toString());

                    dynamicLayout.addView(textView);
                    dynamicLayout.setPadding(5, 5, 5, 10);


                }
            } catch (JSONException e) {

                e.printStackTrace();
                Log.i("msgsss", e + "");

            }
            try {
                TextView isDiscountTxt = new TextView(getActivity());

//            showDiscount.getString("isPercentage").equals("true")||showDiscount.getString("isPercentage").equals("Y")
                Log.i("disPer", "" + showDiscount.getString("disPer"));
                Log.i("isDiscount", "" + showDiscount.getString("isDiscount"));
                Log.i("isPercent", "" + showDiscount.getString("isPercent"));

                if (showDiscount.getString("isDiscount").equals("true") || showDiscount.getString("isDiscount").equals("Y")) {

                    if (showDiscount.getString("isPercent").equals("true") || showDiscount.getString("isPercent").equals("Y")) {

                        isDiscountTxt.setText("Merchant Service (MS) Discount :" + showDiscount.getString("disPer") + " ( Percent ) ");

                    } else {
                        isDiscountTxt.setText("Merchant Service (MS) Discount :" + showDiscount.getString("disPer") + " ( Amount ) ");
                    }

                } else {

                    isDiscountTxt.setText("Merchant Service (MS) Addition :" + showDiscount.getString("disPer") + " (Amount) ");

                }
                dynamicLayout.addView(isDiscountTxt);
                dynamicLayout.setPadding(5, 5, 5, 10);

                TextView amounttTxt = new TextView(getActivity());
                amounttTxt.setText("Amount : " + response.getString("amount"));

                amounttTxt.setText("Net Cost Amount : " + response.getString("netAmount"));
                dynamicLayout.addView(amounttTxt);
                dynamicLayout.setPadding(5, 5, 5, 10);

//           sendParams.put("Amount", response.getString("amount"));
//           params.put("Net Cost Amount", response.getString("netAmount"));
                sendParams.put("responseType", "JSON");

                Log.i("sendParams", "" + sendParams);

            } catch (JSONException e) {

                e.printStackTrace();
                Log.i("msgsss", e + "");

            }

        } else {

        }


    }

    public void sendFieldContent() {

        // Make Http call
//        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
        sendParams.put("parentTask", "rechargeApp");
//         sendParams.put("childTask", "saveRecharge");
        sendParams.put("childTask", "performRecharge");
//        sendParams.put("childTask", "saveRecharge");
        sendParams.put("userCode", sessionHandler.getUserCode());
        sendParams.put("authenticationCode", sessionHandler.getAuthCode());
        sendParams.put("confirmationPin", confirmationPinEditTxt.getText().toString());
        // String url="http://192.168.1.52:8080/MerchantPlugin/serviceRecharge/saveRecharge";

/*        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Processing...", sendParams,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {

                            Log.i("ListresponseT", "response");
                            Log.i("params", "" + sendParams.toString());

                            handleSendFieldContentResponse(response);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exp", e + "");

                        }

                    }
                });*/


//        retrofitHelper.setOnResponseListener(new RetrofitHelper.OnRetrofitResponse() {
//            @Override
//            public void onRetrofitSuccess(JSONObject jsonObject, int apiCode) {
//                try {
//                    Log.i("ListresponseT", "response");
//                    handleSendFieldContentResponse(jsonObject);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("exp", e + "");
//                }
//            }
//
//            @Override
//            public void onRetrofitFailure(String errorMessage, int apiCode) {
//                Log.e("BuyPageConfirmFrag", "error");
//                sendParams.clear();
//            }
//        });
//        retrofitHelper.sendRequest(ApiIndex.POST_ON_AUTH_APP_USER_ENDPOINT, null, null, sendParams);
        Call<ResponseBody> call = apiInterface.getRechargeServiceDetails(PayByOnlineConfig.SERVER_URL+"authenticateAppUser"
                ,params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.i("ListresponseT", "response");
                    handleSendFieldContentResponse(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exp", e + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void handleSendFieldContentResponse(JSONObject response) throws JSONException {


        try {


            JSONObject json = response;
            Log.i("jsonttttttttttttt", json + "");

            if (json.getString("msgTitle").equals("Failed")) {


                if (json.has("reason")) {
                    if (json.getString("reason").equals("No Code")) {
                        showUserAlertDialogWithView(json.getString("msg"),
                                "msgTitle");
                    } else {

                        showMyAlertProgressDialog.showUserAlertDialog(
                                json.getString("msg"), json.getString("msgTitle"));
                    }
                } else {

                    showMyAlertProgressDialog.showUserAlertDialog(
                            json.getString("msg"), json.getString("msgTitle"));
                }


            } else {
                showMyAlertProgressDialog.showUserAlertDialog(
                        json.getString("msg"), json.getString("msgTitle"));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(this);
                fragmentTransaction.commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
            Toast.makeText(getActivity(),"Success", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.payNow:

                transactionConfirmModel();

                break;

            case R.id.pinCodeRequest:

                pinRequest();

                break;

            default:
                break;

        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public void transactionConfirmModel() {
        // rechargeTargetNumberErrorMsg="";

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogview = inflater.inflate(R.layout.confirmation_pin_code_form, null);
        confirmationPinEditTxt = (EditText) dialogview
                .findViewById(R.id.confirmationPin);
        Log.i("msgss", "transactionConfirm");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialog = dialogBuilder
                // .setMessage(ss)
                // .setIcon(R.drawable.ic_launcher)
                .setView(dialogview).setTitle("Transaction Confirmation")
                .setPositiveButton("Yes", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

        pinRequest = (TextView) dialogview
                .findViewById(R.id.pinCodeRequest);
        pinCodeHeading = (TextView) dialogview
                .findViewById(R.id.pinCodeHeading);

        if (pinCodePresent.equals("YES")) {

            Log.i("msgss", "transactionConfirm");
            pinRequest.setVisibility(View.GONE);
            pinCodeHeading.setVisibility(View.GONE);

        } else {

            pinRequest.setVisibility(View.VISIBLE);
            pinCodeHeading.setVisibility(View.VISIBLE);
            pinRequest.setText("Create Pin Code ");
            pinRequest.setTextColor(Color.parseColor("#5F86C4"));
            pinRequest.setOnClickListener(this);

        }


        Button yesBtn = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        yesBtn.setOnClickListener(new CustomListener(dialog));
    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;

        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {

            // Do whatever you want here
            // If you want to close the dialog, uncomment the line below
            String minLength = "4";
            if (minLength.equals(confirmationPinEditTxt.getText().toString().length()
                    + "")) {
                dialog.dismiss();
                // for hiding keyboard on button click
                // imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                sendFieldContent();
            } else {
                confirmationPinEditTxt.setError("4 Digit Transaction Pin Code Required");
            }

        }
    }

    public void showUserAlertDialogWithView(String message, String title) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogview = inflater.inflate(R.layout.redirect_website_message,
                null);
        TextView redirectMsg = (TextView) dialogview
                .findViewById(R.id.redirectMsg);

        redirectMsg.setText(message);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                getActivity());
        final AlertDialog pindialog = dialogBuilder.setTitle(title)
                .setView(dialogview).setPositiveButton("Request Pin", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();

        pindialog.show();
        Button saveBtn = pindialog.getButton(DialogInterface.BUTTON_POSITIVE);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pindialog.dismiss();

                pinRequest();
            }
        });

    }

    public void pinRequest() {

        dialog.dismiss();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialogview = inflater.inflate(R.layout.request_pin_layout, null);
        pinEnterEditText = (EditText) dialogview.findViewById(R.id.enterPin);
        pinReenterEditText = (EditText) dialogview
                .findViewById(R.id.reenterPin);
        enterPasswordEditText = (EditText) dialogview
                .findViewById(R.id.confirmpassword);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
                getActivity());
        AlertDialog pindialog = dialogBuilder
                // .setMessage(ss)
                // .setIcon(R.drawable.ic_launcher)
                .setView(dialogview).setTitle("Pin Code Request")
                .setPositiveButton("Save", this)
                .setNegativeButton("Cancel", this).setCancelable(false)
                .create();
        pindialog.show();
        pindialog.setCanceledOnTouchOutside(true);

        Button saveBtn = pindialog.getButton(DialogInterface.BUTTON_POSITIVE);
        saveBtn.setOnClickListener(new CustomListener2(pindialog));

    }

    class CustomListener2 implements View.OnClickListener {

        private final Dialog dialog;

        public CustomListener2(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View v) {

            // Do whatever you want here
            // If you want to close the dialog, uncomment the line below
            String minLength = "4";

            if (!minLength.equals(pinEnterEditText.getText().toString()
                    .length()
                    + ""))
                pinEnterEditText
                        .setError("4 Digit Transaction Pin Code Required");

            if (!minLength.equals(pinReenterEditText.getText().toString()
                    .length()
                    + ""))
                pinReenterEditText
                        .setError("4 Digit Transaction Pin Code Required");

            if (enterPasswordEditText.getText().toString().isEmpty())
                enterPasswordEditText.setError("password required");

            if (pinEnterEditText.getText().toString().length() > 0
                    && pinReenterEditText.getText().toString().length() > 0
                    && enterPasswordEditText.getText().toString().length() > 0) {

                if (minLength.equals(pinEnterEditText.getText().toString()
                        .length()
                        + "")
                        && minLength.equals(pinReenterEditText.getText()
                        .toString().length()
                        + "")) {

                    if (pinEnterEditText.getText().toString()
                            .equals(pinReenterEditText.getText().toString())) {

						/*
                         * new HttpAsyncTaskSaveRechargePayDetails().execute(url
						 * + "/saveRecharge");
						 */
                        try {

                            Map<String, String> params = new HashMap<>();
                            params.put("parentTask", "rechargeApp");
                            params.put("childTask", "saveUserPinCode");
                            params.put("userCode", sessionHandler.getUserCode());
                            params.put("authenticationCode", sessionHandler.getAuthCode());
                            params.put("pin", pinEnterEditText.getText()
                                    .toString());
                            // Make Http call
//                            PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());
//                            handler.makeRequest(PayByOnlineConfig.SERVER_ACTION, "Saving Pin Details", params,
//                                    new PboServerRequestListener() {
//                                        @Override
//                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                                            try {
//                                                handleSendFieldContentResponse(response);
//                                            } catch (JSONException e) {
//                                                e.printStackTrace();
//                                            }
//                                            //Toast.makeText(getApplicationContext(), "JSONObject "+response+"",Toast.LENGTH_LONG).show();
//                                        }
//                                    });



//                            retrofitHelper.setOnResponseListener(new RetrofitHelper.OnRetrofitResponse() {
//                                @Override
//                                public void onRetrofitSuccess(JSONObject jsonObject, int apiCode) {
//                                    try {
//                                        handleSendFieldContentResponse(jsonObject);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                @Override
//                                public void onRetrofitFailure(String errorMessage, int apiCode) {
//                                    Log.d("BuyPageConfirmFrag", "error in Pin request");
//                                }
//                            });
//                            retrofitHelper.sendRequest(ApiIndex.GET_ON_AUTH_APP_USER_ENDPOINT, params, null, null);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    } else {
                        pinEnterEditText.setError("Pin not matched");
                    }

                } else {
                    pinEnterEditText
                            .setError(" 4 Digit Transaction Pin Code Required");
                }

            } else {
                enterPasswordEditText.setError("enter the data");
            }
        }
    }
}
