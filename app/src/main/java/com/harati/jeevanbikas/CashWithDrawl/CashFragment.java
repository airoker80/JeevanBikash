package com.harati.jeevanbikas.CashWithDrawl;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.harati.jeevanbikas.BalanceEnquiry.EnquiryUserDetails;
import com.harati.jeevanbikas.Helper.ApiSessionHandler;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteAdapter;
import com.harati.jeevanbikas.Helper.AutoCompleteHelper.AutoCompleteModel;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MainPackage.MainActivity;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by User on 8/28/2017.
 */

public class CashFragment extends Fragment {
    ApiSessionHandler apiSessionHandler;
    Retrofit retrofit;
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;

    List<AutoCompleteModel> autoCompleteModelList = new ArrayList<>();
    AutoCompleteTextView input;
     ImageView imageView;
    TextView cash_wd_txt;
    public CashFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fund, container, false);

        apiSessionHandler = new ApiSessionHandler(getContext());

        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);

        sessionHandler = new SessionHandler(getContext());


        imageView = (ImageView)view.findViewById(R.id.imageView);
        input = (AutoCompleteTextView) view.findViewById(R.id.input);
        cash_wd_txt = (TextView) view.findViewById(R.id.cash_wd_txt);

        autoCompleteModelList.add(new AutoCompleteModel("Sameer","9843697320",R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("arjun","9844400099",R.drawable.ic_username));
        autoCompleteModelList.add(new AutoCompleteModel("Binaya","9841012346",R.drawable.ic_username));
        input.setDropDownBackgroundResource(R.drawable.shape_transparent);
        AutoCompleteAdapter autoCompleteAdapter = new AutoCompleteAdapter(getContext(),autoCompleteModelList);
        input.setAdapter(autoCompleteAdapter);

        cash_wd_txt.setTypeface(MainActivity.centuryGothic);
        input.setTypeface(MainActivity.centuryGothic);
        imageView.setOnClickListener(view1 -> {
            if (input.getText().toString().equals("")){
                input.setError("Please Enter the Phone Number");
            }else {
                getMemberList(input.getText().toString());
//                    Fragment fragment= new FingerPrintAuthFragment();
//                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.addToBackStack(null);
//                    transaction.replace(R.id.contentFrame, fragment);
//                    transaction.commit();
            }
        });
        return view;
    }

    private void getMemberList(String mobile_no){
        sessionHandler.showProgressDialog("Sending Request ...");
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SearchModel> call = apiInterface.sendMemberSearchRequest(apiSessionHandler.getMEMBER_SEARCH(),mobile_no,sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",apiSessionHandler.getAgentCode());
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                sessionHandler.hideProgressDialog();
                Log.d("DADAD0","response");
                if (String.valueOf(response.code()).equals("200")){
                    Log.d("DADAD0","succes");
                    Fragment fragment = new CashwithdrawlFragment();
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
                    }catch (Exception e){
                        Intent intent = new Intent(getContext(), ErrorDialogActivity.class);
                        intent.putExtra("msg","Wrong Credential");
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
