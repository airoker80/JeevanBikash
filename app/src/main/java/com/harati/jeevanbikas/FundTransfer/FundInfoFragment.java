package com.harati.jeevanbikas.FundTransfer;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.harati.jeevanbikas.Helper.DialogActivity;
import com.harati.jeevanbikas.Helper.ErrorDialogActivity;
import com.harati.jeevanbikas.Helper.SessionHandler;
import com.harati.jeevanbikas.R;
import com.harati.jeevanbikas.Retrofit.Interface.ApiInterface;
import com.harati.jeevanbikas.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.jeevanbikas.Retrofit.RetrofitModel.TransferModel;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 8/28/2017.
 */

public class FundInfoFragment extends Fragment {
    ApiInterface apiInterface;
    SessionHandler sessionHandler ;

    Bundle bundle;
    ImageView submit;
    EditText BenificiaryaccNo,confirmAccNo ,transferAmt,agentPin ,fundRemarks;
    String BenificiaryaccNoTxt,confirmAccNoTxt ,deposoitAmtTxt,agentPinTxt ,fundRemarksTxt;
    public FundInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fund_info, container, false);

        apiInterface= RetrofitClient.getApiService();
        sessionHandler = new SessionHandler(getContext());

        submit = (ImageView)view.findViewById(R.id.submit);

        bundle = getArguments();

        BenificiaryaccNo=(EditText)view.findViewById(R.id.BenificiaryaccNo) ;
        confirmAccNo=(EditText)view.findViewById(R.id.confirmAccNo) ;
        transferAmt=(EditText)view.findViewById(R.id.transferAmt) ;
        agentPin=(EditText)view.findViewById(R.id.agentPin) ;
        fundRemarks=(EditText)view.findViewById(R.id.fundRemarks) ;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      /*          Intent intent= new Intent(getContext(), DialogActivity.class);
                getActivity().startActivity(intent);*/

                BenificiaryaccNoTxt=BenificiaryaccNo.getText().toString();
                confirmAccNoTxt=confirmAccNo.getText().toString();
                deposoitAmtTxt=transferAmt.getText().toString();
                agentPinTxt=agentPin.getText().toString();
                fundRemarksTxt=fundRemarks.getText().toString();

                if (BenificiaryaccNoTxt.equals("")|confirmAccNoTxt.equals("")|deposoitAmtTxt.equals("")|
                agentPinTxt.equals("")|fundRemarksTxt.equals("")){
                    getActivity().startActivity(new Intent(getContext(), ErrorDialogActivity.class));
                }else {
/*                    Bundle args = new Bundle();
                    args.putString("goto","ok");
                    Fragment fragment= new FundFingerCheckFragment();
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.contentFrame, fragment);
                    transaction.commit();*/
                    sendTransferPostRequest();
                }
            }
        });
        return view;
    }

    void sendTransferPostRequest(){
        sessionHandler.showProgressDialog("sending Request ... ");
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("membercode",bundle.getString("code"));
            jsonObject.put("finger","1234");
            jsonObject.put("amount",transferAmt.getText().toString());
            jsonObject.put("agentpin",agentPin.getText().toString());
            jsonObject.put("beneficiary",BenificiaryaccNo.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),(jsonObject.toString()));
        retrofit2.Call<TransferModel> call = apiInterface.sendFundTransferRequest(body,
                sessionHandler.getAgentToken(),"Basic dXNlcjpqQiQjYUJAMjA1NA==",
                "application/json");

        call.enqueue(new Callback<TransferModel>() {
            @Override
            public void onResponse(Call<TransferModel> call, Response<TransferModel> response) {
                sessionHandler.hideProgressDialog();
                if (response.isSuccessful()){
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg",response.body().getMessage());
                    startActivity(intent);
                }else {
                    sessionHandler.hideProgressDialog();
                    Intent intent = new Intent(getContext(),DialogActivity.class);
                    intent.putExtra("msg","Error in Fields");
                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<TransferModel> call, Throwable t) {
                sessionHandler.hideProgressDialog();
                Intent intent = new Intent(getContext(),DialogActivity.class);
                intent.putExtra("msg","Connection Error");
                startActivity(intent);
            }
        });
    }
}
