package com.harati.jeevanbikas.CashDeposit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/28/2017.
 */

public class FingerPrintAuthDepositFragment extends Fragment {
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
        apiInterface = RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());
        code = bundle.getString("code");
        name = bundle.getString("name");
        office = bundle.getString("office");
        photo = bundle.getString("photo");
//        clientPin = bundle.getString("clientPin");
        clientMobile = bundle.getString("clientMobile");
        getMemberList(clientMobile);
        fingerPrint = (ImageView) view.findViewById(R.id.fingerPrint);
        fingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= new AgentPinDetailsFragment();
/*                Bundle args = new Bundle();
                args.putString("code",code);
                args.putString("name",name);
                args.putString("office",office);
//                    args.putString("office",response.body().getCode());
                args.putString("photo",photo);
                args.putString("clientPin",clientPin);*/
                bundle.putString("clientCode",clientCode);
                bundle.putString("clientName",clientName);
                bundle.putString("clientOffice",clientOffice);
                bundle.putString("clientPhoto",clientPhoto);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.contentFrame, fragment);
                transaction.commit();
            }
        });
        return view;
    }

    private void getMemberList(String mobile_no){
        sessionHandler.showProgressDialog("Sending Request ....");
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SearchModel> call = apiInterface.sendMemberSearchRequest(mobile_no,sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                sessionHandler.hideProgressDialog();
//                Log.d("DADAD0","ADA");
                if (response.isSuccessful()){
                    clientCode=response.body().getCode();
                    clientName=response.body().getName();
                    clientOffice=response.body().getOffice();
//                    args.putString("office",response.body().getCode());
                    clientPhoto=response.body().getPhoto();
                }else {

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
