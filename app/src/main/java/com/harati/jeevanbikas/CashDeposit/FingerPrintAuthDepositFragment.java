package com.harati.jeevanbikas.CashDeposit;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.JeevanBikashConfig.JeevanBikashConfig;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.MyApplication;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by User on 8/28/2017.
 */

public class FingerPrintAuthDepositFragment extends Fragment {
    Retrofit retrofit;
    ApiInterface apiInterface ;
    SessionHandler sessionHandler;
    ImageView fingerPrint;
    String code,name,office ,photo,clientMobile;
    String clientCode,clientName,clientOffice,clientPhoto,clientPin;
    Bundle bundle;
    public FingerPrintAuthDepositFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint, container, false);

        bundle = getArguments();

        Log.d("bund d;le", "v^V^V^v" + bundle.toString());

        retrofit = MyApplication.getRetrofitInstance(JeevanBikashConfig.BASE_URL);
        apiInterface = retrofit.create(ApiInterface.class);
        sessionHandler = new SessionHandler(getContext());
//        getMemberList(clientMobile);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        fingerPrint.setOnClickListener(view1 -> {
            Fragment fragment= new AgentPinDetailsFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.contentFrame, fragment);
            transaction.commit();
        });
        return view;
    }

    private void getMemberList(String mobile_no){
        sessionHandler.showProgressDialog("Sending Request ....");
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SearchModel> call = apiInterface.sendMemberSearchRequest(mobile_no,sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json",sessionHandler.getAgentSerialNo(),"");
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                sessionHandler.hideProgressDialog();
//                Log.d("DADAD0","ADA");
                if (String.valueOf(response.code()).equals("200")){
                    clientCode=response.body().getCode();
                    clientName=response.body().getName();
                    clientOffice=response.body().getOffice();
//                    args.putString("office",response.body().getCode());
                    clientPhoto=response.body().getPhoto();
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
