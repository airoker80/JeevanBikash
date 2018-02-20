package com.harati.jeevanbikas.PabyByOnlineService.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.PabyByOnlineService.Helper.Constants;
import com.harati.jeevanbikas.PabyByOnlineService.Model.DynamicFormField;
import com.harati.jeevanbikas.PabyByOnlineService.config.PayByOnlineConfig;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


//import com.paybyonline.ebiz.serverdata.PboServerRequestHandler;
//import com.paybyonline.ebiz.serverdata.PboServerRequestListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyProductFormFragment extends Fragment implements View.OnClickListener {
    TextView product_Type;
    TextView countryTxt;
    TextView account_description;
    String country;
    String productType;
    String accountDescription;
    String formCountryId;
    String merchantName;
    Button btnSubmmit;
    CoordinatorLayout coordinatorLayout;
    LinearLayout dynamicLayout;
    SessionHandler sessionHandler;
    LinkedHashMap linkedHashMap = new LinkedHashMap();
    LinkedHashMap fieldDetailsMap = new LinkedHashMap();
    Button smtBtn;
    List<DynamicFormField> dynamicFormFieldList;
    JSONObject labelVal = new JSONObject();
    JSONObject labelType = new JSONObject();
    DynamicFormField dynamicFormField;
    String serviceCategory = "";
    String scstId = "";
    String NEA_SERVICE_CATEGORY = "Nepal Electricity Authority";
    String BSR_MOVIES_CATEGORY = "BSR MOVIES";
    View view;
    //    PboServerRequestHandler pboServerRequestHandler;
    TextView productCountry;
    private ArrayList<Counter> counterList;
    private Counter currentSelectedCounter = null;
    private EditText consumerId, subscriberNumber;
    //    RequestParams params = new RequestParams();
    private Map<String, String> params = new HashMap<>();
    private View topView;

    Retrofit retrofit;
    ApiInterface apiInterface;

    public BuyProductFormFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Buy Product");
        view = inflater.inflate(R.layout.fragment_buy_product_form, container, false);

        retrofit = MyApplication.getRetrofitInstance(PayByOnlineConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        sessionHandler = new SessionHandler(getActivity());
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinatorLayout);
        topView = view.findViewById(R.id.topView);
