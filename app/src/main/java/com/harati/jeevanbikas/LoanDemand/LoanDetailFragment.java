package com.harati.jeevanbikas.LoanDemand;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.LoanDemand.Adapter.SpinnerAdapter;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.LoanDetailsModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SearchModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.SuccesResponseModel;

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

public class LoanDetailFragment extends Fragment {
        Bundle bundle;
    List<LoanDetailsModel> loanDetailsModels= new ArrayList<LoanDetailsModel>();

    SessionHandler sessionHandler;
    ApiInterface apiInterface;

    ImageView submit;
    Spinner spinner;
    EditText loanAmt;
    public LoanDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_loan_detail, container, false);
        bundle=getArguments();
        sessionHandler = new SessionHandler(getContext());
        apiInterface= RetrofitClient.getApiService();

        getLoanTypeList();

        List<String> stringList = new ArrayList<String>();
        for (int i = 0;i<loanDetailsModels.size();i++){
            LoanDetailsModel loanDetailsModel= loanDetailsModels.get(i);
            Log.d("loantype","=-"+loanDetailsModel.getLoanType());
            stringList.add(loanDetailsModel.getLoanType());
        }
        submit = (ImageView)view.findViewById(R.id.submit);
        loanAmt = (EditText) view.findViewById(R.id.loanAmt);
        spinner= (Spinner)view.findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.loantype, R.layout.text_layout);


//        SpinnerAdapter spinnerAdapter =new SpinnerAdapter(getContext(),R.layout.text_layout,getContext(),loanDetailsModels);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.loantype, R.layout.text_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        spinner.setAdapter(spinnerMenu);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loanAmt.getText().toString().equals("")|spinner.getSelectedItem().toString().equals("")){
                    Intent intent= new Intent(getContext(), ErrorDialogActivity.class);
                    getActivity().startActivity(intent);
                }else {
                    sendLoanDemandRequest();
                }

            }
        });
        return view;
    }

    private void getLoanTypeList(){
        sessionHandler.showProgressDialog("Sending Request ...");
        retrofit2.Call<List<LoanDetailsModel>> call = apiInterface.getLoanTypeList(sessionHandler.getAgentToken(),
                "Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<List<LoanDetailsModel>>() {
            @Override
            public void onResponse(Call<List<LoanDetailsModel>> call, Response<List<LoanDetailsModel>> response) {
                sessionHandler.hideProgressDialog();
                loanDetailsModels= response.body();
                try {
                    Log.d("ok lets test","==="+loanDetailsModels.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<LoanDetailsModel>> call, Throwable t) {
                sessionHandler.hideProgressDialog();
            }
        });

    }

    private void sendLoanDemandRequest(){

        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",loanAmt.getText().toString());
            jsonObject.put("loantype",spinner.getSelectedItem().toString());
            jsonObject.put("remark","testing");
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("body jsonBody","=()-"+jsonObject.toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<SuccesResponseModel> call = apiInterface.sendPostLoanDemand(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<SuccesResponseModel>() {
            @Override
            public void onResponse(Call<SuccesResponseModel> call, Response<SuccesResponseModel> response) {
                sessionHandler.hideProgressDialog();
                if (response.isSuccessful()){
                    if (response.body().getStatus().equals("Success")){
                        Intent intent = new Intent(getContext(),DialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getContext(),ErrorDialogActivity.class);
                        intent.putExtra("msg",response.body().getMessage());
                        startActivity(intent);
                    }


                }else {
                    sessionHandler.hideProgressDialog();
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg","Error in Fields");
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<SuccesResponseModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(getContext(),DialogActivity.class);
                intent.putExtra("msg","Connection Error");
                startActivity(intent);
            }
        });
    }
}
