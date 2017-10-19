package com.harati.jeevanbikas.BalanceEnquiry;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteAdapter;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteModel;
import com.harati.jeevanbikas.Helper.HelperListModelClass;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Login.LoginActivity;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.VolleyPackage.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/28/2017.
 */

public class BalanceFragment extends Fragment implements View.OnClickListener {
    ApiInterface apiInterface ;
    List<AutoCompleteModel> autoCompleteModelList = new ArrayList<AutoCompleteModel>();
    AutoCompleteTextView input;
    ImageView imageView,enquiry_cross;
    SessionHandler sessionHandler;
    List<HelperListModelClass> helperListModelClasses=new ArrayList<HelperListModelClass>();
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
        input.setAdapter(autoCompleteAdapter);
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
                if (input.getText().toString().equals("")) {
                    input.setError("Please Enter the Phone Number");
                } else {
                    /*sendBalanceEnquiryRequest();*/
                    Fragment fragment = new EnquiryUserDetails();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();
                }
                break;

        }

    }

    public  void  sendBalanceEnquiryRequest(){
        final JSONObject jsonObject = new JSONObject();
//      startActivity(new Intent(InitialResetPassword.this,ResetPassword.class));
        JSONArray jsonArray = new JSONArray();
        try{
            Log.e("agentCode","ac"+sessionHandler.getAgentCode());
            jsonObject.put("membercode","M0670001");
            jsonObject.put("finger","1234");

            jsonArray.put(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<String> call = apiInterface.sendBalanceRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