//        pboServerRequestHandler = PboServerRequestHandler.getInstance(coordinatorLayout, getContext());
        product_Type = (TextView) view.findViewById(R.id.productType);
        countryTxt = (TextView) view.findViewById(R.id.country_txt);
        account_description = (TextView) view.findViewById(R.id.acountDesc);
        // smtBtn=(Button)view.findViewById(R.id.smtBtn);
        dynamicLayout = (LinearLayout) view.findViewById(R.id.dynamicLayout);
        Bundle bundle = getArguments();
        Log.e("bundle", "==>" + bundle.toString());
        scstId = bundle.getString("scstId");
        serviceCategory = bundle.getString("serviceCategory");
        btnSubmmit = (Button) view.findViewById(R.id.smtBtn);
        btnSubmmit.setVisibility(View.GONE);
        btnSubmmit.setOnClickListener(this);
        getDynamicFieldContent();
        return view;
    }

    public void getDynamicFieldContent() {

//        RequestParams params = new RequestParams();
        Map<String, String> params = new HashMap<>();
        // Make Http call
        if (serviceCategory != null && serviceCategory.equals(NEA_SERVICE_CATEGORY)) {
            params.put("parentTask", "customizedMerchantApi");
            params.put("childTask", "getNeaOfficeCode");
        }
//        else if (serviceCategory != null && serviceCategory.equals(BSR_MOVIES_CATEGORY)) {
//
//        }
        else {
            params.put("parentTask", "rechargeApp");
            params.put("childTask", "selectItemApp");
        }
        params.put("userCountry", "Nepal");
        params.put("userCode", sessionHandler.getUserCode());
        params.put("authenticationCode", sessionHandler.getAuthCode());
        Log.i("scstId", scstId);
        params.put("scstId", scstId);

//        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout, getActivity());


/*        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Please Wait !!!", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        try {
                            if (serviceCategory != null && serviceCategory.equals(NEA_SERVICE_CATEGORY)) {
                                handleStaticFields(response);
                            } else {
                                handleDynamicFieldContentResponse(response);
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.e("exp", "" + e);

                        }

                    }
                });*/


//        retrofitHelper.setOnResponseListener(new RetrofitHelper.OnRetrofitResponse() {
//            @Override
//            public void onRetrofitSuccess(JSONObject jsonObject, int apiCode) {
//                try {
//                    if (serviceCategory != null && serviceCategory.equals(NEA_SERVICE_CATEGORY)) {
//                        handleStaticFields(jsonObject);
//                    } else if (serviceCategory != null && serviceCategory.equals(BSR_MOVIES_CATEGORY)) {
//                        startWebView(jsonObject.getJSONObject("result").getJSONObject("serviceCategoryServiceType")
//                                .getString("serviceUrl"));
//                    } else {
//                        handleDynamicFieldContentResponse(jsonObject);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("exp", "" + e);
//
//                }
//            }
//
//            @Override
//            public void onRetrofitFailure(String errorMessage, int apiCode) {
//                Log.e("exp", "" + errorMessage);
//            }
//        });
//        retrofitHelper.sendRequest(ApiIndex.POST_ON_AUTH_APP_USER_ENDPOINT, null, null, params);


        Call<ResponseBody> call = apiInterface.getRechargeServiceDetails(PayByOnlineConfig.SERVER_URL+"authenticateAppUser",params);
        sessionHandler.showProgressDialog("Fetching data from server ..");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                sessionHandler.hideProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (serviceCategory != null && serviceCategory.equals(NEA_SERVICE_CATEGORY)) {
                        handleStaticFields(jsonObject);
                    } else if (serviceCategory != null && serviceCategory.equals(BSR_MOVIES_CATEGORY)) {
//                        startWebView(jsonObject.getJSONObject("result").getJSONObject("serviceCategoryServiceType").getString("serviceUrl"));
                    } else {
                        handleDynamicFieldContentResponse(jsonObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("exp", "" + e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });


    }


    private class Counter {

        private String counterAddress;
        private int counterNumber;

        Counter(String counterAddress, int counterNumber) {
            this.counterAddress = counterAddress;
            this.counterNumber = counterNumber;
        }

        public String getCounterAddress() {
            return counterAddress;
        }

        public int getCounterNumber() {
            return counterNumber;
        }
    }

    private void handleStaticFields(JSONObject result) {
        counterList = new ArrayList<>();
        ArrayList<String> counterConcatinatedList = new ArrayList<>();
        try {
            JSONArray counterListArray = result.getJSONArray("counterOfficeList");
            for (int i = 0; i < counterListArray.length(); i++) {
                JSONObject counter = counterListArray.getJSONObject(i);
                String counterName = counter.getString("CounterName");
                int counterCode = counter.getInt("CounterCode");
                Counter counterObj = new Counter(counterName, counterCode);
                counterList.add(counterObj);
            }
            for (Counter counter : counterList) {
                counterConcatinatedList.add(counter.getCounterAddress());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LinearLayout staticLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.static_layout_for_nea, dynamicLayout, false);
        // set views based on json
        final Spinner counterSpinner = (Spinner) staticLayout.findViewById(R.id.counterSpinner);
        staticLayout.findViewById(R.id.spinnerDrop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterSpinner.performClick();
            }
        });
        consumerId = (EditText) staticLayout.findViewById(R.id.consumerId);
        subscriberNumber = (EditText) staticLayout.findViewById(R.id.subscriberNumber);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, counterConcatinatedList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counterSpinner.setAdapter(dataAdapter);
        counterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                currentSelectedCounter = counterList.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dynamicLayout.addView(staticLayout);
        topView.setVisibility(View.GONE);
        btnSubmmit.setVisibility(View.VISIBLE);
    }

    public void handleDynamicFieldContentResponse(JSONObject result) throws JSONException {

        Log.i("Listresponsefield", "" + result);

        try {
            JSONObject response = result.getJSONObject("result");
            JSONObject fieldListObj = response.getJSONObject("fieldList");
            JSONObject serviceCategoryServiceType = response.getJSONObject("serviceCategoryServiceType");
            JSONObject merchantType = serviceCategoryServiceType.getJSONObject("merchantType");


            merchantName = merchantType.getString("name");
            Log.i("merchantType", merchantName + "");
            country = response.getString("country");
            // merchantType= response.getString("merchantType").toString();
            Log.i("merchant", "" + merchantType);

            productType = response.getString("productType");
            accountDescription = response.getString("accountDescription");
            formCountryId = response.getString("formCountryId");


            product_Type.setText(productType);

            if (country.equals("")) {
                countryTxt.setText("-");
            } else {
                countryTxt.setText(country);
            }
            account_description.setText(accountDescription);

            Log.i("String :", country + productType + formCountryId);

            Iterator iterator = fieldListObj.keys();
            JSONObject object = new JSONObject();
            String key = "";

            dynamicFormFieldList = new ArrayList<DynamicFormField>();
            while (iterator.hasNext()) {

                key = (String) iterator.next();
                object = fieldListObj.getJSONObject(key);
                Log.d("ob", object.toString());
                Log.i("object", key + "");
                //  get id from  issue
                //String _pubKey = issue.optString("id");
                dynamicFormField = new DynamicFormField(key, object.get("cid").toString(),
                        object.get("field_type").toString(),
                        object.get("label").toString(), object.get("required").toString()
                        , object.get("field_options").toString());

                Log.i("field_Option", "" + object.get("field_options").toString());

                dynamicFormFieldList.add(dynamicFormField);
                createDynamicFormElements(dynamicFormField);
                btnSubmmit.setVisibility(View.VISIBLE);

            }
        } catch (JSONException e) {

            e.printStackTrace();
            Log.i("msgsss", e + "");

        } catch (NullPointerException ex) {

            ex.printStackTrace();
            Log.i("msgsss", ex + "");

        }

    }

    public void createDynamicFormElements(DynamicFormField dynamicFormField) {

        JSONObject field_options;
        String field_type;

        try {

            field_type = dynamicFormField.getFieldType();
            Log.i("field_Option :", field_type);

            fieldDetailsMap = new LinkedHashMap();
            fieldDetailsMap.put("field_type", dynamicFormField.getFieldType());
            fieldDetailsMap.put("required", dynamicFormField.getRequire());
            fieldDetailsMap.put("label", dynamicFormField.getLabel());

            TextView textView = new TextView(getContext());
            textView.setText(dynamicFormField.getLabel() + (dynamicFormField.getRequire().equals("true") ? " *" : ""));
            textView.setTextColor(Color.parseColor("#494949"));
            textView.setTextSize(16);
            textView.setPadding(textView.getPaddingLeft(), 12, textView.getPaddingRight(), textView.getPaddingBottom());
            dynamicLayout.addView(textView);
            int id = Integer.parseInt(dynamicFormField.getId());

            field_options = new JSONObject(dynamicFormField.getfieldOption());

            switch (field_type) {

                case "number":
                case "website":
                case "email":
                case "text":
                case "paragraph":

                    EditText tv = new EditText(getContext());
                    tv.setTextSize(15);
                    tv.setPadding(tv.getPaddingLeft(), 10, tv.getPaddingRight(), tv.getPaddingBottom());
                    tv.setHint(dynamicFormField.getLabel());
                    tv.setId(R.id.titleId + id);

                    if ((field_type.equals("text")) || (field_type.equals("paragraph"))) {

                        //   JSONObject field_optionsText  = fieldListObj.getJSONObject("field_options");

                        if (field_options.has("minlength")) {

                            fieldDetailsMap.put("minlength", field_options.get("minlength").toString());
                        }

                        if (field_options.has("maxlength")) {

                            fieldDetailsMap.put("maxlength", field_options.get("maxlength").toString());

                        }

                        if (field_options.has("min_max_length_units")) {
                            fieldDetailsMap.put("min_max_length_units", field_options.get("min_max_length_units").toString());
                        }

//                        min_max_length_units --> characters or words

                        if (field_type.equals("text")) {
                            if (field_options.has("description")) {
                                fieldDetailsMap.put("description", field_options.get("description").toString());
                            }
                        }

                        if (field_type.equals("paragraph")) {
                            tv.setMinLines(4);
                            tv.setMaxLines(4);

                        }

                    }

                    if (field_type.equals("number")) {
                        tv.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }

                    if (field_type.equals("email")) {
                        tv.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                    }

                    linkedHashMap.put(id + "", fieldDetailsMap);
//                    dynamicLayout.addView(tv, editParams);
                    dynamicLayout.addView(tv);
                    break;

                case "checkbox":

                    LinearLayout checkboxLinearLayout = new LinearLayout(getContext());
                    checkboxLinearLayout.setOrientation(LinearLayout.VERTICAL);
                    checkboxLinearLayout.setId(R.id.titleId + id);
                    //  JSONObject field_optionsCheckbox = fieldListObj.getJSONObject("field_options");
                    JSONArray optionsCheckbox = field_options.getJSONArray("options");
                    CheckBox cb;
                    for (int i = 0; i < optionsCheckbox.length(); i++) {
                        JSONObject jo = (JSONObject) optionsCheckbox.get(i);
                        cb = new CheckBox(getContext());
                        cb.setText(jo.getString("label"));
                        cb.setTextColor(Color.parseColor("#3E3E3E"));
//                        cb.setId(i);
                        Log.i("is checked", jo.getString("checked") + "");
                        if (jo.getString("checked").equals("true")) {
                            cb.setChecked(true);
                        }

                        checkboxLinearLayout.addView(cb);
                    }

                    linkedHashMap.put(id + "", fieldDetailsMap);
                    dynamicLayout.addView(checkboxLinearLayout);


                    break;

                case "radio":
                    //add radio buttons
                    //  JSONObject field_options = fieldListObj.getJSONObject("field_options");
                    JSONArray options = field_options.getJSONArray("options");

                    final RadioButton[] rb = new RadioButton[5];
                    RadioGroup rg = new RadioGroup(getContext()); //create the RadioGroup
                    rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
                    rg.setId(R.id.titleId + id);

                    for (int i = 0; i < options.length(); i++) {
                        JSONObject jo = (JSONObject) options.get(i);
                        RadioButton button = new RadioButton(getContext());
                        button.setId(i);
                        button.setTextColor(Color.parseColor("#3E3E3E"));
                        button.setText(jo.getString("label"));
                        button.setChecked(i == 0); // Only select first button
                        rg.addView(button);
                    }

                    linkedHashMap.put(id + "", fieldDetailsMap);
                    dynamicLayout.addView(rg);//you add the whole RadioGroup to the layout

                    break;

                case "date":


                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    final Button button = new Button(getContext());
                    button.setId(R.id.titleId + id);
                    button.setTextColor(Color.parseColor("#3E3E3E"));
                    button.setBackgroundResource(R.drawable.btn);
                    button.setPadding(15, 15, 15, 15);
                    button.setTextSize(15);
                    setCurrentDateOnView(button);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showDatePicker(button);
                        }
                    });
                    linkedHashMap.put(id + "", fieldDetailsMap);
