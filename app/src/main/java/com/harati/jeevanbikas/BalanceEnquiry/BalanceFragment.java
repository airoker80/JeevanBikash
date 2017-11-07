package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteAdapter;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteModel;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/28/2017.
 */

public class BalanceFragment extends Fragment implements View.OnClickListener {
    ApiInterface apiInterface ;
    List<AutoCompleteModel> autoCompleteModelList = new ArrayList<>();
    AutoCompleteTextView input;
    ImageView imageView,enquiry_cross;
    SessionHandler sessionHandler;
    List<HelperListModelClass> helperListModelClasses= new ArrayList<>();
    TextView text;
    public BalanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        sessionHandler = new SessionHandler(getContext());
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        apiInterface = RetrofitClient.getApiService();
        imageView = (ImageView) view.findViewById(R.id.imageView);
        enquiry_cross = (ImageView) view.findViewById(R.id.enquiry_cross);
        input = (AutoCompleteTextView) view.findViewById(R.id.input);

        text = (TextView) view.findViewById(R.id.text);
        text.setTypeface(MainActivity.centuryGothic);
        autoCompleteModelList.add(new AutoCompleteModel("Sameer", "9843697320", R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("arjun", "9844400099", R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("Binaya", "9841012346", R.drawable.ic_username));
        input.setDropDownBackgroundResource(R.drawable.shape_transparent);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(getContext(), autoCompleteModelList);
        /*input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                final StringBuilder sb = new StringBuilder(s.length()+1);
                sb.append(s);
                Log.d("Changed Text=","---"+sb.toString());
                getMemberList();
//                Log.d("Changed Text=","---"+sb.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
//        input.setAdapter(autoCompleteAdapter);
        input.setTypeface(MainActivity.centuryGothic);
        imageView.setOnClickListener(this);
        enquiry_cross.setOnClickListener(this);
        return view;
    }

    /*private void enquiryBalance(){
        VolleyRequestHandler volleyRequestHandler = new VolleyRequestHandler(getContext());
        helperListModelClasses.add(new HelperListModelClass("mobile",input.getText().toString()));
        String response = volleyRequestHandler.makePostRequest(JeevanBikashConfig.BASE_URL+"/agentbanking/api/v1/member/search?device=SerialNo",helperListModelClasses);

        Bundle bundle = new Bundle();
        bundle.putString("response",response);
        Fragment fragment = new EnquiryUserDetails();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.contentFrame, fragment);
        transaction.commit();
    }*/

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid){
            case R.id.enquiry_cross:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
            case R.id.imageView:
                String mobileNo = input.getText().toString();
                if (input.getText().toString().equals("")) {
                    input.setError("Please Enter the Phone Number");
                } else {
//                    sendBalanceEnquiryRequest();

                    getMemberList(mobileNo);

//                    Fragment fragment = new EnquiryUserDetails();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
//                    transaction.replace(R.id.contentFrame, fragment);
//                    transaction.commit();
                }
                break;

        }

    }
    private void getMemberList(String mobile_no){
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));


        sessionHandler.showProgressDialog("Sending Request ...");
        retrofit2.Call<SearchModel> call = apiInterface.sendMemberSearchRequest(mobile_no,sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                sessionHandler.hideProgressDialog();
//                Log.d("DADAD0","ADA");
                if (String.valueOf(response.code()).equals("200")){
                    Fragment fragment = new EnquiryUserDetails();
                    Bundle args = new Bundle();
                    args.putString("code",response.body().getCode());
                    args.putString("name",response.body().getName());
                    args.putString("office",response.body().getOffice());
//                    args.putString("office",response.body().getCode());
                    args.putString("photo",response.body().getOffice());
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                }else {
                    try {

                        String jsonString = response.errorBody().string();

                        Log.d("here ","--=>"+jsonString);

                        JSONObject jsonObject = new JSONObject(jsonString);
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg",jsonObject.getString("message"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Toast.makeText(getContext(), "Connection Problem", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