//                    dynamicLayout.addView(button);//you add the whole RadioGroup to the layout

                    dynamicLayout.addView(button, params);

                    break;

                case "dropdown":

                    //JSONObject field_optionsDropdown = fieldListObj.getJSONObject("field_options");
                    JSONArray optionsDropdown = field_options.getJSONArray("options");

                    Spinner spinner = new Spinner(getContext());
                    List<String> list = new ArrayList<String>();

                    if (field_options.getString("include_blank_option").equals("true")) {
                        list.add("Choose");
                    }

                    for (int i = 0; i < optionsDropdown.length(); i++) {
                        JSONObject jo = (JSONObject) optionsDropdown.get(i);
                        list.add(jo.getString("label"));
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, list);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(dataAdapter);
                    spinner.setId(R.id.titleId + id);
                    linkedHashMap.put(id + "", fieldDetailsMap);
                    dynamicLayout.addView(spinner);//you add the whole spinner to the layout
                    // intent.putExtra("labelVal",spinner.getDr)
                    break;

                default:
                    break;
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.i("msgsss", e + "");

        } catch (NullPointerException nullPointer) {

            nullPointer.printStackTrace();
            Log.i("msgsss", nullPointer + "");
        }

    }

    public Boolean validateFormData() {

        try {
            labelVal = new JSONObject();
            labelType = new JSONObject();

            Set set = linkedHashMap.entrySet();
            Iterator i = set.iterator();

            while (i.hasNext()) {

                Map.Entry me = (Map.Entry) i.next();

                LinkedHashMap data = (LinkedHashMap) me.getValue();
                String field_type = data.get("field_type").toString();
                int id = Integer.parseInt(me.getKey() + "");
                Log.i("msgsss", "key : " + me.getKey() + " value : " + me.getValue());
                labelType.put(data.get("label") + "Type", data.get("field_type").toString().trim());

                if (data.get("required").equals("true")) {

                    Log.i("msgss", "data is required");

                    switch (field_type) {

                        case "number":
                        case "website":
                        case "email":
                        case "text":
                        case "paragraph":

                            EditText editText = (EditText) view.findViewById(R.id.titleId + id);

                            if (!(editText.getText().toString().length() > 0)) {

                                editText.setError(data.get("label") + " required");
                                return false;

                            } else {

                                editText.setError(null);

                            }

                            if (field_type.equals("text")) {

                                if (data.get("minlength") != null) {

                                    int minLength = Integer.parseInt(data.get("minlength").toString());
                                    if ((editText.getText().toString().length() < minLength)) {

                                        editText.setError(data.get("label") + " shoul have min length " + minLength);
                                        return false;

                                    } else {

                                        editText.setError(null);
                                    }
                                }

                            }


                            if (field_type.equals("email")) {
                                //dom email validation here

                                if (TextUtils.isEmpty(editText.getText().toString()) || !android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
                                    editText.setError(data.get("label") + " should be email address");
                                    return false;
                                } else {
                                    editText.setError(null);
                                }

                            }


                            if (scstId.equals("24") && data.get("label").equals("SIM Tv Customer Id")) {
                                if (editText.getText().toString().startsWith("1")) {
                                    editText.setError(null);
                                } else {
                                    editText.setError(data.get("label") + " must start with 1.");
                                    return false;
                                }

                                if (editText.getText().toString().length() == 10) {
                                    editText.setError(null);
                                } else {
                                    editText.setError(data.get("label") + " must be of 10 characters.");
                                    return false;
                                }
                            }

                            if (scstId.equals("24") && data.get("label").equals("Amount")) {
                                if (Integer.parseInt(editText.getText().toString()) < 350) {
                                    editText.setError(data.get("label") + " should be greater than 350.");
                                    return false;
                                } else {
                                    editText.setError(null);
                                }
                            }

                            labelVal.put((data.get("label") + "Val").trim(), editText.getText().toString());
                            Log.d("eeee", data.get("label") + "Val");

                            break;

                        case "checkbox":

                            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.titleId + id);

                            int count = linearLayout.getChildCount();
                            Boolean isAnyCheckboxChecked = false;
                            CheckBox c;
                            for (int j = 0; j < count; j++) {

                                c = (CheckBox) linearLayout.getChildAt(j);
                                if (!isAnyCheckboxChecked) {
                                    if (c.isChecked()) {
                                        isAnyCheckboxChecked = true;
                                        break; // no need to check further more
                                    }
                                }
                            }

                            if (!isAnyCheckboxChecked) {
                                Toast toast = Toast.makeText(getContext(), "Please Choose " + data.get("label"), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP, 25, 400);
                                toast.show();
                                return false;
                            }

                            String checkBoxVal = "";
                            for (int j = 0; j < count; j++) {

                                c = (CheckBox) linearLayout.getChildAt(j);

                                if (c.isChecked()) {

                                    checkBoxVal = (checkBoxVal.length() > 0) ? checkBoxVal + "," + c.getText().toString()
                                            : c.getText().toString();
                                }

                            }

                            labelVal.put(data.get("label") + "Val", checkBoxVal);

                            break;

                        case "radio":


                            RadioGroup rg = (RadioGroup) view.findViewById(R.id.titleId + id); //create the RadioGroup
                            // rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL


                            RadioButton button;
                            Boolean isAnyRadioChecked = false;

                            for (int k = 0; k < rg.getChildCount(); k++) {
                                button = (RadioButton) rg.getChildAt(k);

                                if (!isAnyRadioChecked) {
                                    if (button.isChecked()) {
                                        isAnyRadioChecked = true;
                                        labelVal.put(data.get("label") + "Val", button.getText().toString());
                                        break;
                                    }
                                }
                            }
                            if (!isAnyRadioChecked) {
                                Toast toast = Toast.makeText(getContext(), "Please Choose " + data.get("label"), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP, 25, 400);
                                toast.show();
                                return false;
                            }


                            break;

                        case "date":

                            Button btnDate = (Button) view.findViewById(R.id.titleId + id);
                            labelVal.put(data.get("label") + "Val", btnDate.getText().toString().replaceAll("-", ","));

                            break;

                        case "dropdown":

                            Spinner spinner = (Spinner) view.findViewById(R.id.titleId + id);
                            if (spinner.getSelectedItem().equals("Choose")) {
//                            Toast.makeText(getApplicationContext(),"Please Select "+data.get("label"),Toast.LENGTH_SHORT).show();
                                Toast toast = Toast.makeText(getContext(), "Please Select " + data.get("label"), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP, 25, 400);
                                toast.show();
                                return false;
                            }


                            if (spinner.getSelectedItem().equals("Choose")) {

                                labelVal.put(data.get("label") + "Val", "");

                            } else {

                                labelVal.put(data.get("label") + "Val", spinner.getSelectedItem().toString());

                            }
                            break;

                        default:
                            break;
                    }


                } else {

                    switch (field_type) {

                        case "number":
                        case "website":
                        case "email":
                        case "text":
                        case "paragraph":

                            EditText editText = (EditText) view.findViewById(R.id.titleId + id);
                            labelVal.put(data.get("label") + "Val", editText.getText().toString());
                            Log.d("print", data.get("label").toString());

                            break;

                        case "checkbox":


                            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.titleId + id);
                            int count = linearLayout.getChildCount();

                            Boolean isAnyCheckboxChecked = false;
                            CheckBox c;
                            for (int j = 0; j < count; j++) {

                                c = (CheckBox) linearLayout.getChildAt(j);
                                if (!isAnyCheckboxChecked) {

                                    if (c.isChecked()) {

                                        isAnyCheckboxChecked = true;
                                        labelVal.put(data.get("label") + "Val", c.getText().toString());
                                        break; // no need to check further more
                                    }
                                } else {
                                    labelVal.put(data.get("label") + "Val", "");

                                }
                            }

                            if (!isAnyCheckboxChecked) {
//                            Toast.makeText(getApplicationContext(),"Please Choose "+data.get("label"),Toast.LENGTH_SHORT).show();
                                Toast toast = Toast.makeText(getActivity(), "Please Choose " + data.get("label"), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP, 25, 400);
                                toast.show();
                                return false;
                            }

                            String checkBoxVal = "";
                            for (int j = 0; j < count; j++) {

                                c = (CheckBox) linearLayout.getChildAt(j);
                                if (c.isChecked()) {

                                    checkBoxVal = (checkBoxVal.length() > 0) ? checkBoxVal + "," + c.getText().toString()
                                            : c.getText().toString();
                                }
                            }

                            labelVal.put(data.get("label") + "Val", checkBoxVal);
                            break;

                        case "radio":


                            RadioGroup rg = (RadioGroup) view.findViewById(R.id.titleId + id);
                            ;
                            for (int j = 0; j < rg.getChildCount(); j++) {

                                RadioButton r = (RadioButton) rg.getChildAt(j);
                                if (r.isChecked()) {

                                    labelVal.put(data.get("label") + "Val", r.getText().toString());

                                }


                            }

                            break;

                        case "date":

                            Button btnDate = (Button) view.findViewById(R.id.titleId + id);
                            labelVal.put(data.get("label") + "Val", btnDate.getText().toString().replaceAll("-", ","));

                            break;

                        case "dropdown":

                            Spinner spinner = (Spinner) view.findViewById(R.id.titleId + id);
                            if (spinner.getSelectedItem().equals("Choose")) {

                                labelVal.put(data.get("label") + "Val", "");

                            } else {

                                labelVal.put(data.get("label") + "Val", spinner.getSelectedItem().toString());

                            }
                            break;

                        default:
                            break;
                    }

                }
            }


        } catch (JSONException e) {

            Log.e("exe", e + "");

        } catch (NullPointerException nullPointer) {
            Log.e("nullPointer", nullPointer + "");
        }
        return true;

    }

    public void setCurrentDateOnView(Button fromToBtn) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        fromToBtn.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(year).append("-").append(month + 1).append("-")
                .append(day));

    }

    private void showDatePicker(final Button fromToBtn) {

        DatePickerAllFragment date = new DatePickerAllFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        String[] selDate = fromToBtn.getText().toString().split("-");
        args.putInt("year", Integer.parseInt(selDate[0]));
        args.putInt("month", Integer.parseInt(selDate[1]) - 1);
        args.putInt("day", Integer.parseInt(selDate[2]));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
//        date.setCallBack(ondate);
        date.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                fromToBtn.setText(String.valueOf(year) + "-"
                        + String.valueOf(monthOfYear + 1) + "-"
                        + String.valueOf(dayOfMonth));
            }
        });
        date.show(getActivity().getSupportFragmentManager(), "Date Picker");
    }

    private boolean validateStaticForm() {
        if (subscriberNumber.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Enter subscriber number", Toast.LENGTH_SHORT).show();
            subscriberNumber.requestFocus();
            return false;
        } else if (consumerId.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Enter consumer id", Toast.LENGTH_SHORT).show();
            consumerId.requestFocus();
            return false;
        } else return true;
    }

    private void sendStaticFormValues() {
        // Make Http call
/*        PboServerRequestHandler handler = PboServerRequestHandler.getInstance(coordinatorLayout,
                getActivity());*/
        params.put("parentTask", "customizedMerchantApi");
        params.put("childTask", "getNeaBill");
        params.put("subscriberNo", subscriberNumber.getText().toString().trim());
        params.put("scstId", scstId);
        params.put("consumerId", consumerId.getText().toString().trim());
        params.put("officeCodes", String.valueOf(currentSelectedCounter.getCounterNumber()));
        params.put("userCode", sessionHandler.getUserCode());
        params.put("authenticationCode", sessionHandler.getAuthCode());
/*        handler.makePostRequest(PayByOnlineConfig.SERVER_ACTION, "Processing...", params,
                new PboServerRequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("asdasdasda", response.toString());
                        Log.i("params", "" + params.toString());
                        Fragment fragment = new NeaBillFragmentNew();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, fragment, Constants.NEA_BILL_FRAGMENT);
                        Bundle bundle = new Bundle();
                        bundle.putString("formJson", response.toString());
                        fragment.setArguments(bundle);
                        transaction.commit();
                    }
                });*/


//        retrofitHelper.setOnResponseListener(new RetrofitHelper.OnRetrofitResponse() {
//            @Override
//            public void onRetrofitSuccess(JSONObject jsonObject, int apiCode) throws JSONException {
//                Log.d("asdasdasda", jsonObject.toString());
//                Log.i("params", "" + params.toString());
//                if (jsonObject.has("msg") && jsonObject.getString("msg").equals("failed")) {
////                    if (jsonObject.getString("msg").equals("failed")){
//                    Toast.makeText(getContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
////                    }
//                } else if (getActivity() != null) {
//                    Fragment fragment = new NeaBillFragmentNew();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.replace(R.id.content_frame, fragment, Constants.NEA_BILL_FRAGMENT);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("formJson", jsonObject.toString());
//                    fragment.setArguments(bundle);
//                    transaction.commit();
//                }
//            }
//
//            @Override
//            public void onRetrofitFailure(String errorMessage, int apiCode) {
//                Log.i("BuyProductFormFragment", "error on " + apiCode);
//                Toast.makeText(getActivity(), "Server did not respond", Toast.LENGTH_SHORT).show();
//                params.clear();
//            }
//        });
//        retrofitHelper.sendRequest(ApiIndex.POST_ON_AUTH_APP_USER_ENDPOINT, null, null, params);


        Call<ResponseBody> call = apiInterface.getRechargeServiceDetails(PayByOnlineConfig.SERVER_URL +"authenticateAppUser",params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.has("msg") && jsonObject.getString("msg").equals("failed")) {
                        if (jsonObject.getString("msg").equals("failed")){
                            Toast.makeText(getContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } else if (getActivity() != null) {
//                        Fragment fragment = new NeaBillFragmentNew();
//                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.content_frame, fragment, Constants.NEA_BILL_FRAGMENT);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("formJson", jsonObject.toString());
//                        fragment.setArguments(bundle);
//                        transaction.commit();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.smtBtn:
                if (counterList != null && counterList.size() != 0) {
                    if (validateStaticForm()) {
                        sendStaticFormValues();
                        break;
                    }
                } else {
                    Boolean result = validateFormData();
                    if (result) {
                        Bundle bundle = new Bundle();
                        Fragment fragment = new BuyPageConfirmFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.contentFrame, fragment, Constants.BUY_CONFIRM_FRAGMENT);
//                        fragmentTransaction.addToBackStack(null);
                        bundle.putString("valList", labelVal.toString());
                        bundle.putString("typeList", labelType.toString());
                        bundle.putString("scstId", scstId);
                        bundle.putString("formCountryId", formCountryId);
                        bundle.putString("merchantName", merchantName);
                        fragment.setArguments(bundle);
                        fragmentTransaction.commit();
                        break;
                    }
                }
        }
    }
}
